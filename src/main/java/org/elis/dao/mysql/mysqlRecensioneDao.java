package org.elis.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.elis.dao.definition.RecensioneDao;
import org.elis.progetto.model.Recensione;

public class mysqlRecensioneDao implements RecensioneDao {
	
	private DataSource dataSource;
	
	public mysqlRecensioneDao(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public void insert(Recensione r) {
		String sql = "INSERT INTO recensione (voto, descrizione, data, id_utenteScrittore, id_utenteRicevente) VALUES (?, ?, ?, ?, ?)";
		
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement insert = connection.prepareStatement(sql)) {
			
			insert.setInt(1, r.getVoto());
			insert.setString(2, r.getDescrizione());
			insert.setDate(3, Date.valueOf(r.getData()));
			insert.setLong(4, r.getId_utenteScrittore());
			insert.setLong(5, r.getId_utenteRicevente());
			
			insert.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Recensione selectById(Long id) {
		String sql = "SELECT * FROM recensione WHERE id = ?";
		
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement select = connection.prepareStatement(sql)) {
			
			select.setLong(1, id);
			
			try (ResultSet rs = select.executeQuery()) {
				if(rs.next()) {
					return new Recensione(
						rs.getInt("voto"),
						rs.getString("descrizione"),
						rs.getDate("data").toLocalDate(),
						rs.getLong("id_utenteScrittore"),
						rs.getLong("id_utenteRicevente")
					);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Recensione> selectAll() {
		String sql = "SELECT * FROM recensione";
		List<Recensione> recensioni = new ArrayList<>();
		
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement select = connection.prepareStatement(sql);
			 ResultSet rs = select.executeQuery()) {
			
			while(rs.next()) {
				recensioni.add(new Recensione(
					rs.getInt("voto"),
					rs.getString("descrizione"),
					rs.getDate("data").toLocalDate(),
					rs.getLong("id_utenteScrittore"),
					rs.getLong("id_utenteRicevente")
				));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return recensioni;
	}

	@Override
	public void update(Recensione r) {
		String sql = "UPDATE recensione SET voto=?, descrizione=?, data=?, id_utenteScrittore=?, id_utenteRicevente=? WHERE id=?";
		
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement update = connection.prepareStatement(sql)) {
			
			update.setInt(1, r.getVoto());
			update.setString(2, r.getDescrizione());
			update.setDate(3, Date.valueOf(r.getData()));
			update.setLong(4, r.getId_utenteScrittore());
			update.setLong(5, r.getId_utenteRicevente());
			update.setLong(6, r.getId());
			
			update.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Long id) {
		String sql = "DELETE FROM recensione WHERE id = ?";
		
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement delete = connection.prepareStatement(sql)) {
			
			delete.setLong(1, id);
			delete.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Recensione> selectByIdUtenteRicevente(Long idUtenteRicevente) {
		String sql = "SELECT * FROM recensione WHERE id_utenteRicevente = ?";
		List<Recensione> recensioni = new ArrayList<>();
		
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement select = connection.prepareStatement(sql)) {
			
			select.setLong(1, idUtenteRicevente);
			
			try (ResultSet rs = select.executeQuery()) {
				while(rs.next()) {
					recensioni.add(new Recensione(
						rs.getInt("voto"),
						rs.getString("descrizione"),
						rs.getDate("data").toLocalDate(),
						rs.getLong("id_utenteScrittore"),
						rs.getLong("id_utenteRicevente")
					));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return recensioni;
	}
}