package org.elis.dao.definition;

import java.util.List;
import java.util.Map;

import org.elis.progetto.model.Immagine;

public interface ImmagineDao {
	void insert(Immagine i);
	Immagine selectById(Long id);
	List<Immagine> selectAll();
	void update(Immagine i);
	void delete(Long id);
	List<Immagine> selectByIdUtente(Long idUtente);
	Map<Long, String> getMappaFotoProfilo(List<Long> id_utenti) throws Exception;
}
