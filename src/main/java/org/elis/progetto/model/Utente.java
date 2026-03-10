package org.elis.progetto.model;

import java.time.LocalDate;

public class Utente {
	private Long id;                 
    private String nome;                 
    private String cognome;            
    private String email;            
    private String telefono;          
    private String password;         
    private LocalDate ddn;           
    private String cf;               
    private Ruolo ruolo;             
    private Long idCitta;
    
	public Utente() {};
	
	public Utente(Long id, String nome, String cognome, String email, String telefono, String password, LocalDate ddn, String cf,
			Ruolo ruolo, Long idCitta) {
		this(nome,cognome,email,telefono,password,ddn,cf,ruolo,idCitta);
		this.id = id;
	}
	
	public Utente(String nome, String cognome,String email,  String telefono, String password, LocalDate ddn, String cf,
			Ruolo ruolo, Long idCitta) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.telefono = telefono;
		this.password = password;
		this.ddn = ddn;
		this.cf = cf;
		this.ruolo = ruolo;
		this.idCitta = idCitta;
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

	public Long getIdCitta() {
		return idCitta;
	}

	public void setIdCitta(Long idCitta) {
		this.idCitta = idCitta;
	}
}
