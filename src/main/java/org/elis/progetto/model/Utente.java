package org.elis.progetto.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name="utente")
public class Utente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;   
	@Column(nullable = false)
    private String nome;     
	@Column(nullable = false)

    private String cognome; 
	@Column(nullable = false,unique = true)

    private String email;            
	@Column(nullable = false,unique = true)

    private String telefono;          
	@Column(nullable = false)

    private String password; 
	@Column(nullable = false)

    private LocalDate ddn;
	@Column(nullable = false,unique = true)

    private String cf;             
	@Enumerated(EnumType.ORDINAL)
	@Column(columnDefinition = "TINYINT CHECK(ruolo IN (0,1,2))")
    private Ruolo ruolo;             
	@ManyToOne
    @JoinColumn(name = "id_citta", nullable = false)
    private Citta citta;
	@OneToMany(mappedBy = "utente") 
	private List<UtenteVeicolo> veicoli;

	@OneToMany(mappedBy = "utente")
	private List<Immagine> immagini;

	@OneToMany(mappedBy = "utente")
	private List<UtenteProfessione> professioni;

	@OneToMany(mappedBy = "utente")
	private List<OrarioBase> orariBase;
    
	@OneToMany(mappedBy = "utente")
	private List<Disponibilita> disponibilita;

	@OneToMany(mappedBy = "utenteRichiedente")
	private List<Richiesta> richiesteFatte;

	@OneToMany(mappedBy = "utenteRichiesto")
	private List<Richiesta> richiesteRicevute;

	@OneToMany(mappedBy = "utenteScrittore")
	private List<Recensione> recensioniScritte;

	@OneToMany(mappedBy = "utenteRicevente")
	private List<Recensione> recensioniRicevute;	
	
	
	
	
	
	public Utente() {};
	

	public Utente(Long id, String nome, String cognome, String email, String telefono, String password, LocalDate ddn,
			String cf, Ruolo ruolo, Citta citta) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.telefono = telefono;
		this.password = password;
		this.ddn = ddn;
		this.cf = cf;
		this.ruolo = ruolo;
		this.citta=citta;
	}



	public Citta getCitta() {
		return citta;
	}



	public void setCitta(Citta citta) {
		this.citta = citta;
	}



	public Utente(String nome, String cognome,String email,  String telefono, String password, LocalDate ddn, String cf,
			Ruolo ruolo, Citta citta) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.telefono = telefono;
		this.password = password;
		this.ddn = ddn;
		this.cf = cf;
		this.ruolo = ruolo;
		this.citta=citta;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getDdn() {
		return ddn;
	}

	public void setDdn(LocalDate ddn) {
		this.ddn = ddn;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public Ruolo getRuolo() {
		return ruolo;
	}

	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}
	public String getFotoProfiloPath(String contextPath) {
	    String path = null;
	    if (immagini != null && !immagini.isEmpty()) {
	        for (Immagine img : immagini) {
	            if (img.getIsFotoProfilo()) {
	                path = img.getPercorso();
	                break;
	            }
	        }
	    }

	    if (path == null || path.trim().isEmpty()) {
	        return contextPath + "/immagini/default-avatar.png";
	    }

	    if (!path.startsWith("http") && !path.startsWith("/")) {
	        return contextPath + "/" + path;
	    }

	    return path;
	}


	public List<UtenteVeicolo> getVeicoli() {
		return veicoli;
	}


	public void setVeicoli(List<UtenteVeicolo> veicoli) {
		this.veicoli = veicoli;
	}


	public List<Immagine> getImmagini() {
		return immagini;
	}


	public void setImmagini(List<Immagine> immagini) {
		this.immagini = immagini;
	}


	public List<UtenteProfessione> getProfessioni() {
		return professioni;
	}


	public void setProfessioni(List<UtenteProfessione> professioni) {
		this.professioni = professioni;
	}


	public List<OrarioBase> getOrariBase() {
		return orariBase;
	}


	public void setOrariBase(List<OrarioBase> orariBase) {
		this.orariBase = orariBase;
	}


	public List<Disponibilita> getDisponibilita() {
		return disponibilita;
	}


	public void setDisponibilita(List<Disponibilita> disponibilita) {
		this.disponibilita = disponibilita;
	}


	public List<Richiesta> getRichiesteFatte() {
		return richiesteFatte;
	}


	public void setRichiesteFatte(List<Richiesta> richiesteFatte) {
		this.richiesteFatte = richiesteFatte;
	}


	public List<Richiesta> getRichiesteRicevute() {
		return richiesteRicevute;
	}


	public void setRichiesteRicevute(List<Richiesta> richiesteRicevute) {
		this.richiesteRicevute = richiesteRicevute;
	}


	public List<Recensione> getRecensioniScritte() {
		return recensioniScritte;
	}


	public void setRecensioniScritte(List<Recensione> recensioniScritte) {
		this.recensioniScritte = recensioniScritte;
	}


	public List<Recensione> getRecensioniRicevute() {
		return recensioniRicevute;
	}


	public void setRecensioniRicevute(List<Recensione> recensioniRicevute) {
		this.recensioniRicevute = recensioniRicevute;
	}

}
