package org.elis.progetto.model;

public class Veicolo {
	private Long id;
    private String categoria;
	public Veicolo() {
		// TODO Auto-generated constructor stub
	}
	public Veicolo(Long id, String categoria) {
		super();
		this.id = id;
		this.categoria = categoria;
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
