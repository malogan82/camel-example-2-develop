package it.marco.camel.builder;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.builder.RouteBuilder;

import it.marco.camel.model.Persona;

public class TestRouteBuilder extends RouteBuilder {

	@SuppressWarnings("deprecation")
	@Override
	public void configure() throws Exception {
		from("direct:test-request1").
			convertBodyTo(String.class).
			log("${body}");
		
		from("direct:test-request2").
		log("BEFORE REMOVING HEADER: ${headers}").
		removeHeader("user").
		log("AFTER REMOVING HEADER: ${headers}").
		log("${body}");
		
		from("direct:test-request3").
			process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					exchange.setProperty("property1", "value1");
				}
			}).
			log("BEFORE REMOVING PROPERTY: ${exchangeProperty[property1]}").
			removeProperty("property1").
			log("AFTER REMOVING PROPERTY: ${exchangeProperty[property1]}").
			log("${body}");
		
		from("direct:test-request4").
		    log("BEFORE CHANGING BODY: ${body}").
		    setBody(constant("Giuseppe Verdi")).
		    log("AFTER CHANGING BODY: ${body}");
		
		from("direct:test-request5").
		    log("BEFORE ADDING HEADER: ${headers}").
		    setHeader("user",constant("Marco")). 
		    log("AFTER ADDING HEADER: ${headers}");
		
		from("direct:test-request6").
		    log("BEFORE ADDING HEADER: ${headers}").
		    setOutHeader("user",constant("Marco")). 
		    log("AFTER ADDING HEADER: ${headers}");
		
		from("direct:test-request7").
		    log("BEFORE SETTING PROPERTY: ${exchangeProperty[property1]}").
		    setProperty("property1", constant("value1")).
		    log("AFTER SETTING PROPERTY: ${exchangeProperty[property1]}");
		    
		 from("direct:test-request8").
		    transform(constant("Giuseppe Verdi")).
		    log("${body}");
		 
		 from("direct:test-request9").
		    transform().body().setBody(constant("Giuseppe Verdi")).
		    log("${body}");
		 
		 from("direct:test-request10").
		 	process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					exchange.setProperty("property1", "value1");
				}
			}).
		    log("BEFORE ADDING HEADER: ${headers}").
		    setHeader("body",body().append(" - Rossi Mario")).
		    setHeader("bodyAs",bodyAs(String.class)).
		    setHeader("header",header("user")).
		    setHeader("outBody",outBody()).
		    setHeader("property",property("property1")).
		    setHeader("regexReplaceAll1",regexReplaceAll(body(), "Mario Rossi", "Giovanni Bianchi")).
		    setHeader("regexReplaceAll2",regexReplaceAll(body(), "Mario Rossi", constant("Giuseppe Verdi"))).
		    setHeader("regexReplaceAll3",regexReplaceAll(body(), "Diego Armando Maradona", constant("Lionel Messi"))).
		    setHeader("sendTo",sendTo("direct:test-request11")).
		    setHeader("systemProperty1",systemProperty("java.home")).
		    setHeader("systemProperty2",systemProperty("test","test value")).
		    log("AFTER ADDING HEADER: ${headers}").
		    log("${body}");
		 
		 
		 from("direct:test-request11").
		    transform().body().setBody(constant("Dejan Savicevic")).
		    log("${body}");
		 
		 from("direct:test-request12").
		    choice().
			    when(body().convertTo(String.class).contains("Mario Rossi")).
			    	to("direct:mario-rossi").
			    otherwise().
			        log("${body}").
			    end();
		 
		 from("direct:test-request13").
		    choice().
			    when(body().convertToString().contains("Mario Rossi")).
			    	to("direct:mario-rossi").
			    otherwise().
			        log("${body}").
			    end();
		 
		 from("direct:test-request14").
		    choice().
			    when(body().convertToString().endsWith("Rossi")).
			    	to("direct:mario-rossi").
			    otherwise().
			        log("${body}").
			    end();
		 
		 from("direct:test-request15").
		    choice().
		    when(body().convertToString().in("Tony Stark","Steve Rogers")).
		    	to("direct:avengers").
		    otherwise().
		        log("${body}").
		    end();
		 
		 from("direct:test-request16").
		    choice().
		    when(body().convertToString().isEqualTo("Tony Stark")).
		    	to("direct:avengers").
		    otherwise().
		        log("${body}").
		    end();
		 
		 from("direct:test-request17").
		    choice().
		    when(body().convertTo(Integer.class).isGreaterThanOrEqualTo(45000)).
		    	to("direct:high-salaries").
		    otherwise().
		        log("${body}").
		    end();
		 
		 from("direct:test-request18").
		    choice().
		    when(body().isInstanceOf(Integer.class)).
		    	to("direct:integers").
		    otherwise().
		        log("${body}").
		    end();
		 
		 from("direct:test-request19").
		    choice().
		    when(body().isNotEqualTo(30000)).
		    	to("direct:developers").
		    otherwise().
		        log("${body}").
		    end();
		 
		 from("direct:test-request20").
		    choice().
		    when(body().isNotNull()).
		    	to("direct:not-null").
		    otherwise().
		        log("${body}").
		    end();
		 
		 from("direct:test-request21").
		    choice().
		    when(body().isNull()).
		    	to("direct:null").
		    otherwise().
		        log("${body}").
		    end();
		 
		 from("direct:test-request22").
		    transform(body().convertToString().prepend("Mr "));
		 
		 from("direct:test-request23").
		    transform(body().regexReplaceAll("Mario Rossi", "Giuseppe Verdi")).
		 	choice().
		    when(body().regex("Mario Rossi")).
		        transform(constant("Ciao Mario Rossi")).
			otherwise().
				log("${body}").
			end();
		 
		 from("direct:test-request24").
		 	transform(body().convertToString()).
		 	choice().
		    when(body().startsWith("Mario")).
		        log("Ciao Mario!").
			otherwise().
				log("${body}").
			end();
		 
		 from("direct:mario-rossi").
		    transform(constant("Ciao Mario, come stai?"));
		 
		 from("direct:high-salaries").
		    transform(constant("Congratulations!"));
		 
		 from("direct:not-null"). 
		    log("not null");
		 
		 from("direct:null"). 
		    log("null");
		 
		 from("direct:matches"). 
		    log("matches");
		 
		 from("direct:developers").
		    log("ahahahahah");
		
	}

}
