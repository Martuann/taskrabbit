package org.elis.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.elis.dao.definition.ProfessioneDao;
import org.elis.progetto.model.Professione;

public class MysqlProfessioneDao implements ProfessioneDao {
    
    private DataSource dataSource;

    public MysqlProfessioneDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(Professione professione) {
        String sql = "INSERT INTO professione (nome) VALUES(?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
             
            ps.setString(1, professione.getNome());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Professione selectById(Long id) {
        String sql = "SELECT * FROM professione WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setLong(1, id); 
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Professione(
                        rs.getLong("id"),
                        rs.getString("nome")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Professione> selectAll() {
        String sql = "SELECT * FROM professione";
        List<Professione> professioni = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                professioni.add(new Professione(
                    rs.getLong("id"),
                    rs.getString("nome")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return professioni;
    }

    @Override
    public void update(Professione professione) throws Exception {
        String sql = "UPDATE professione SET nome = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setString(1, professione.getNome());
            ps.setLong(2, professione.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM professione WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
/*    @Override
    public Professione getProfessionibynome() throws Exception {
        List<Professione> professioni = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM professione");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Professione p = new Professione(resultSet.getLong("id"), resultSet.getString("nome"));
                professioni.add(p);
            }
        }
        return professioni;
    }
    
    
    
    
    
    
    
    
    
    create table utente(
id bigint unsigned primary key auto_increment,
nome varchar(30)not null,
cognome varchar(30)not null,
email varchar(50)not null unique,
telefono varchar(20)not null unique,
password varchar(100)not null,
ddn date not null,
cf varchar(16) not null unique,
ruolo TINYINT NOT NULL COMMENT '0:admin, 1:professionista, 2:base', 
CONSTRAINT chk_ruolo CHECK (ruolo BETWEEN 0 AND 2),
id_citta bigint unsigned not null, 
foreign key (id_citta) references citta(id) 

);
    
    
    
    
    
    
    
    
    */
    
    @Override
    public List<Professione> selectbyUtente(Long id_utente) throws Exception {
        String sql = "SELECT p.* FROM professione p JOIN utente_professione up ON p.id = up.id_professione WHERE up.id_utente=?";
        List<Professione> professioni = new ArrayList<>();
        
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setLong(1, id_utente);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    professioni.add(new Professione(
                        rs.getLong("id"),
                        rs.getString("nome")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return professioni;
    }
}
