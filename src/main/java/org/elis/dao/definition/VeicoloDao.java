package org.elis.dao.definition;

import java.util.List;

import org.elis.progetto.model.Veicolo;

public interface VeicoloDao {
    
    void aggiungiVeicolo(Veicolo v) throws Exception;
    
    Veicolo ricercaPerId(long id) throws Exception;
    
    List<Veicolo> getAllVeicoli() throws Exception;

	List<Veicolo> getVeicolibyUtente(Long id_utente) throws Exception;

 
}
