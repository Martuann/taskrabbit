package org.elis.dao.definition;

import java.util.List;
import org.elis.progetto.model.Recensione;

public interface RecensioneDao {
	void insert(Recensione r);
	Recensione selectById(Long id);
	List<Recensione> selectAll();
	void update(Recensione r);
	void delete(Long id);
	List<Recensione> selectByIdUtenteRicevente(Long idUtenteRicevente);
}
