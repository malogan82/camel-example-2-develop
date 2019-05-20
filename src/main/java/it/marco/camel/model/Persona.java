package it.marco.camel.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Persona")
@XmlAccessorType(XmlAccessType.FIELD)
public class Persona implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "nome")
	private String nome;
	
	@XmlElement(name = "cognome")
	private String cognome;
	
	public Persona() {
		
	}
	
	public Persona(String nome, String cognome) {
		this.nome = nome;
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s", nome, cognome);
	}

}
