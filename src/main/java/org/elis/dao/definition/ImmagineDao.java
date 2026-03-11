package org.elis.dao.definition;

import java.util.List;
import org.elis.progetto.model.Immagine;

public interface ImmagineDao {
	void insert(Immagine i);
	Immagine selectById(Long id);
	List<Immagine> selectAll();
	void update(Immagine i);
	void delete(Long id);
	List<Immagine> selectByIdUtente(Long idUtente);
}
