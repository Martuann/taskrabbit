package org.elis.dao.definition;

import java.util.List;
import org.elis.progetto.model.Recensione;

public interface RecensioneDao {
	void insert(Recensione r)throws Exception;
	Recensione selectById(Long id)throws Exception;
	List<Recensione> selectAll()throws Exception;
	void update(Recensione r)throws Exception;
	void delete(Long id)throws Exception;
	List<Recensione> selectByIdUtenteRicevente(Long idUtenteRicevente)throws Exception;
	Boolean esisteRecensionePerRichiesta(Long idScrittore, Long idRicevente)throws Exception;
	List<Recensione> selectByIdUtenteScrittore(Long idUtenteScrittore)throws Exception;
}
