package it.marco.camel.model;

public class HelloBean {
	
	private String greeting;

	public String getGreeting() {
		return greeting;
	}

	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}	
	
	public String sayHello(Persona persona) {
		return String.format("%s %s", greeting, persona.toString());
	}

}
