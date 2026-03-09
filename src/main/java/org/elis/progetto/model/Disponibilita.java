package org.elis.progetto.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Disponibilita {
	private Long id;
	private Long idUtente;
	private LocalDate data;
	private LocalTime inizio;
	private LocalTime fine;
	
	
	
	
	public Disponibilita(Long idUtente, LocalDate data, LocalTime inizio, LocalTime fine) {
        this.idUtente = idUtente;
        this.data = data;
        this.inizio = inizio;
        this.fine = fine;
    }



	public Disponibilita(Long id, Long idUtente, LocalDate data, LocalTime inizio, LocalTime fine) {
        this.id = id;
        this.idUtente = idUtente;
        this.data = data;
        this.inizio = inizio;
        this.fine = fine;
    }
	
	
    public Long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public LocalTime getInizio() {
		return inizio;
	}
	public void setInizio(LocalTime inizio) {
		this.inizio = inizio;
	}
	public LocalTime getFine() {
		return fine;
	}
	public void setFine(LocalTime fine) {
		this.fine = fine;
	}
}