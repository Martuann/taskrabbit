package org.elis.dao.definition;

import java.util.List;
import org.elis.progetto.model.Richiesta;

public interface RichiestaDao {
	void insert(Richiesta r);
	Richiesta selectById(Long id);
	List<Richiesta> selectAll();
	void update(Richiesta r);
	void delete(Long id);
}
