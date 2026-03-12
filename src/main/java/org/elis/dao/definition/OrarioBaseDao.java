package org.elis.dao.definition;

import java.util.List;

import org.elis.progetto.model.OrarioBase;

public interface OrarioBaseDao {

	    
	    void salvaOrario(OrarioBase orario) throws Exception;
	    
	    List<OrarioBase> getOrariByUtente(long idUtente) throws Exception;
	    
	    void eliminaOrario(long idUtente, int giornoSettimana) throws Exception;
	
	
	
	
	
	
	
}
