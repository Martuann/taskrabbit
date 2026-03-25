/*package org.elis.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.elis.dao.definition.OrarioBaseDao;
import org.elis.progetto.model.OrarioBase;

public class MysqlOrarioBaseDao implements OrarioBaseDao{
   private DataSource dataSource;

	    public MysqlOrarioBaseDao(DataSource dataSource) {
	        this.dataSource = dataSource;
	    }

	    @Override
	    public void salvaOrario(OrarioBase orario) throws Exception {
	        String query = "INSERT INTO orario_base (giorno_settimana, ora_inizio, ora_fine, id_utente) " +
	                       "VALUES (?, ?, ?, ?) " +
	                       "ON DUPLICATE KEY UPDATE ora_inizio = VALUES(ora_inizio), ora_fine = VALUES(ora_fine)";

	        try (Connection connection = dataSource.getConnection(); 
	             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            
	        	preparedStatement.setInt(1, orario.getGiornoSettimana().getValue());
	        	preparedStatement.setTime(2, Time.valueOf(orario.getOraInizio()));
	        	preparedStatement.setTime(3, Time.valueOf(orario.getOraFine()));
	        	preparedStatement.setLong(4, orario.getIdUtente());

	        	preparedStatement.executeUpdate();
	        }
	    }

	    @Override
	    public List<OrarioBase> getOrariByUtente(long idUtente) throws Exception {
	        List<OrarioBase> lista = new ArrayList<>();
	        String query = "SELECT * FROM orario_base WHERE id_utente = ? ORDER BY giorno_settimana";

	        try (Connection connection = dataSource.getConnection(); 
	             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            
	        	preparedStatement.setLong(1, idUtente);
	            ResultSet resultSet = preparedStatement.executeQuery();

	            while (resultSet.next()) {
	                OrarioBase orarioBase = new OrarioBase();
	                orarioBase.setId(resultSet.getLong("id"));
	                orarioBase.setGiornoSettimana(DayOfWeek.of(resultSet.getInt("giorno_settimana")));
	                orarioBase.setOraInizio(resultSet.getTime("ora_inizio").toLocalTime());
	                orarioBase.setOraFine(resultSet.getTime("ora_fine").toLocalTime());
	                
	                lista.add(orarioBase);
	            }
	        }
	        return lista;
	    }

	    @Override
	    public void eliminaOrario(long idUtente, int giornoSettimana) throws Exception {
	        String query = "DELETE FROM orario_base WHERE id_utente = ? AND giorno_settimana = ?";
	        try (Connection c = dataSource.getConnection(); 
	             PreparedStatement ps = c.prepareStatement(query)) {
	            ps.setLong(1, idUtente);
	            ps.setInt(2, giornoSettimana);
	            ps.executeUpdate();
	        }
	    }
	    
	    @Override
	    public OrarioBase getOrariByUtenteEGiorno(long idUtente, DayOfWeek giornodellasettimana) throws Exception {
	        String query = "SELECT * FROM orario_base WHERE id_utente = ? AND giorno_settimana = ?";

	        try (Connection connection = dataSource.getConnection(); 
	             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            
	        	preparedStatement.setLong(1, idUtente);
	        	preparedStatement.setInt(2, giornodellasettimana.getValue());
	        	  try ( ResultSet resultSet = preparedStatement.executeQuery()){

	     
	                if (resultSet.next()) {
	                    OrarioBase orarioBase = new OrarioBase();
	                    orarioBase.setId(resultSet.getLong("id"));
	                    orarioBase.setOraInizio(resultSet.getTime("ora_inizio").toLocalTime());
	                    orarioBase.setOraFine(resultSet.getTime("ora_fine").toLocalTime());
	                    orarioBase.setIdUtente(idUtente);
	                    orarioBase.setGiornoSettimana(giornodellasettimana);
	                    return orarioBase;
	                }
	        
	        }
	        	  return null;
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    }
	    





















}


*/