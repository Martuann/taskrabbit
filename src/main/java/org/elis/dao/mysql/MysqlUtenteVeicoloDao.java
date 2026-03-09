package org.elis.dao.mysql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.elis.dao.definition.UtenteVeicoloDao;
import org.elis.progetto.model.UtenteVeicolo;

public class MysqlUtenteVeicoloDao implements UtenteVeicoloDao {
    
    private DataSource dataSource;

    public MysqlUtenteVeicoloDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

	@Override
	public void associaVeicolo(UtenteVeicolo utenteVeicolo) throws Exception {
String query = "INSERT INTO utente_veicolo (id_utente, id_veicolo, aggiuntaServizio) VALUES (?, ?, ?)";
        
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setLong(1, utenteVeicolo.getIdUtente());
            preparedStatement.setLong(2, utenteVeicolo.getIdVeicolo());
            preparedStatement.setBigDecimal(3, utenteVeicolo.getAggiuntaServizio());
            
            preparedStatement.executeUpdate();
        }
	}

	@Override
	public List<UtenteVeicolo> getDettagliVeicoliUtente(long idUtente) throws Exception {
		List<UtenteVeicolo> listaDettagli = new ArrayList<UtenteVeicolo>();
        String query = "SELECT * FROM utente_veicolo WHERE id_utente = ?";
        
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setLong(1, idUtente);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    listaDettagli.add(MysqlToUtenteVeicolo(resultSet));
                }
            }
        }
        return listaDettagli;
	}

	@Override
    public void aggiornaPrezzoServizio(long idUtente, long idVeicolo, BigDecimal nuovoPrezzo) throws Exception {
        String query = "UPDATE utente_veicolo SET aggiuntaServizio = ? WHERE id_utente = ? AND id_veicolo = ?";
        
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setBigDecimal(1, nuovoPrezzo);
            preparedStatement.setLong(2, idUtente);
            preparedStatement.setLong(3, idVeicolo);
            
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void rimuoviAssociazione(long idUtente, long idVeicolo) throws Exception {
        String query = "DELETE FROM utente_veicolo WHERE id_utente = ? AND id_veicolo = ?";
        
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setLong(1, idUtente);
            preparedStatement.setLong(2, idVeicolo);
            
            preparedStatement.executeUpdate();
        }
    }
    
    @Override
    public List<UtenteVeicolo> selectByUtente(long idUtente) {
        String sql = "SELECT * FROM utente_veicolo WHERE id_utente = ?";
        List<UtenteVeicolo> listaVeicoliUtente = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, idUtente);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    listaVeicoliUtente.add(new UtenteVeicolo(
                        resultSet.getLong("id"),
                        resultSet.getLong("id_utente"),
                        resultSet.getLong("id_veicolo"),
                        resultSet.getBigDecimal("aggiunta_servizio")
                    ));
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return listaVeicoliUtente;
    }
    
    private UtenteVeicolo MysqlToUtenteVeicolo(ResultSet resultSet) throws Exception {
        UtenteVeicolo uv = new UtenteVeicolo();
        uv.setId(resultSet.getLong("id"));
        uv.setIdUtente(resultSet.getLong("id_utente"));
        uv.setIdVeicolo(resultSet.getLong("id_veicolo"));
        uv.setAggiuntaServizio(resultSet.getBigDecimal("aggiuntaServizio"));
        return uv;
    }


}