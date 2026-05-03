package org.elis.dao.definition;

import java.util.List;
import org.elis.progetto.model.UtenteProfessione;

public interface UtenteProfessioneDao {
	void insert(UtenteProfessione u)throws Exception;
	UtenteProfessione selectById(Long id)throws Exception;
	List<UtenteProfessione> selectAll()throws Exception;
	void update(UtenteProfessione u)throws Exception;
	void delete(Long id)throws Exception;
	UtenteProfessione selectByIdUtenteIdProfessione(Long idUtente, Long idProfessione)throws Exception;
	public List<UtenteProfessione> selectByUtente(long idUtente)throws Exception;
	Boolean checkTariffeCritiche(Long utenteId) throws Exception;
	public List<UtenteProfessione> selectByUtenteandtariffa(long idUtente)throws Exception;

}
