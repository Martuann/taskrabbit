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
    
    
    */
    
    
}
