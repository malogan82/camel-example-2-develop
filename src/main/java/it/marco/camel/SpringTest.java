package it.marco.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.main.Main;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import it.marco.camel.model.Persona;
import it.marco.camel.thread.TestRunnable;

public class SpringTest {

	public static void main(String[] args) {
		AbstractXmlApplicationContext appContext = new ClassPathXmlApplicationContext("camel-context.xml");
		try {
			CamelContext camelContext = SpringCamelContext.springCamelContext(appContext);
			Main main = new Main();
			main.getCamelContexts().add(camelContext);
			TestRunnable runnable = new TestRunnable(main);
			Thread thread = new Thread(runnable);
	        thread.start();
	        boolean started = main.isStarted();
	        while(!started) {
	        	if(main.isStarted()) {
	        		started = main.isStarted();
	        	}
	        }
	        ProducerTemplate template = main.getCamelTemplate();
	        Persona persona = new Persona("Mario","Rossi");
	        //Object response1 = template.requestBody("direct:marshal",persona);
	        //Object response2 = template.requestBody("binding:jaxb:direct:marshal-endpoint",persona);
	        //Object response3 = template.requestBody("jaxbmc:FirstDirect",persona);
	        //Object response4 = template.requestBody("direct:start",persona);
	        //Object response5 = template.requestBody("direct:bar",persona);
	        Object response6 = template.requestBody("direct:inbox",persona);
			System.out.println(response6);
			MockEndpoint a = main.getCamelContexts().get(0).getEndpoint("mock:a", MockEndpoint.class);
			MockEndpoint b = main.getCamelContexts().get(0).getEndpoint("mock:b", MockEndpoint.class);
			MockEndpoint c = main.getCamelContexts().get(0).getEndpoint("mock:c", MockEndpoint.class);
			System.out.println(a.getExchanges().get(0).getIn().getBody());
			System.out.println(b.getExchanges().get(0).getIn().getBody());
			System.out.println(c.getExchanges().get(0).getIn().getBody());
			Object response7 = template.requestBody("direct:start-ref",persona);
			System.out.println(response7);
			Object response8 = template.requestBody("direct:start-custom",persona);
			System.out.println(response8);
			MockEndpoint first = main.getCamelContexts().get(0).getEndpoint("mock:first", MockEndpoint.class);
			MockEndpoint second = main.getCamelContexts().get(0).getEndpoint("mock:second", MockEndpoint.class);
			MockEndpoint third = main.getCamelContexts().get(0).getEndpoint("mock:third", MockEndpoint.class);
			System.out.println(first.getExchanges().get(0).getIn().getBody());
			System.out.println(second.getExchanges().get(0).getIn().getBody());
			System.out.println(third.getExchanges().get(0).getIn().getBody());
			Object response9 = template.requestBody("seda:start-pattern",persona);
			System.out.println(response9);
			MockEndpoint result = main.getCamelContexts().get(0).getEndpoint("mock:result", MockEndpoint.class);
			System.out.println(result);
			main.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
