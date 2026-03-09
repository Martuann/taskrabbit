package org.elis.dao.definition;

import java.util.List;
import org.elis.progetto.model.UtenteProfessione;

public interface UtenteProfessioneDAO {
	void insert(UtenteProfessione u);
	UtenteProfessione selectById(Long id);
	List<UtenteProfessione> selectAll();
	void update(UtenteProfessione u);
	void delete(Long id);
	UtenteProfessione selectByIdUtenteIdProfessione(Long idUtente, Long idProfessione);
	public List<UtenteProfessione> selectByUtente(long idUtente);

}
