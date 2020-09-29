package DAO;

import model.PersonTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 6/12/2020.
 */
public class Database {
    Connection connection;
    PreparedStatement statement;

    public Database() {

     /*
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.
        getConnection("jdbc:mysql://localhost:3306/test2", "root", "123");
        connection.setAutoCommit(false);
    */
        try {

            getConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() throws Exception {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.
                        getConnection("jdbc:oracle:thin:@192.168.142.134:1521:orcl",
                                "system", "12345");
                connection.setAutoCommit(false);
            }


        } catch (Exception e) {
        }
        return connection;
    }


    public List<PersonTO> select() throws SQLException {
        statement = connection.prepareStatement("select * from person");
        ResultSet result = statement.executeQuery();
        List<PersonTO> ls = new ArrayList<>();

        while (result.next()) {
            PersonTO psn = new PersonTO();
            psn.setId(result.getInt("person_id"));
            psn.setName(result.getString("name"));
            psn.setFamily(result.getString("family"));
            ls.add(psn);
        }
        return ls;


    }

    public PersonTO select(int id) throws SQLException {
        statement = connection.prepareStatement("select * from person where person_id = ?");
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();
        PersonTO psn = new PersonTO();

        if (result.next()) {
            psn.setId(result.getInt("person_id"));
            psn.setName(result.getString("name"));
            psn.setFamily(result.getString("family"));
        }

        return psn;


    }

    public List<PersonTO> select(String name, String family)
            throws SQLException {
        statement = connection.
                prepareStatement("select * from person " +
                        "where name=? and family=?");
        statement.setString(1, name);
        statement.setString(2, family);
        ResultSet result = statement.executeQuery();
        List<PersonTO> ls = new ArrayList<>();


        while (result.next()) {
            PersonTO psn = new PersonTO();
            psn.setId(result.getInt("person_id"));
            psn.setName(result.getString("name"));
            psn.setFamily(result.getString("family"));
            ls.add(psn);
        }

        System.out.println(ls.toString());
        return ls;


    }

    public void insert(String name, String family) throws SQLException {
        try {

            int pk_id = generateID("personsequence");

            if (connection == null || connection.isClosed()) {
                getConnection();
            }
            statement = connection.prepareStatement("insert into person (person_id, " +
                     "name , family) values (?,?,?) ");
            statement.setInt(1, pk_id);
            statement.setString(2, name);
            statement.setString(3, family);
            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        }

    }

    public int insert(PersonTO personTO) throws SQLException {
        int id = 0;
        try {

            int pk_id = generateID("personsequence");

            if (connection == null || connection.isClosed()) {
                getConnection();
            }
            statement = connection.
                    prepareStatement("insert into person " +
                            "(person_id, name , family) values (?,?,?) ");
            statement.setInt(1, pk_id);
            statement.setString(2, personTO.getName());
            statement.setString(3, personTO.getFamily());
            id = statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
        }
        return id;
    }

    public boolean update(int id , PersonTO personTO) throws SQLException {
        try {

            String query = "update person set name = ? , family=? where person_id=?";
            statement = connection.prepareStatement(query);

            statement.setString(1, personTO.getName());
            statement.setString(2, personTO.getFamily());
            statement.setInt   (3, id);
            statement.executeUpdate();
            connection.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        }
        return false;
    }


    public boolean delete(int id ) throws SQLException {
        try {

            String query = " delete from person where person_id=?";
            statement = connection.prepareStatement(query);
            statement.setInt   (1, id);
            statement.executeUpdate();
            connection.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        }
        return false;
    }


    public int generateID(String sequenceName) {
        int id = 0;
        try {
            String query = "SELECT " + sequenceName + ".NEXTVAL FROM dual";


            if (connection == null || connection.isClosed()) {
                getConnection();
            }

            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            if (rs != null && rs.next()) {
                id = rs.getInt(1);
                rs.close();
            }
            statement.close();
            connection.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }
}
