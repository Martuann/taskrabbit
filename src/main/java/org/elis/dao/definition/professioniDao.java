package org.elis.dao.definition;

import java.util.List;
import org.elis.progetto.model.Professione;

public interface professioniDao {
	void insert(Professione p);
	Professione selectById(Long id);
	List<Professione> selectAll();
	void update(Professione p);
	void delete(Long id);
}
