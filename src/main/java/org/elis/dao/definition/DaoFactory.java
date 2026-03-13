package org.elis.dao.definition;

import org.elis.dao.mysql.JdbcDaoFactory;

public abstract class DaoFactory {

	protected DaoFactory() {
		// TODO Auto-generated constructor stub
	}
private static final DaoFactory INSTANCE;

static{
	String FACTORY_IMPLEMENTATION= System.getenv("FACTORY_IMPLEMENTATION");
	
	INSTANCE=switch(FACTORY_IMPLEMENTATION) {
	case "JDBC"-> new JdbcDaoFactory();
	default -> throw new IllegalStateException("Errore durante il caricamento della factory");
	
	};
}

public static DaoFactory getInstance() {
	return INSTANCE;
	
}






public abstract UtenteDao getUtenteDao();
public abstract CittaDao getCittaDao();
public abstract DisponibilitaDao getDisponibilitaDao();
public abstract ImmagineDao getImmagineDao();
public abstract OrarioBaseDao getOrarioBaseDao();
public abstract ProfessioneDao getProfessioneDao();
public abstract RecensioneDao getRecensioneDao();
public abstract RichiestaDao getRichiestaDao();
public abstract UtenteProfessioneDao getUtenteProfessioneDao();
public abstract UtenteVeicoloDao getUtenteVeicoloDao();
public abstract VeicoloDao getVeicoloDao();


}
