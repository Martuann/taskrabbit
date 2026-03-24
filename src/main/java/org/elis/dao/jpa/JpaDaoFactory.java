package org.elis.dao.jpa;

import java.util.HashMap;
import java.util.Map;

import org.elis.dao.definition.CittaDao;
import org.elis.dao.definition.DaoFactory;
import org.elis.dao.definition.DisponibilitaDao;
import org.elis.dao.definition.ImmagineDao;
import org.elis.dao.definition.OrarioBaseDao;
import org.elis.dao.definition.ProfessioneDao;
import org.elis.dao.definition.RecensioneDao;
import org.elis.dao.definition.RichiestaDao;
import org.elis.dao.definition.UtenteDao;
import org.elis.dao.definition.UtenteProfessioneDao;
import org.elis.dao.definition.UtenteVeicoloDao;
import org.elis.dao.definition.VeicoloDao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaDaoFactory extends DaoFactory {

	public JpaDaoFactory() {
		
		//se ci sono persone con il mac
		/*Map<String,String> properties = new HashMap()<String, String>();
		properties.put("jakarta.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
		properties.put("jakarta.persistence.jdbc.url", "jdbc:mysql://localhost:3306/jpa_test");
		properties.put("jakarta.persistence.jdbc.user", "root");
		properties.put("jakarta.persistence.jdbc.password", "root");
		properties.put("jakarta.persistence.schema-generation.database.action", "drop-and-create");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.format_sql", "true");*/
		
		
		
EntityManagerFactory emf= Persistence.createEntityManagerFactory("taskly");	}

	@Override
	public UtenteDao getUtenteDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CittaDao getCittaDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DisponibilitaDao getDisponibilitaDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImmagineDao getImmagineDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrarioBaseDao getOrarioBaseDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProfessioneDao getProfessioneDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RecensioneDao getRecensioneDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RichiestaDao getRichiestaDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UtenteProfessioneDao getUtenteProfessioneDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UtenteVeicoloDao getUtenteVeicoloDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VeicoloDao getVeicoloDao() {
		// TODO Auto-generated method stub
		return null;
	}

}
