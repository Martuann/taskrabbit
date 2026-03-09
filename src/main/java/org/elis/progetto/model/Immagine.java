package org.elis.progetto.model;

public class Immagine {
    private Long id;
	private String nome;
	private String percorso; 
	private Boolean isFotoProfilo; 
	private Long idUtente;

	public Immagine() {};

	public Immagine(String nome, String percorso, Boolean isFotoProfilo, Long idUtente) {
		this.nome = nome;
		this.percorso = percorso;
		this.isFotoProfilo = isFotoProfilo;
		this.idUtente = idUtente;
	}

	public Immagine(Long id, String nome, String percorso, Boolean isFotoProfilo, Long idUtente) {
			this(nome,percorso,isFotoProfilo,idUtente);
			this.id=id;
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

	public String getPercorso() {
		return percorso;
	}

	public void setPercorso(String percorso) {
		this.percorso = percorso;
	}

	public Boolean getIsFotoProfilo() {
		return isFotoProfilo;
	}

	public void setIsFotoProfilo(Boolean isFotoProfilo) {
		this.isFotoProfilo = isFotoProfilo;
	}

	public Long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}
}


