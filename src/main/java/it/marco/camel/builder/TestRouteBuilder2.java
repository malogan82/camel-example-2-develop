package it.marco.camel.builder;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;

import it.marco.camel.model.Persona;

public class TestRouteBuilder2 extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		PropertiesComponent propertiesComponent = new PropertiesComponent();
		propertiesComponent.setLocation("it/marco/camel/fusesource/endpoints.properties");
		getContext().addComponent("properties", propertiesComponent);
		
		DataFormat jaxb = new JaxbDataFormat("it.marco.camel.model");
		
		from("direct:marshal").
		    marshal(jaxb).
		    convertBodyTo(String.class).
		    to("direct:unmarshal");
		
		from("direct:unmarshal").
		    unmarshal(jaxb);
		
//		from("direct:marshal-xstream"). 
//		    marshal(). 
//		    xstream(Persona.class).
//		    convertBodyTo(String.class).
//		    to("direct:unmarshal-stream");
//		
//		from("direct:unmarshal-stream").
//	    	unmarshal().
//	    	xstream(Persona.class);
		
		from("properties:{{marshal-xstream}}").
		 	marshal(). 
		    xstream(Persona.class).
		    convertBodyTo(String.class).
		    to("properties:{{unmarshal-xstream}}");
		
		from("properties:{{unmarshal-xstream}}").
    		unmarshal().
    		xstream(Persona.class);
		
		from("direct:start")
	    	.multicast().placeholder("stopOnException", "stop.flag")
	        .to("mock:a").throwException(new IllegalAccessException("Damn")).to("mock:b");
		
		from("direct:start-default")
	    	.transform().simple("Hi ${body} do you think ${properties:cheese.quote:cheese is good}?");
		
		from("direct:start-override")
			.transform().simple("Hi ${body}. ${properties-location:it/marco/camel/fusesource/bar.properties:bar.quote}.");
		
	}
	
	

}
