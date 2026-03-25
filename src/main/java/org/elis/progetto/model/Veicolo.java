package org.elis.progetto.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "veicolo")
public class Veicolo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false,unique = true )
  private String categoria;
	@OneToMany(mappedBy = "veicolo")
	List<UtenteVeicolo> utenteVeicolo;
	public Veicolo() {
	}
	public Veicolo(Long id, String categoria) {
		super();
		this.id = id;
		this.categoria = categoria;
	}
	public Veicolo( String categoria) {
		super();
		
		this.categoria = categoria;
	}
	
	
	
	
	public List<UtenteVeicolo> getUtenteVeicolo() {
		return utenteVeicolo;
	}
	public void setUtenteVeicolo(List<UtenteVeicolo> utenteVeicolo) {
		this.utenteVeicolo = utenteVeicolo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

}
