package org.elis.progetto.model;

import java.math.BigDecimal;

public class UtenteVeicolo {
	private Long id;
    private Long idUtente;
    private Long idVeicolo;
    private BigDecimal aggiuntaServizio;
	public UtenteVeicolo() {
		// TODO Auto-generated constructor stub
	}
	public UtenteVeicolo(Long id, Long idUtente, Long idVeicolo, BigDecimal aggiuntaServizio) {
		super();
		this.id = id;
		this.idUtente = idUtente;
		this.idVeicolo = idVeicolo;
		this.aggiuntaServizio = aggiuntaServizio;
	}
	
	public UtenteVeicolo(Long idUtente, Long idVeicolo, BigDecimal aggiuntaServizio) {
		super();
		this.idUtente = idUtente;
		this.idVeicolo = idVeicolo;
		this.aggiuntaServizio = aggiuntaServizio;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}
	public Long getIdVeicolo() {
		return idVeicolo;
	}
	public void setIdVeicolo(Long idVeicolo) {
		this.idVeicolo = idVeicolo;
	}
	public BigDecimal getAggiuntaServizio() {
		return aggiuntaServizio;
	}
	public void setAggiuntaServizio(BigDecimal aggiuntaServizio) {
		this.aggiuntaServizio = aggiuntaServizio;
	}

}