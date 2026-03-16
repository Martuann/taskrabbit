package org.elis.dao.definition;

import java.time.LocalDate;
import java.util.List;

import org.elis.progetto.model.Professione;
import org.elis.progetto.model.Ruolo;
import org.elis.progetto.model.Utente;

public interface UtenteDao {

	
	


    Long aggiungiUtente(Utente utente) throws Exception;
    

    void aggiornaUtente(Utente utente) throws Exception;
    
    void rimuoviUtente(long id) throws Exception;



    Utente login(String email, String password) throws Exception;
    
    boolean presenzaUtente(String email) throws Exception;
    
    Utente ricercaTramiteEmail(String email) throws Exception;
    

    Utente ricercaPerId(long id) throws Exception;

    
    List<Utente> getUtentiProfessionisti() throws Exception;
    
  
    List<Utente> ricercaTramiteProfessione(String professione) throws Exception;


	List<Utente> ricercaLikeProfessione(String professione) throws Exception;








}
