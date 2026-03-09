package org.elis.dao.mysql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.elis.dao.definition.UtenteProfessioneDAO;
import org.elis.progetto.model.UtenteProfessione;

public class MysqlUtenteProfessioneDao implements UtenteProfessioneDAO {
    private DataSource dataSource;

    public MysqlUtenteProfessioneDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(UtenteProfessione u) {
        String sql = "INSERT INTO utente_professione (id_utente, id_professione, tariffaH) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, u.getIdUtente());
            ps.setLong(2, u.getIdProfessione());
            ps.setBigDecimal(3, u.getTariffaH());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public UtenteProfessione selectById(Long id) {
        String sql = "SELECT * FROM utente_professione WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new UtenteProfessione(
                        rs.getLong("id"),
                        rs.getLong("id_utente"),
                        rs.getLong("id_professione"),
                        rs.getBigDecimal("tariffaH")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UtenteProfessione> selectAll() {
        String sql = "SELECT * FROM utente_professione";
        List<UtenteProfessione> listaUtenteProfessione = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                listaUtenteProfessione.add(new UtenteProfessione(
                    rs.getLong("id"),
                    rs.getLong("id_utente"),
                    rs.getLong("id_professione"),
                    rs.getBigDecimal("tariffaH")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaUtenteProfessione;
    }

    @Override
    public void update(UtenteProfessione u) {
        String sql = "UPDATE utente_professione SET id_utente = ?, id_professione = ?, tariffaH = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, u.getIdUtente());
            ps.setLong(2, u.getIdProfessione());
            ps.setBigDecimal(3, u.getTariffaH());
            ps.setLong(4, u.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM utente_professione WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Override
	public List<UtenteProfessione> selectByUtente(long idUtente) {
		String sql = "SELECT * FROM utente_professione WHERE id_utente = ?";
	    List<UtenteProfessione> listaProfessioniUtente = new ArrayList<>();

	    try (Connection connection = dataSource.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

	        preparedStatement.setLong(1, idUtente);

	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            while (resultSet.next()) {
	                listaProfessioniUtente.add(new UtenteProfessione(
	                    resultSet.getLong("id"),
	                    resultSet.getLong("id_utente"),
	                    resultSet.getLong("id_professione"),
	                    resultSet.getBigDecimal("tariffaH")
	                ));
	            }
	        }
	    } catch (Exception exception) {
	        exception.printStackTrace();
	    }
	    return listaProfessioniUtente;
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