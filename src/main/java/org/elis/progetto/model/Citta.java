package org.elis.progetto.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name="citta",uniqueConstraints = {@UniqueConstraint(columnNames = {"nome","provincia"})})
public class Citta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable=false)
    private String nome;
	@Column(nullable=false)
	private String provincia;
	@OneToMany(mappedBy = "citta")
	private List<Utente> utenti;
	
    public Citta() {}; 
	public Citta(Long id, String nome,String provincia) {
		this(nome,provincia);
		this.id = id;
	}
	public Citta(String nome,String provincia) {
		super();
		this.nome = nome;
		this.provincia=provincia;
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
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public List<Utente> getUtenti() {
		return utenti;
	}
	public void setUtenti(List<Utente> utenti) {
		this.utenti = utenti;
	}
}
