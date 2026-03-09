package org.elis.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.elis.dao.definition.VeicoloDao;
import org.elis.progetto.model.Veicolo;

public class MySqlVeicoloDao implements VeicoloDao{
	private DataSource dataSource;
	public MySqlVeicoloDao(DataSource dataSource) {
		this.dataSource = dataSource;	}

	
	@Override
    public void aggiungiVeicolo(Veicolo veicolo) throws Exception {
        String query = "INSERT INTO veicolo (categoria) VALUES (?)";
        
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, veicolo.getCategoria());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public Veicolo ricercaPerId(long id) throws Exception {
        String query = "SELECT * FROM veicolo WHERE id = ?";
        
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setLong(1, id);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return MysqlToVeicolo(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public List<Veicolo> getAllVeicoli() throws Exception {
        List<Veicolo> listaVeicoli = new ArrayList<Veicolo>();
        String query = "SELECT * FROM veicolo";
        
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            
            while (resultSet.next()) {
                listaVeicoli.add(MysqlToVeicolo(resultSet));
            }
        }
        return listaVeicoli;
    }

    private Veicolo MysqlToVeicolo(ResultSet resultSet) throws Exception {
        long id = resultSet.getLong("id");
        String categoria = resultSet.getString("categoria");
        
        return new Veicolo(id, categoria);
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
