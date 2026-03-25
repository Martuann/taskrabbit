package org.elis.dao.definition;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.elis.progetto.model.Professione;
import org.elis.progetto.model.Ruolo;
import org.elis.progetto.model.Utente;

public interface UtenteDao {





    Long aggiungiUtente(Utente utente) throws Exception;


    Utente aggiornaUtente(Utente utente) throws Exception;

    void rimuoviUtente(Long id) throws Exception;



    Utente login(String email, String password) throws Exception;

    boolean presenzaUtente(String email) throws Exception;

    Utente ricercaTramiteEmail(String email) throws Exception;


    Utente ricercaPerId(Long id) throws Exception;


    List<Utente> getUtentiProfessionisti() throws Exception;


    List<Utente> ricercaTramiteProfessione(String professione) throws Exception;



    Map<Utente, String> getRecensoriConFoto(Long id_utenteRicevente) throws Exception;
	List<Utente> ricercaLikeProfessione(String professione) throws Exception;


	boolean presenzaCodiceFiscale(String codiceFiscale)throws Exception;


	boolean presenzaTelefono(String telefono) throws Exception;




	List<Utente> listaUtentiRichiestiDaRichiedente(Long id_utenteRichiedente) throws Exception;








}
