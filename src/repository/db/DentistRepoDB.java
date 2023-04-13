package repository.db;

import domain.Dentist;
import utils.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class DentistRepoDB extends DatabaseRepository<String, Dentist> {

    public DentistRepoDB(String url) {
        super(url);
    }

    @Override
    protected void createTable() {
        try {
            openConnection();
            final Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS " +
                            "dentists(" +
                            "DentistID VARCHAR(25) PRIMARY KEY, " +
                            "Name VARCHAR(200) NOT NULL , " +
                            "Email VARCHAR(200) NOT NULL , " +
                            "Phone VARCHAR(200) NOT NULL );");
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
            exit(1);
        } finally {
            closeConnection();
        }
    }

    @Override
    public boolean add(String id, Dentist dentist) {
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO dentists VALUES (?, ?, ?, ?);");
            statement.setString(1, dentist.getID());
            statement.setString(2, dentist.getName());
            statement.setString(3, dentist.getEmail());
            statement.setString(4, dentist.getPhone());
            int ra = statement.executeUpdate();
            Logger.log(this.getClass().getSimpleName(), "Executed: " + statement);
            Logger.log(this.getClass().getSimpleName(), "Added " + ra + " dentist");
            return true;
        } catch (SQLException e) {
            Logger.log(this.getClass().getSimpleName(), "ERROR: " + e.getMessage());
            return false;
        } finally {
            closeConnection();
        }
    }

    @Override
    public boolean remove(String id) {
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM dentists WHERE DentistID=?;");
            statement.setString(1, id);
            int ra = statement.executeUpdate();
            Logger.log(this.getClass().getSimpleName(), "Executed: " + statement);
            Logger.log(this.getClass().getSimpleName(), "Removed " + ra + " dentist");
            return true;
        } catch (SQLException e) {
            Logger.log(this.getClass().getSimpleName(), "ERROR: " + e.getMessage());
            return false;
        } finally {
            closeConnection();
        }
    }

    @Override
    public boolean update(String id, Dentist dentist) {
        try {
            openConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE dentists " +
                            "SET DentistID = ?, Name = ?, Email = ?, Phone = ? " +
                            "WHERE DentistID = ?;");
            statement.setString(1, dentist.getID());
            statement.setString(2, dentist.getName());
            statement.setString(3, dentist.getEmail());
            statement.setString(4, dentist.getPhone());
            statement.setString(5, id);
            int ra = statement.executeUpdate();
            Logger.log(this.getClass().getSimpleName(), "Executed: " + statement);
            Logger.log(this.getClass().getSimpleName(), "Updated " + ra + " dentist");
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException ex) {
            Logger.log(this.getClass().getSimpleName(), "ERROR: " + ex.getMessage());
            return false;
        } finally {
            closeConnection();
        }
    }

    @Override
    public Dentist findById(String id) {
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * from dentists as d WHERE d.DentistID = ?;");
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            Dentist d = null;
            int counter = 0;
            if (rs.next()) {
                d = new Dentist(
                        rs.getString("DentistID"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Phone"));
                counter++;
            }
            Logger.log(this.getClass().getSimpleName(), "Executed: " + statement);
            Logger.log(this.getClass().getSimpleName(), "Queried " + counter + " dentist");
            return d;
        } catch (SQLException ex) {
            Logger.log(this.getClass().getSimpleName(), "ERROR: " + ex.getMessage());
            return null;
        } finally {
            closeConnection();
        }
    }

    @Override
    public List<Dentist> getAll() {
        var dentists = new ArrayList<Dentist>();
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * from dentists;");
            ResultSet rs = statement.executeQuery();
            int counter = 0;
            while (rs.next()) {
                var d = new Dentist(
                        rs.getString("DentistID"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Phone"));
                dentists.add(d);
                counter++;
            }
            Logger.log(this.getClass().getSimpleName(), "Executed: " + statement);
            Logger.log(this.getClass().getSimpleName(), "Queried " + counter + " dentist");
            return dentists;
        } catch (SQLException ex) {
            Logger.log(this.getClass().getSimpleName(), "ERROR: " + ex.getMessage());
            return null;
        } finally {
            closeConnection();
        }
    }

    @Override
    public Integer size() {
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT COUNT(*) from dentists;");
            ResultSet rs = statement.executeQuery();
            Logger.log(this.getClass().getSimpleName(), "Executed: " + statement);
            if (rs.next())
                return rs.getInt(1);
            return 0;
        } catch (SQLException ex) {
            Logger.log(this.getClass().getSimpleName(), "ERROR: " + ex.getMessage());
            return 0;
        } finally {
            closeConnection();
        }
    }
}
