package org.elis.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.elis.dao.definition.RecensioneDAO;
import org.elis.progetto.model.Recensione;

public class mysqlRecensioneDAO implements RecensioneDAO {
	Connection connection;

	public mysqlRecensioneDAO(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public void insert(Recensione r) {
		String sql = "INSERT INTO recensione (voto,descrizione,data,id_utenteScrittore,id_utenteRicevente) VALUES(?,?,?,?,?)";
		try (PreparedStatement insert = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			insert.setInt(1, r.getVoto());
			insert.setString(2, r.getDescrizione());
			insert.setObject(3, Date.valueOf(r.getData()));
			insert.setLong(4, r.getId_utenteScrittore());
			insert.setLong(5, r.getId_utenteRicevente());
			insert.executeUpdate();
	    }
		catch (Exception e) {
			 e.printStackTrace();
		}
	}

	@Override
	public Recensione selectById(Long id) {
		String sql = "SELECT * FROM recensione WHERE id="+id;
		try (PreparedStatement select = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			 ResultSet rs = select.executeQuery();
			 if(rs.next()) {
				 return new Recensione(
					rs.getInt("voto"),
					rs.getString("descrizione"),
					((Date) rs.getObject("data")).toLocalDate(),
					rs.getLong("id_utenteScrittore"),
					rs.getLong("id_utenteRicevente")
				 );
			 }
	     }
		catch (Exception e) {
			 e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Recensione> selectAll() {
		String sql = "SELECT * FROM recensione";
		List<Recensione> recensioni = new ArrayList<>();
		try (PreparedStatement select = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			 ResultSet rs = select.executeQuery();
			 while(rs.next()) {
				 recensioni.add(new Recensione(
					rs.getInt("voto"),
					rs.getString("descrizione"),
					((Date) rs.getObject("data")).toLocalDate(),
					rs.getLong("id_utenteScrittore"),
					rs.getLong("id_utenteRicevente")
				 ));
			 }
	     }
		catch (Exception e) {
			 e.printStackTrace();
		}
		return recensioni;
	}

	@Override
	public void update(Recensione r) {
		String sql = "UPDATE recensione SET voto=?,descrizione=?,data=?,id_utenteScrittore=?,id_utenteRicevente=? "+
					 "WHERE id="+r.getId();
		try (PreparedStatement update = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			update.setInt(1, r.getVoto());
			update.setString(2, r.getDescrizione());
			update.setObject(3, Date.valueOf(r.getData()));
			update.setLong(4, r.getId_utenteScrittore());
			update.setLong(5, r.getId_utenteRicevente());
			update.executeUpdate();
	     }
		catch (Exception e) {
			 e.printStackTrace();
		}
	}

	@Override
	public void delete(Long id) {
		String sql = "DELETE FROM recensione WHERE id="+id;
		try (PreparedStatement delete = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			delete.executeUpdate();
	     }
		catch (Exception e) {
			 e.printStackTrace();
		}
	}
	
	@Override
	public List<Recensione> selectByIdUtenteRicevente(Long idUtenteRicevente) {
		String sql = "SELECT * FROM recensione WHERE id_utenteRicevente="+idUtenteRicevente;
		List<Recensione> recensioni = new ArrayList<>();
		try (PreparedStatement select = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			 ResultSet rs = select.executeQuery();
			 while(rs.next()) {
				 recensioni.add(new Recensione(
					rs.getInt("voto"),
					rs.getString("descrizione"),
					((Date) rs.getObject("data")).toLocalDate(),
					rs.getLong("id_utenteScrittore"),
					rs.getLong("id_utenteRicevente")
				 ));
			 }
	     }
		catch (Exception e) {
			 e.printStackTrace();
		}
		return recensioni;
	}
}
