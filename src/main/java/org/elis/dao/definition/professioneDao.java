package org.elis.dao.definition;

import java.util.List;
import org.elis.progetto.model.Professione;

public interface ProfessioneDao {
	void insert(Professione p);
	Professione selectById(Long id);
	List<Professione> selectAll();
	void update(Professione p) throws Exception;
	void delete(Long id);
}
