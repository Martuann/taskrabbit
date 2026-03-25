package org.elis.progetto.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "recensione")
public class Recensione {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;
	@Column(name="voto", columnDefinition = "TINYINT check (voto between 1 and 5)", nullable = false)
	private Integer voto;
	@Column(columnDefinition = "TEXT", nullable = false)
	private String descrizione;
	@Column(nullable = false)
	private LocalDate data;
	@ManyToOne
	@JoinColumn(name ="id_utenteScrittore", nullable = false)
	private Utente utenteScrittore;
	@ManyToOne
	@JoinColumn(name ="id_utenteRicevente", nullable = false)

	private Utente utenteRicevente;
	
	public Recensione() {}

	

	public Recensione(Integer voto, String descrizione, LocalDate data, Utente utenteScrittore,
			Utente utenteRicevente) {
		super();
		this.voto = voto;
		this.descrizione = descrizione;
		this.data = data;
		this.utenteScrittore = utenteScrittore;
		this.utenteRicevente = utenteRicevente;
	}



	public Recensione(Long id, Integer voto, String descrizione, LocalDate data, Utente utenteScrittore,
			Utente utenteRicevente) {
		super();
		this.id = id;
		this.voto = voto;
		this.descrizione = descrizione;
		this.data = data;
		this.utenteScrittore = utenteScrittore;
		this.utenteRicevente = utenteRicevente;
	}



	public Utente getUtenteScrittore() {
		return utenteScrittore;
	}



	public void setUtenteScrittore(Utente utenteScrittore) {
		this.utenteScrittore = utenteScrittore;
	}



	public Utente getUtenteRicevente() {
		return utenteRicevente;
	}



	public void setUtenteRicevente(Utente utenteRicevente) {
		this.utenteRicevente = utenteRicevente;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVoto() {
		return voto;
	}

	public void setVoto(Integer voto) {
		this.voto = voto;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}


}
