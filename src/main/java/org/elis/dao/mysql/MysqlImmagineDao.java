package org.elis.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.elis.dao.definition.ImmagineDAO;
import org.elis.progetto.model.Immagine;

public class MysqlImmagineDao implements ImmagineDAO{
	Connection connection;

	public MysqlImmagineDao(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public void insert(Immagine i) {
		String sql = "INSERT INTO immagine (nome,percorso,isfotoprofilo,id_utente) VALUES(?,?,?,?)";
		 try (PreparedStatement insert = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			 insert.setString(1, i.getNome());
			 insert.setString(2, i.getPercorso());
			 insert.setBoolean(3, i.getIsFotoProfilo());
			 insert.setLong(4, i.getIdUtente());
			 insert.executeUpdate();
	     }
		 catch (Exception e) {
			 e.printStackTrace();
		 }
	}

	@Override
	public Immagine selectById(Long id) {
		String sql = "SELECT * FROM immagine WHERE id="+id;
		try (PreparedStatement select = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			 ResultSet rs = select.executeQuery();
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
		 catch (Exception e) {
			 e.printStackTrace();
		 }
		return null;
	}

	@Override
	public List<Immagine> selectAll() {
		String sql = "SELECT * FROM immagine";
		List<Immagine> immagini = new ArrayList<>();
		try (PreparedStatement select = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			 ResultSet rs = select.executeQuery();
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
		 catch (Exception e) {
			 e.printStackTrace();
		 }
		return immagini;
	}

	@Override
	public void update(Immagine i) {
		String sql = "UPDATE immagine SET nome=?, percorso=?, isfotoprofilo=?, id_utente=? WHERE id="+i.getId();
		try (PreparedStatement update = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			update.setString(1, i.getNome());
			update.setString(2, i.getPercorso());
			update.setBoolean(3, i.getIsFotoProfilo());
			update.setLong(4, i.getIdUtente());
			update.executeUpdate();
	     }
		 catch (Exception e) {
			 e.printStackTrace();
		 }
	}

	@Override
	public void delete(Long id) {
		String sql = "DELETE FROM immagine WHERE id="+id;
		try (PreparedStatement delete = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			delete.executeUpdate();
	     }
		 catch (Exception e) {
			 e.printStackTrace();
		 }
	}
	
	@Override
	public List<Immagine> selectByIdUtente(Long idUtente) {
		String sql = "SELECT * FROM immagine WHERE id_utente="+idUtente;
		List<Immagine> immagini = new ArrayList<>();
		try (PreparedStatement select = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			 ResultSet rs = select.executeQuery();
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
		 catch (Exception e) {
			 e.printStackTrace();
		 }
		return immagini;
	}
}
