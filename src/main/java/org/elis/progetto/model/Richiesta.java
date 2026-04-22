package org.elis.progetto.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name="richiesta")
public class Richiesta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "descrizione", 
	        columnDefinition = "text NOT NULL")  
    private String descrizione;
	@Column(nullable = false)
    private LocalDate data;
	@Column(nullable = false)

    private LocalTime orarioInizio;
	@Column(nullable = false)

    private LocalTime orarioFine;
	@Column(name = "costo_effettivo", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal costoEffettivo;
	@Column(nullable = false)

    private String indirizzo;
	@Enumerated(EnumType.ORDINAL)
    @Column(nullable = false, columnDefinition = "TINYINT DEFAULT 0 CHECK (stato BETWEEN 0 AND 4)")

    private StatoRichiesta stato;
	@ManyToOne	
	@JoinColumn(name="id_utenteRichiedente",nullable = false)

    private Utente utenteRichiedente;
	@ManyToOne	
	@JoinColumn(name="id_utenteRichiesto",nullable = false)
    private Utente utenteRichiesto;
	@ManyToOne	
	@JoinColumn(name="id_professione",nullable = false)
    private Professione professione;
	@ManyToOne	
	@JoinColumn(name="id_veicolo")
    private Veicolo veicolo;
    
	public Richiesta() {};

	public Richiesta(String descrizione, LocalDate data, LocalTime orarioInizio, LocalTime orarioFine,
			BigDecimal costoEffettivo, String indirizzo, StatoRichiesta stato, Utente utenteRichiedente,
			Utente utenteRichiesto, Professione professione, Veicolo veicolo) {
		this.descrizione = descrizione;
		this.data = data;
		this.orarioInizio = orarioInizio;
		this.orarioFine = orarioFine;
		this.costoEffettivo = costoEffettivo;
		this.indirizzo = indirizzo;
		this.stato = stato;
		this.utenteRichiedente = utenteRichiedente;
		this.utenteRichiesto = utenteRichiesto;
		this.professione = professione;
		this.veicolo = veicolo;
	}

	
	public Richiesta(Long id, String descrizione, LocalDate data, LocalTime orarioInizio, LocalTime orarioFine,
			BigDecimal costoEffettivo, String indirizzo, StatoRichiesta stato, Utente utenteRichiedente,
			Utente utenteRichiesto, Professione professione, Veicolo veicolo) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.data = data;
		this.orarioInizio = orarioInizio;
		this.orarioFine = orarioFine;
		this.costoEffettivo = costoEffettivo;
		this.indirizzo = indirizzo;
		this.stato = stato;
		this.utenteRichiedente = utenteRichiedente;
		this.utenteRichiesto = utenteRichiesto;
		this.professione = professione;
		this.veicolo = veicolo;
	}

	public Utente getUtenteRichiedente() {
		return utenteRichiedente;
	}

	public void setUtenteRichiedente(Utente utenteRichiedente) {
		this.utenteRichiedente = utenteRichiedente;
	}

	public Utente getUtenteRichiesto() {
		return utenteRichiesto;
	}

	public void setUtenteRichiesto(Utente utenteRichiesto) {
		this.utenteRichiesto = utenteRichiesto;
	}

	public Professione getProfessione() {
		return professione;
	}

	public void setProfessione(Professione professione) {
		this.professione = professione;
	}

	public Veicolo getVeicolo() {
		return veicolo;
	}

	public void setVeicolo(Veicolo veicolo) {
		this.veicolo = veicolo;
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

}
