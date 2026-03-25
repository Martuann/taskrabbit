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
	private UtenteDao utenteDao;
	private CittaDao cittaDao;
	private DisponibilitaDao disponibilitaDao;
	private ImmagineDao immagineDao;
	private OrarioBaseDao orarioBaseDao;
	private ProfessioneDao professioneDao;
	private RecensioneDao recensioneDao;
	private RichiestaDao richiestaDao;
	private UtenteProfessioneDao utenteProfessioneDao;
	private UtenteVeicoloDao UtenteVeicoloDao;
	private VeicoloDao veicoloDao;
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
		
		
		
EntityManagerFactory emf= Persistence.createEntityManagerFactory("taskly");	

this.utenteDao=new JpaUtenteDao(emf);
this.cittaDao=new JpaCittaDao(emf);
this.disponibilitaDao=new JpaDisponibilitaDao(emf);	
this.immagineDao=new JpaImmagineDao(emf);		
this.orarioBaseDao=new JpaOrarioBaseDao(emf);		
this.professioneDao=new JpaProfessioneDao(emf);	
this.recensioneDao=new JpaRecensioneDao(emf);		
this.richiestaDao=new JpaRichiestaDao(emf);		
this.utenteProfessioneDao=new JpaUtenteProfessioneDao(emf);		
this.UtenteVeicoloDao=new JpaUtenteVeicoloDao(emf);		
this.veicoloDao=new JpaVeicoloDao(emf);		


}
	public UtenteDao getUtenteDao() {
		return utenteDao;
	}
	public CittaDao getCittaDao() {
		return cittaDao;
	}
	public DisponibilitaDao getDisponibilitaDao() {
		return disponibilitaDao;
	}
	public ImmagineDao getImmagineDao() {
		return immagineDao;
	}
	public OrarioBaseDao getOrarioBaseDao() {
		return orarioBaseDao;
	}
	public ProfessioneDao getProfessioneDao() {
		return professioneDao;
	}
	public RecensioneDao getRecensioneDao() {
		return recensioneDao;
	}
	public RichiestaDao getRichiestaDao() {
		return richiestaDao;
	}
	public UtenteProfessioneDao getUtenteProfessioneDao() {
		return utenteProfessioneDao;
	}
	public UtenteVeicoloDao getUtenteVeicoloDao() {
		return UtenteVeicoloDao;
	}
	public VeicoloDao getVeicoloDao() {
		return veicoloDao;
	}
	
	
	
	
	

}
