/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storage.database;

import domain.GeneralEntity;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import storage.IDatabaseBroker;
import storage.database.connection.DatabaseConnection;

/**
 *
 * @author student1
 */
public class DatabaseBroker implements IDatabaseBroker {

   @Override
    public List<GeneralEntity> getAll(GeneralEntity entity, String keyword) throws Exception {
        List<GeneralEntity> list;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            System.out.println("Uspostavljena je konekcija na bazu");
            String query = "SELECT * FROM " + entity.getTableName() + entity.getConstraints(keyword);
            System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            list = entity.getList(resultSet);
            resultSet.close();
            statement.close();
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public GeneralEntity getOne(GeneralEntity entity) throws Exception {
        GeneralEntity generalEntity;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            System.out.println("Uspostavljena je konekcija na bazu");
            String query = "SELECT * FROM " + entity.getTableName() + entity.getConstraints(null);
            System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            generalEntity = entity.getOne(resultSet);
            resultSet.close();
            statement.close();
            return generalEntity;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public long save(List<GeneralEntity> entities) throws Exception {
        List<GeneralEntity> entiteti = (List<GeneralEntity>) entities;
        List<GeneralEntity> entitetiIzBaze = getAll(entiteti.get(0), "");
        proveriJednakost(entiteti, entitetiIzBaze);
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            System.out.println("Uspostavljena je konekcija na bazu");
            for (GeneralEntity entitet : entiteti) {
                String query = "INSERT INTO " + entitet.getTableName() + " VALUES(" + entitet.getValues() + ")";
                System.out.println(query);
                statement.addBatch(query);
            }
            int[] izvrsena = statement.executeBatch();
            for (int i : izvrsena) {
                if (i < 1) {
                    throw new Exception("Error while executing queries!");
                }
            }
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw ex;
        }
        return 1;
    }

    @Override
    public boolean remove(GeneralEntity entity) throws Exception {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            System.out.println("Uspostavljena je konekcija na bazu");
            String query = "DELETE FROM " + entity.getTableName() + entity.getConstraints(new char[0]);
            Statement statement = connection.createStatement();
            String[] queryList = query.split(";");
            for (int i = queryList.length - 1; i >= 0; i--) {
                statement.addBatch(queryList[i]);
                System.out.println(queryList[i]);
            }
            int[] izvrsena = statement.executeBatch();
            for (int i : izvrsena) {
                if (i < 0) {
                    throw new Exception("Error while executing queries");
                }
            }
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
        return true;
    }

 

    private void proveriJednakost(List<GeneralEntity> entiteti, List<GeneralEntity> entitetiIzBaze) throws Exception {
        for (GeneralEntity generalEntityBaza : entitetiIzBaze) {
            for (GeneralEntity generalEntity : entiteti) {
                if (generalEntity.equals(generalEntityBaza)) {
                    throw new Exception("Entity already exists!");
                }
            }
        }
    }

    @Override
    public List<GeneralEntity> getAll(GeneralEntity entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

}
