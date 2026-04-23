package org.elis.dao.definition;

import java.util.List;
import org.elis.progetto.model.Richiesta;

public interface RichiestaDao {
	void insert(Richiesta r) throws Exception;
	Richiesta selectById(Long id) throws Exception;
	List<Richiesta> selectAll()throws Exception;
	void update(Richiesta r)throws Exception;
	void delete(Long id)throws Exception;
	List<Richiesta> selectByIdUtenteRichiesto(Long idUtenteRichiesto)throws Exception;
	List<Richiesta> selectByIdUtenteRichiedente(Long idUtenteRichiedente)throws Exception;
	Boolean haLavorato(Long idUtenteRichiedente, Long idUtenteRichiesto) throws Exception;
}
