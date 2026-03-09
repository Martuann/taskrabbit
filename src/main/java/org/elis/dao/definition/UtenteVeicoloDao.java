package org.elis.dao.definition;

import java.math.BigDecimal;
import java.util.List;

import org.elis.progetto.model.UtenteVeicolo;

public interface UtenteVeicoloDao {
    void associaVeicolo(UtenteVeicolo uv) throws Exception;
    
    List<UtenteVeicolo> getDettagliVeicoliUtente(long idUtente) throws Exception;
    
    void aggiornaPrezzoServizio(long idUtente, long idVeicolo, BigDecimal nuovoPrezzo) throws Exception;
    
    void rimuoviAssociazione(long idUtente, long idVeicolo) throws Exception;

	List<UtenteVeicolo> selectByUtente(long idUtente);
}
