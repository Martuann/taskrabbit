package org.elis.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.elis.dao.mysql.MysqlImmagineDao;
import org.elis.dao.mysql.MysqlProfessioneDao;
import org.elis.dao.mysql.MysqlUtenteDAO;
import org.elis.progetto.model.Immagine;
import org.elis.progetto.model.Professione;
import org.elis.progetto.model.Ruolo;
import org.elis.progetto.model.StatoRichiesta;
import org.elis.progetto.model.Utente;
import org.elis.dao.definition.ImmagineDAO;
import org.elis.dao.definition.UtenteDao;
import org.elis.dao.definition.professioniDao;

public class MainTest {
	public static void main(String[] args) throws Exception {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/taskly", "root", "root")){
			UtenteDao utenteDao = new MysqlUtenteDAO(DataSourceConfig.getDataSource());
			utenteDao.aggiungiUtente(new Utente(
				"Martin","Ilardi","email1","telefono1","password1",LocalDate.of(2000,1,1),"1234567890abcdef",
				Ruolo.PROFESSIONISTA,1L));
			
		}
		catch(SQLException e) {
		e.printStackTrace();
		}
	}
}
