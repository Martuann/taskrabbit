package org.elis.dao.definition;

import java.util.List;

import org.elis.progetto.model.Disponibilita;

public interface DisponibilitaDao {
    void aggiungiDisponibilita(Disponibilita d) throws Exception;

    List<Disponibilita> getDisponibilitaPerUtente(long idUtente) throws Exception;

    void rimuoviDisponibilita(long idDisponibilita) throws Exception;

    void aggiornaDisponibilita(Disponibilita d) throws Exception;
    
    void cancellaTuttePerUtente(long idUtente) throws Exception;
}
