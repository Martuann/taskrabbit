package org.elis.progetto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="immagine")
public class Immagine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private String percorso;
	@Column(nullable = false, columnDefinition="Default false" )
	private Boolean isFotoProfilo; 
	@ManyToOne
	@JoinColumn(name="id_utente", nullable = false)

	private Utente utente;

	public Immagine() {};

	public Immagine(String nome, String percorso, Boolean isFotoProfilo, Utente utente) {
		this.nome = nome;
		this.percorso = percorso;
		this.isFotoProfilo = isFotoProfilo;
		this.utente = utente;
	}

	public Immagine(Long id, String nome, String percorso, Boolean isFotoProfilo, Utente utente) {
			this(nome,percorso,isFotoProfilo,utente);
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

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}


}


