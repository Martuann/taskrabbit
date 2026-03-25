package org.elis.progetto.model;

import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.annotations.Check;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "disponibilita")
@Check(constraints = "ora_fine >= ora_inizio")
public class Disponibilita {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name="id_utente", nullable = false)
	private Utente utente;
	@Column(nullable = false)
	private LocalDate data;
	@Column(name = "ora_inizio", nullable = false)
	private LocalTime inizio;
	@Column(name = "ora_fine", nullable = false)
	private LocalTime fine;
	
	
	
	
	public Disponibilita(Utente utente, LocalDate data, LocalTime inizio, LocalTime fine) {
        this.utente = utente;
        this.data = data;
        this.inizio = inizio;
        this.fine = fine;
    }



	public Disponibilita() {
	}



	public Disponibilita(Long id, Utente utente, LocalDate data, LocalTime inizio, LocalTime fine) {
        this.id = id;
        this.utente = utente;
        this.data = data;
        this.inizio = inizio;
        this.fine = fine;
    }
	
	
    public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
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