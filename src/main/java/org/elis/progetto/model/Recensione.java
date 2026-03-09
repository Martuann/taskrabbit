package org.elis.progetto.model;

import java.time.LocalDate;

public class Recensione {
	private Long id;
	private Integer voto;
	private String descrizione;
	private LocalDate data;
	private Long id_utenteScrittore;
	private Long id_utenteRicevente;
	
	public Recensione() {}

	public Recensione(Integer voto, String descrizione, LocalDate data, Long id_utenteScrittore, Long id_utenteRicevente) {
		super();
		this.voto = voto;
		this.descrizione = descrizione;
		this.data = data;
		this.id_utenteScrittore = id_utenteScrittore;
		this.id_utenteRicevente = id_utenteRicevente;
	}

	public Recensione(Long id, Integer voto, String descrizione, LocalDate data, Long id_utenteScrittore,
			Long id_utenteRicevente) {
		this(voto,descrizione,data,id_utenteScrittore,id_utenteRicevente);
		this.id = id;
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

	public Long getId_utenteScrittore() {
		return id_utenteScrittore;
	}

	public void setId_utenteScrittore(Long id_utenteScrittore) {
		this.id_utenteScrittore = id_utenteScrittore;
	}

	public Long getId_utenteRicevente() {
		return id_utenteRicevente;
	}

	public void setId_utenteRicevente(Long id_utenteRicevente) {
		this.id_utenteRicevente = id_utenteRicevente;
	}
}
