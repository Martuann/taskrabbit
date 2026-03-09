package org.elis.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.elis.dao.definition.professioniDao;
import org.elis.progetto.model.Professione;

public class MysqlProfessioneDao implements professioniDao{
	Connection connection;

	public MysqlProfessioneDao(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public void insert(Professione p) {
		String sql = "INSERT INTO professione (nome) VALUES(?)";
		try (PreparedStatement insert = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			 insert.setString(1, p.getNome());
			 insert.executeUpdate();
	    }
		catch (Exception e) {
			 e.printStackTrace();
		}
	}

	@Override
	public Professione selectById(Long id) {
		String sql = "SELECT * FROM professione WHERE id="+id;
		try (PreparedStatement select = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			 ResultSet rs = select.executeQuery();
			 if(rs.next()) {
				 return new Professione(
					rs.getLong("id"),
					rs.getString("nome")
				 );
			 }
	     }
		 catch (Exception e) {
			 e.printStackTrace();
		 }
		return null;
	}

	@Override
	public List<Professione> selectAll() {
		String sql = "SELECT * FROM professione";
		List<Professione> professioni = new ArrayList<>();
		try (PreparedStatement select = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			 ResultSet rs = select.executeQuery();
			 while(rs.next()) {
				 professioni.add(new Professione(
					rs.getLong("id"),
					rs.getString("nome")
				 ));
			 }
	     }
		catch (Exception e) {
			 e.printStackTrace();
		}
		return professioni;
	}

	@Override
	public void update(Professione p) {
		String sql = "UPDATE professione SET nome=? WHERE id="+p.getId();
		try (PreparedStatement update = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			update.setString(1, p.getNome());
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
}
