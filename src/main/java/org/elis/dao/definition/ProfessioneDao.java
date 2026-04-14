package org.elis.dao.definition;

import java.util.List;
import org.elis.progetto.model.Professione;

public interface ProfessioneDao {
	void insert(Professione p)throws Exception;
	Professione selectById(Long id)throws Exception;
	List<Professione> selectAll()throws Exception;
	void update(Professione p) throws Exception;
	void delete(Long id)throws Exception;
	List<Professione> selectbyUtente(Long id_utente) throws Exception;
}
