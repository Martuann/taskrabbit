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
@Table(name="utente_veicolo")
public class UtenteVeicolo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name="id_utente", nullable = false)
	
    private Utente utente;
	@ManyToOne

	@JoinColumn(name="id_veicolo", nullable = false)
    private Veicolo veicolo;
	@Column(name = "aggiunta_servizio", 
			columnDefinition = "DECIMAL(10,2) NOT NULL DEFAULT 0.0 CHECK (aggiunta_servizio >= 0)") 
	private BigDecimal aggiuntaServizio;
	public UtenteVeicolo() {
		// TODO Auto-generated constructor stub
	}
	public UtenteVeicolo(Long id, Utente utente, Veicolo veicolo, BigDecimal aggiuntaServizio) {
		super();
		this.id = id;
		this.utente = utente;
		this.veicolo = veicolo;
		this.aggiuntaServizio = aggiuntaServizio;
	}
	
	public UtenteVeicolo(Utente utente,Veicolo veicolo, BigDecimal aggiuntaServizio) {
		super();
		this.utente = utente;
		this.veicolo = veicolo;
		this.aggiuntaServizio = aggiuntaServizio;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAggiuntaServizio() {
		return aggiuntaServizio;
	}
	public void setAggiuntaServizio(BigDecimal aggiuntaServizio) {
		this.aggiuntaServizio = aggiuntaServizio;
	}
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	public Veicolo getVeicolo() {
		return veicolo;
	}
	public void setVeicolo(Veicolo veicolo) {
		this.veicolo = veicolo;
	}

}