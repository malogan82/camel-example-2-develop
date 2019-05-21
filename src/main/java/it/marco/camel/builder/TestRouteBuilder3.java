package it.marco.camel.builder;

import java.util.Date;
import java.util.concurrent.ExecutorService;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.ThreadPoolBuilder;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.impl.ThreadPoolProfileSupport;
import org.apache.camel.routepolicy.quartz.CronScheduledRoutePolicy;
import org.apache.camel.routepolicy.quartz.SimpleScheduledRoutePolicy;
import org.apache.camel.spi.ExecutorServiceManager;
import org.apache.camel.spi.ThreadPoolProfile;

public class TestRouteBuilder3 extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		ExecutorServiceManager manager = getContext().getExecutorServiceManager();
		ThreadPoolProfile defaultProfile = manager.getDefaultThreadPoolProfile();
		defaultProfile.setPoolSize(3);
		defaultProfile.setMaxQueueSize(100);
		manager.registerThreadPoolProfile(defaultProfile);
		
		ThreadPoolProfile customProfile = new ThreadPoolProfileSupport("customProfile");
		customProfile.setPoolSize(5);
		customProfile.setMaxPoolSize(5);
		customProfile.setMaxQueueSize(100);;
		getContext().getExecutorServiceManager().registerThreadPoolProfile(customProfile);
		
		ThreadPoolBuilder poolBuilder = new ThreadPoolBuilder(getContext());
		ExecutorService customPool = poolBuilder.poolSize(5).maxPoolSize(5).maxQueueSize(100).build("customPool");
		
		getContext().getRegistry(JndiRegistry.class).bind("customPool", customPool);
		
		SimpleScheduledRoutePolicy policy = new SimpleScheduledRoutePolicy();
		long startTime = System.currentTimeMillis() + 3000L;
		policy.setRouteStartDate(new Date(startTime));
		policy.setRouteStartRepeatCount(1);
		policy.setRouteStartRepeatInterval(3000);
		
		CronScheduledRoutePolicy cronPolicy = new CronScheduledRoutePolicy();
		cronPolicy.setRouteStartTime("*/3 * * * * ?");
		
		from("direct:start-policy")
		   .routeId("test")
		   .routePolicy(policy)
		   .to("mock:success");
		
		from("direct:start-cron-policy")
		    .routeId("test-cron")
		    .routePolicy(cronPolicy)
		    .to("mock:success");
		
		from("direct:inbox").
			multicast().parallelProcessing().
			to("mock:a").
			to("mock:b").
			to("mock:c");
		
		from("direct:start").
		    multicast().executorService(customPool). 
		    to("mock:first").
		    to("mock:second").
		    to("mock:third");
		
		from("direct:start-ref").
			multicast().executorServiceRef("customPool").
			to("mock:first").
			to("mock:second").
			to("mock:third");
		
		from("direct:start-custom").
			multicast().executorServiceRef("customProfile").
			to("mock:first").
			to("mock:second").
			to("mock:third");
		
		from("jetty:http://localhost:8180")
	    	.routeId("first")
	    	.startupOrder(1)
	    	.to("seda:buffer");

		from("seda:buffer")
	    	.routeId("second")
	    	.startupOrder(2)
	    	.to("mock:result");
		
	}

}
