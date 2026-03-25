package org.elis.dao.definition;

import java.time.LocalDate;
import java.util.List;

import org.elis.progetto.model.Disponibilita;

public interface DisponibilitaDao {
    void aggiungiDisponibilita(Disponibilita d) throws Exception;

    List<Disponibilita> getDisponibilitaPerUtente(Long idUtente) throws Exception;

    void rimuoviDisponibilita(Long idDisponibilita) throws Exception;

    void aggiornaDisponibilita(Disponibilita d) throws Exception;
    
    void cancellaTuttePerUtente(Long idUtente) throws Exception;

	void salvaOAggiorna(Disponibilita d) throws Exception;

	void rimuoviDisponibilitaByIdUtenteData(Long idUtente, LocalDate data) throws Exception;

}
