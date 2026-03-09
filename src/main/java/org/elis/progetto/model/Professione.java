package org.elis.progetto.model;

public class Professione {
	private Long id;
    private String nome;
    
	public Professione() {};
	
	public Professione(String nome) {
		this.nome = nome;
	}
	
	public Professione(Long id, String nome) {
		this(nome);
		this.id = id;
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
}
