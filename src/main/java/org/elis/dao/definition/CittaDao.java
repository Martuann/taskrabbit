package org.elis.dao.definition;

import java.util.List;
import org.elis.progetto.model.Citta;

public interface CittaDao {
    Long aggiungiCitta(Citta citta) throws Exception;

    Citta getCitta(Citta citta) throws Exception;


    boolean esisteCitta(Citta citta) throws Exception;

    List<Citta> getAllCitta() throws Exception;

    List<Citta> getCittaByProvincia(String provincia) throws Exception;

    void aggiornaCitta(Citta citta) throws Exception;

    void rimuoviCitta(Long id) throws Exception;

	Citta selectById(Long id);

	Citta getByName(String cittaDaPulire);
}
