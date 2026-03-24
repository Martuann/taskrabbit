package org.elis.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.elis.dao.definition.CittaDao;
import org.elis.progetto.model.Citta;

public class MysqlCittaDao implements CittaDao{
private DataSource dataSource;
	public MysqlCittaDao(DataSource dataSource) {
		this.dataSource=dataSource;

	}

	public Long aggiungiCitta(Citta citta)throws Exception
	{
		String query ="insert into citta (nome,provincia) values (?,?)";

		try(Connection c=dataSource.getConnection();
				PreparedStatement st=c.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
		st.setString(1,citta.getNome());
		st.setString(2, citta.getProvincia());
		st.executeUpdate();

		try (ResultSet rs = st.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
            throw new Exception("Errore nel recupero dell'ID generato");
        }

	}}


	public Long getIdCitta(Citta citta) throws Exception{

	String query ="select id from citta where nome = ? and provincia = ?";

		try(Connection c=dataSource.getConnection();
				PreparedStatement selectStatement=c.prepareStatement(query)){
			selectStatement.setString(1,citta.getNome());
			selectStatement.setString(2, citta.getProvincia());
			try(ResultSet result= selectStatement.executeQuery();){
			if(result.next()) {
				return result.getLong("id");

			}
			else throw new Exception("città non trovata nel database");
	}
		}


	}
	public boolean esisteCitta(Citta citta) throws Exception {
	    String query = "select count(*) from citta where nome = ? and provincia = ?";

	    try (Connection c = dataSource.getConnection();
	         PreparedStatement selectStatement = c.prepareStatement(query)) {

	    	selectStatement.setString(1, citta.getNome());
	    	selectStatement.setString(2, citta.getProvincia());

	        try (ResultSet result = selectStatement.executeQuery()) {
	            if (result.next()) {
	                return result.getInt(1) > 0;
	            }
	        }
	    }
	    return false;
	}




	public Long getOrCreateCitta(Citta citta) throws Exception {
	    try {
	        return getIdCitta(citta);
	    } catch (Exception e) {
	        return aggiungiCitta(citta);
	    }
	}

	@Override
	public Citta selectById(Long id) {
		String sql = "SELECT * FROM citta WHERE id = "+id;

		try (Connection connection = dataSource.getConnection();
			 PreparedStatement select = connection.prepareStatement(sql)) {

			try (ResultSet rs = select.executeQuery()) {
				if(rs.next()) {
					return new Citta(
							rs.getString("nome"),
							rs.getString("provincia")
						);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}



	public List<Citta> getCittaByProvincia(String provincia) throws Exception {
	    List<Citta> lista = new ArrayList<Citta>();
	    String query = "select id, nome from citta where provincia = ? order by nome";

	    try (Connection connection = dataSource.getConnection();
	         PreparedStatement selectStatement = connection.prepareStatement(query)) {
	    	selectStatement.setString(1, provincia);
	        try (ResultSet result = selectStatement.executeQuery()) {
	            while (result.next()) {
	                Citta citta = new Citta();
	                citta.setId(result.getLong("id"));
	                citta.setNome(result.getString("nome"));
	                citta.setProvincia(provincia);
	                lista.add(citta);
	            }
	        }
	    }
	    return lista;
	}

	public void aggiornaCitta(Citta citta) throws Exception {
	    String query = "update citta set nome = ?, provincia = ? where id = ?";
	    try (Connection connection = dataSource.getConnection();
	         PreparedStatement selectStatement = connection.prepareStatement(query)) {
	    	selectStatement.setString(1, citta.getNome());
	    	selectStatement.setString(2, citta.getProvincia());
	    	selectStatement.setLong(3, citta.getId());
	    	selectStatement.executeUpdate();
	    }
	}

	public void rimuoviCitta(Long id) throws Exception {
	    String query = "delete from citta where id = ?";
	    try (Connection connection = dataSource.getConnection();
	         PreparedStatement selectStatement = connection.prepareStatement(query)) {
	    	selectStatement.setLong(1, id);
	    	selectStatement.executeUpdate();
	    }
	}

	public List<Citta> getAllCitta() throws Exception {
		List<Citta> lista = new ArrayList<>();
	    String query = "SELECT * FROM citta ORDER BY nome ASC";

	    try (Connection connection = dataSource.getConnection();
	         PreparedStatement selectStatement = connection.prepareStatement(query);
	         ResultSet result = selectStatement.executeQuery()) {

	        while (result.next()) {
	            Citta citta = new Citta();
	            citta.setId(result.getLong("id"));
	            citta.setNome(result.getString("nome"));
	            citta.setProvincia(result.getString("provincia"));
	            lista.add(citta);
	        }
	    }
	    return lista;
	}
	@Override
	public Citta getByName(String nome) {
	    String query = "SELECT id, nome, provincia FROM citta WHERE nome = ?";

	    try (Connection c = dataSource.getConnection();
	         PreparedStatement ps = c.prepareStatement(query)) {

	        ps.setString(1, nome);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                Citta citta = new Citta();
	                citta.setId(rs.getLong("id"));
	                citta.setNome(rs.getString("nome"));
	                citta.setProvincia(rs.getString("provincia"));
	                return citta;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

}
