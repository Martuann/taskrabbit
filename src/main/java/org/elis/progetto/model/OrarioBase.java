package org.elis.progetto.model;

import java.time.DayOfWeek;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
@Entity
@Table(name = "orario_base", uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"id_utente", "giornoSettimana"})})
public class OrarioBase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="giornoSettimana", columnDefinition = "TINYINT CHECK (giornoSettimana between 1 and 7 )", nullable = false)
    private DayOfWeek giornoSettimana;
	@Column(name="ora_inizio", nullable = false)
    private LocalTime oraInizio;
	@Column(name="ora_fine", nullable = false)

    private LocalTime oraFine;
	@ManyToOne
	@JoinColumn(name = "id_utente", nullable = false)
    private Utente utente;
	
	public OrarioBase() {
		
	}
	
	
	public OrarioBase(DayOfWeek giornoSettimana, LocalTime oraInizio, LocalTime oraFine, Utente utente) {
		super();
		this.giornoSettimana = giornoSettimana;
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.utente = utente;
	}


	public OrarioBase(long id, DayOfWeek giornoSettimana, LocalTime oraInizio, LocalTime oraFine, Utente utente) {
		super();
		this.id = id;
		this.giornoSettimana = giornoSettimana;
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.utente = utente;
	}


	public Utente getUtente() {
		return utente;
	}


	public void setUtente(Utente utente) {
		this.utente = utente;
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public DayOfWeek getGiornoSettimana() {
		return giornoSettimana;
	}
	public void setGiornoSettimana(DayOfWeek giornoSettimana) {
		this.giornoSettimana = giornoSettimana;
	}
	public LocalTime getOraInizio() {
		return oraInizio;
	}
	public void setOraInizio(LocalTime oraInizio) {
		this.oraInizio = oraInizio;
	}
	public LocalTime getOraFine() {
		return oraFine;
	}
	public void setOraFine(LocalTime oraFine) {
		this.oraFine = oraFine;
	}
	
	

}
