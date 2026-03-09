package org.elis.progetto.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class Richiesta {
	private Long id;
    private String descrizione;
    private LocalDate data;
    private LocalTime orarioInizio;
    private LocalTime orarioFine;
    private BigDecimal costoEffettivo;
    private String indirizzo;
    private StatoRichiesta stato;
    private Long idUtenteRichiedente;
    private Long idUtenteRichiesto;
    private Long idProfessione;
    private Long idVeicolo;
    
	public Richiesta() {};

	public Richiesta(String descrizione, LocalDate data, LocalTime orarioInizio, LocalTime orarioFine,
			BigDecimal costoEffettivo, String indirizzo, StatoRichiesta stato, Long idUtenteRichiedente,
			Long idUtenteRichiesto, Long idProfessione, Long idVeicolo) {
		this.descrizione = descrizione;
		this.data = data;
		this.orarioInizio = orarioInizio;
		this.orarioFine = orarioFine;
		this.costoEffettivo = costoEffettivo;
		this.indirizzo = indirizzo;
		this.stato = stato;
		this.idUtenteRichiedente = idUtenteRichiedente;
		this.idUtenteRichiesto = idUtenteRichiesto;
		this.idProfessione = idProfessione;
		this.idVeicolo = idVeicolo;
	}
	public Richiesta(Long id, String descrizione, LocalDate data, LocalTime orarioInizio, LocalTime orarioFine,
			BigDecimal costoEffettivo, String indirizzo, StatoRichiesta stato, Long idUtenteRichiedente,
			Long idUtenteRichiesto, Long idProfessione, Long idVeicolo) {
		this(descrizione,data,orarioInizio,orarioFine,costoEffettivo,indirizzo,stato,idUtenteRichiedente,idUtenteRichiesto,
			 idProfessione,idVeicolo);
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public LocalTime getOrarioInizio() {
		return orarioInizio;
	}
	public void setOrarioInizio(LocalTime orarioInizio) {
		this.orarioInizio = orarioInizio;
	}
	public LocalTime getOrarioFine() {
		return orarioFine;
	}
	public void setOrarioFine(LocalTime orarioFine) {
		this.orarioFine = orarioFine;
	}
	public BigDecimal getCostoEffettivo() {
		return costoEffettivo;
	}
	public void setCostoEffettivo(BigDecimal costoEffettivo) {
		this.costoEffettivo = costoEffettivo;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public StatoRichiesta getStato() {
		return stato;
	}
	public void setStato(StatoRichiesta stato) {
		this.stato = stato;
	}
	public Long getIdUtenteRichiedente() {
		return idUtenteRichiedente;
	}
	public void setIdUtenteRichiedente(Long idUtenteRichiedente) {
		this.idUtenteRichiedente = idUtenteRichiedente;
	}
	public Long getIdUtenteRichiesto() {
		return idUtenteRichiesto;
	}
	public void setIdUtenteRichiesto(Long idUtenteRichiesto) {
		this.idUtenteRichiesto = idUtenteRichiesto;
	}
	public Long getIdProfessione() {
		return idProfessione;
	}
	public void setIdProfessione(Long idProfessione) {
		this.idProfessione = idProfessione;
	}
	public Long getIdVeicolo() {
		return idVeicolo;
	}
	public void setIdVeicolo(Long idVeicolo) {
		this.idVeicolo = idVeicolo;
	}

}
