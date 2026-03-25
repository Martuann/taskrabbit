package org.elis.progetto.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name="utente_Professione")
public class UtenteProfessione {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "id_utente", nullable = false)
    private Utente utente;
	@ManyToOne
	@JoinColumn(name = "id_professione", nullable = false)
    private Professione professione;
	@Column(name = "tariffaH", 
	        columnDefinition = "DECIMAL(10,2) NOT NULL DEFAULT 1.0 CHECK (tariffaH > 0)")  
	private BigDecimal tariffaH;
	public UtenteProfessione() {};
	
	public UtenteProfessione(Utente utente, Professione professione, BigDecimal tariffaH) {
		this.utente = utente;
		this.professione = professione;
		this.tariffaH = tariffaH;
	}
	
	public UtenteProfessione(Long id, Utente utente, Professione professione, BigDecimal tariffaH) {
		this(utente,professione,tariffaH);
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Professione getProfessione() {
		return professione;
	}

	public void setProfessione(Professione professione) {
		this.professione = professione;
	}

	public BigDecimal getTariffaH() {
		return tariffaH;
	}
	public void setTariffaH(BigDecimal tariffaH) {
		this.tariffaH = tariffaH;
	}


	
	
	
	
}
