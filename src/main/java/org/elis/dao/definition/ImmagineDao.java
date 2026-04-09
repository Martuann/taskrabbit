package org.elis.dao.definition;

import java.util.List;
import java.util.Map;

import org.elis.progetto.model.Immagine;

public interface ImmagineDao {
	void insert(Immagine i)throws Exception;
	Immagine selectById(Long id)throws Exception;
	List<Immagine> selectAll()throws Exception;
	void update(Immagine i)throws Exception;
	void delete(Long id)throws Exception;
	List<Immagine> selectByIdUtente(Long idUtente)throws Exception;
	Map<Long, String> getMappaFotoProfilo(List<Long> id_utenti) throws Exception;
	Immagine selectFotoProfiloByUtente(Long idUtente) throws Exception;
}
