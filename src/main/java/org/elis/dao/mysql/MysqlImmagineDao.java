/*package org.elis.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;
import org.elis.dao.definition.ImmagineDao;
import org.elis.progetto.model.Immagine;

public class MysqlImmagineDao implements ImmagineDao {
	
	private DataSource dataSource;
	
	public MysqlImmagineDao(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public void insert(Immagine i) {
		String sql = "INSERT INTO immagine (nome, percorso, isfotoprofilo, id_utente) VALUES (?, ?, ?, ?)";
		
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement insert = connection.prepareStatement(sql)) {
			
			insert.setString(1, i.getNome());
			insert.setString(2, i.getPercorso());
			insert.setBoolean(3, i.getIsFotoProfilo());
			insert.setLong(4, i.getIdUtente());
			
			insert.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Immagine selectById(Long id) {
		String sql = "SELECT * FROM immagine WHERE id = ?";
		
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement select = connection.prepareStatement(sql)) {
			
			select.setLong(1, id);
			
			try (ResultSet rs = select.executeQuery()) {
				if(rs.next()) {
					return new Immagine(
						rs.getLong("id"),
						rs.getString("nome"),
						rs.getString("percorso"),
						rs.getBoolean("isfotoprofilo"),
						rs.getLong("id_utente")
					);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Immagine> selectAll() {
		String sql = "SELECT * FROM immagine";
		List<Immagine> immagini = new ArrayList<>();
		
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement select = connection.prepareStatement(sql);
			 ResultSet rs = select.executeQuery()) {
			
			while(rs.next()) {
				immagini.add(new Immagine(
					rs.getLong("id"),
					rs.getString("nome"),
					rs.getString("percorso"),
					rs.getBoolean("isfotoprofilo"),
					rs.getLong("id_utente")
				));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return immagini;
	}

	@Override
	public void update(Immagine i) {
		String sql = "UPDATE immagine SET nome=?, percorso=?, isfotoprofilo=?, id_utente=? WHERE id=?";
		
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement update = connection.prepareStatement(sql)) {
			
			update.setString(1, i.getNome());
			update.setString(2, i.getPercorso());
			update.setBoolean(3, i.getIsFotoProfilo());
			update.setLong(4, i.getIdUtente());
			update.setLong(5, i.getId());
			
			update.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Long id) {
		String sql = "DELETE FROM immagine WHERE id = ?";
		
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement delete = connection.prepareStatement(sql)) {
			
			delete.setLong(1, id);
			delete.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Immagine> selectByIdUtente(Long idUtente) {
		String sql = "SELECT * FROM immagine WHERE id_utente = ?";
		List<Immagine> immagini = new ArrayList<>();
		
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement select = connection.prepareStatement(sql)) {
			
			select.setLong(1, idUtente);
			
			try (ResultSet rs = select.executeQuery()) {
				while(rs.next()) {
					immagini.add(new Immagine(
						rs.getLong("id"),
						rs.getString("nome"),
						rs.getString("percorso"),
						rs.getBoolean("isfotoprofilo"),
						rs.getLong("id_utente")
					));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return immagini;
	}
	
	
	
	
	
	
	@Override
	public Map<Long, String> getMappaFotoProfilo(List<Long> id_utenti) throws Exception {
	    Map<Long, String> mappaFoto = new HashMap<>();
	    if (id_utenti == null || id_utenti.isEmpty()) return mappaFoto;

	    String placeholders = id_utenti.stream().map(id -> "?").collect(Collectors.joining(","));
	    String sql = "SELECT id_utente, percorso FROM immagine WHERE isfotoprofilo = true AND id_utente IN (" + placeholders + ")";

	    try (Connection conn = dataSource.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        for (int i = 0; i < id_utenti.size(); i++) {
	            ps.setLong(i + 1, id_utenti.get(i));
	        }

	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                mappaFoto.put(rs.getLong("id_utente"), rs.getString("percorso"));
	            }
	        }
	    }
	    return mappaFoto;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}*/