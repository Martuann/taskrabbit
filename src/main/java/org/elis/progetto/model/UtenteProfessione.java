package org.elis.progetto.model;

import java.math.BigDecimal;

public class UtenteProfessione {
	private Long id;
    private Long idUtente;
    private Long idProfessione;
    private BigDecimal tariffaH;
	public UtenteProfessione() {};
	
	public UtenteProfessione(Long idUtente, Long idProfessione, BigDecimal tariffaH) {
		this.idUtente = idUtente;
		this.idProfessione = idProfessione;
		this.tariffaH = tariffaH;
	}
	
	public UtenteProfessione(Long id, Long idUtente, Long idProfessione, BigDecimal tariffaH) {
		this(idUtente,idProfessione,tariffaH);
		this.id = id;
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
	public Long getIdProfessione() {
		return idProfessione;
	}
	public void setIdProfessione(Long idProfessione) {
		this.idProfessione = idProfessione;
	}
	public BigDecimal getTariffaH() {
		return tariffaH;
	}
	public void setTariffaH(BigDecimal tariffaH) {
		this.tariffaH = tariffaH;
	}

}
