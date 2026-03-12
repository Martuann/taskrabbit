package org.elis.progetto.model;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class OrarioBase {
	private long id;
    private DayOfWeek giornoSettimana;
    private LocalTime oraInizio;
    private LocalTime oraFine;
    private Long idUtente;
	public OrarioBase() {
		
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
	public Long getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}
	public OrarioBase(long id, DayOfWeek giornoSettimana, LocalTime oraInizio, LocalTime oraFine, Long idUtente) {
		super();
		this.id = id;
		this.giornoSettimana = giornoSettimana;
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.idUtente = idUtente;
	}
	public OrarioBase(DayOfWeek giornoSettimana, LocalTime oraInizio, LocalTime oraFine, Long idUtente) {
		super();
		this.giornoSettimana = giornoSettimana;
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.idUtente = idUtente;
	}
	

}
