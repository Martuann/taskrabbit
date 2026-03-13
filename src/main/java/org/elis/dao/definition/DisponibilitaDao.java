package org.elis.dao.definition;

import java.time.LocalDate;
import java.util.List;

import org.elis.progetto.model.Disponibilita;

public interface DisponibilitaDao {
    void aggiungiDisponibilita(Disponibilita d) throws Exception;

    List<Disponibilita> getDisponibilitaPerUtente(long idUtente) throws Exception;

    void rimuoviDisponibilita(long idDisponibilita) throws Exception;

    void aggiornaDisponibilita(Disponibilita d) throws Exception;
    
    void cancellaTuttePerUtente(long idUtente) throws Exception;

	void salvaOAggiorna(Disponibilita d) throws Exception;

	void rimuoviDisponibilitaByIdUtenteData(long idUtente, LocalDate data) throws Exception;
}
