package org.elis.dao.definition;

import java.math.BigDecimal;
import java.util.List;

import org.elis.progetto.model.UtenteVeicolo;

public interface UtenteVeicoloDao {
    void associaVeicolo(UtenteVeicolo uv) throws Exception;
    
    List<UtenteVeicolo> getDettagliVeicoliUtente(Long idUtente) throws Exception;
    
    void aggiornaPrezzoServizio(Long idUtente, Long idVeicolo, BigDecimal nuovoPrezzo) throws Exception;
    
    void rimuoviAssociazione(Long idUtente, Long idVeicolo) throws Exception;

	List<UtenteVeicolo> selectByUtente(Long idUtente)throws Exception;

	UtenteVeicolo selectByUtenteEVeicolo(Long idUtente, Long idVeicolo) throws Exception;
}
