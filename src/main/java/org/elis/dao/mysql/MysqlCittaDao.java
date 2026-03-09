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
	    String query = "select id, nome, provincia from citta order by nome asc";
	    
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
	
}
