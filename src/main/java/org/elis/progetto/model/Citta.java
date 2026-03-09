package org.elis.progetto.model;

public class Citta {
	private Long id;
    private String nome;
	private String provincia;
	
    public Citta() {}; 
	public Citta(Long id, String nome,String provincia) {
		this(nome,provincia);
		this.id = id;
	}
	public Citta(String nome,String provincia) {
		super();
		this.nome = nome;
		this.provincia=provincia;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
}
