package org.elis.dao.mysql;

import javax.sql.DataSource;

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
import org.elis.utilities.DataSourceConfig;

public class JdbcDaoFactory extends DaoFactory {
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

	public JdbcDaoFactory() {
		DataSource dataSource=DataSourceConfig.getDataSource();
		
		
		this.utenteDao=new MysqlUtenteDao(dataSource);
		this.cittaDao=new MysqlCittaDao(dataSource);
		this.disponibilitaDao=new MysqlDisponibilitaDao(dataSource);	
		this.immagineDao=new MysqlImmagineDao(dataSource);		
		this.orarioBaseDao=new MysqlOrarioBaseDao(dataSource);		
		this.professioneDao=new MysqlProfessioneDao(dataSource);	
		this.recensioneDao=new mysqlRecensioneDao(dataSource);		
		this.richiestaDao=new MysqlRichiestaDao(dataSource);		
		this.utenteProfessioneDao=new MysqlUtenteProfessioneDao(dataSource);		
		this.UtenteVeicoloDao=new MysqlUtenteVeicoloDao(dataSource);		
		this.veicoloDao=new MySqlVeicoloDao(dataSource);		


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
