package org.elis.dao.mysql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.elis.dao.definition.UtenteProfessioneDAO;
import org.elis.progetto.model.UtenteProfessione;

public class MysqlUtenteProfessioneDao implements UtenteProfessioneDAO{
	Connection connection;

	public MysqlUtenteProfessioneDao(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public void insert(UtenteProfessione u) {
		String sql = "INSERT INTO utente_professione (id_utente,id_professione,tariffaH) VALUES(?,?,?)";
		try (PreparedStatement insert = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			 insert.setLong(1, u.getIdUtente());
			 insert.setLong(2, u.getIdProfessione());
			 insert.setObject(3, u.getTariffaH());
			 insert.executeUpdate();
	    }
		catch (Exception e) {
			 e.printStackTrace();
		}
	}

	@Override
	public UtenteProfessione selectById(Long id) {
		String sql = "SELECT * FROM utente_professione WHERE id="+id;
		try (PreparedStatement select = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			 ResultSet rs = select.executeQuery();
			 if(rs.next()) {
				 return new UtenteProfessione(
					rs.getLong("id"),
					rs.getLong("id_utente"),
					rs.getLong("id_professione"),
					(BigDecimal) rs.getObject("tariffaH")
				 );
			 }
	     }
		catch (Exception e) {
			 e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<UtenteProfessione> selectAll() {
		String sql = "SELECT * FROM utente_professione";
		List<UtenteProfessione> listaUtenteProfessione = new ArrayList<>();
		try (PreparedStatement select = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			 ResultSet rs = select.executeQuery();
			 while(rs.next()) {
				 listaUtenteProfessione.add(new UtenteProfessione(
					rs.getLong("id"),
					rs.getLong("id_utente"),
					rs.getLong("id_professione"),
					(BigDecimal) rs.getObject("tariffaH")
				 ));
			 }
	     }
		catch (Exception e) {
			 e.printStackTrace();
		}
		return listaUtenteProfessione;
	}

	@Override
	public void update(UtenteProfessione u) {
		String sql = "UPDATE utente_professione SET id_utente=?, id_professione=?, tariffaH=? WHERE id="+u.getId();
		try (PreparedStatement update = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			update.setLong(1, u.getIdUtente());
			update.setLong(2, u.getIdProfessione());
			update.setObject(3, u.getTariffaH());
			update.executeUpdate();
	     }
		catch (Exception e) {
			 e.printStackTrace();
		}
	}

	@Override
	public void delete(Long id) {
		String sql = "DELETE FROM professione WHERE id="+id;
		try (PreparedStatement delete = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			delete.executeUpdate();
	     }
		catch (Exception e) {
			 e.printStackTrace();
		}
	}
	
	@Override
	public UtenteProfessione selectByIdUtenteIdProfessione(Long idUtente, Long idProfessione) {
		String sql = "SELECT * FROM utente_professione WHERE id_utente="+idUtente+" AND id_professione="+idProfessione;
		try (PreparedStatement select = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			 ResultSet rs = select.executeQuery();
			 if(rs.next()) {
				 return new UtenteProfessione(
					rs.getLong("id"),
					rs.getLong("id_utente"),
					rs.getLong("id_professione"),
					(BigDecimal) rs.getObject("tariffaH")
				 );
			 }
	     }
		catch (Exception e) {
			 e.printStackTrace();
		}
		return null;
	}
}
