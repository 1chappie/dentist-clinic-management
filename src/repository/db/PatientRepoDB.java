package repository.db;

import domain.Patient;
import utils.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class PatientRepoDB extends DatabaseRepository<String, Patient> {

    public PatientRepoDB(String url) {
        super(url);
    }

    @Override
    protected void createTable() {
        try {
            openConnection();
            final Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS patients(" +
                            "PatientID VARCHAR(25) PRIMARY KEY, " +
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
    public boolean add(String id, Patient patient) {
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO patients VALUES (?, ?, ?, ?);");
            statement.setString(1, patient.getID());
            statement.setString(2, patient.getName());
            statement.setString(3, patient.getEmail());
            statement.setString(4, patient.getPhone());
            int ra = statement.executeUpdate();
            Logger.log(this.getClass().getSimpleName(), "Executed: " + statement);
            Logger.log(this.getClass().getSimpleName(), "Added " + ra + " patient");
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
                    "DELETE FROM patients WHERE PatientID=?;");
            statement.setString(1, id);
            int ra = statement.executeUpdate();
            Logger.log(this.getClass().getSimpleName(), "Executed: " + statement);
            Logger.log(this.getClass().getSimpleName(), "Removed " + ra + " patient");
            return true;
        } catch (SQLException e) {
            Logger.log(this.getClass().getSimpleName(), "ERROR: " + e.getMessage());
            return false;
        } finally {
            closeConnection();
        }
    }

    @Override
    public boolean update(String id, Patient patient) {
        try {
            openConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE patients " +
                            "SET PatientID = ?, Name = ?, Email = ?, Phone = ? " +
                            "WHERE PatientID = ?;");
            statement.setString(1, patient.getID());
            statement.setString(2, patient.getName());
            statement.setString(3, patient.getEmail());
            statement.setString(4, patient.getPhone());
            statement.setString(5, id);
            int ra = statement.executeUpdate();
            Logger.log(this.getClass().getSimpleName(), "Executed: " + statement);
            Logger.log(this.getClass().getSimpleName(), "Updated " + ra + " patient");
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
    public Patient findById(String id) {
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * from patients as p WHERE p.PatientID = ?;");
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            Patient p = null;
            int count = 0;
            if (rs.next()) {
                p = new Patient(
                        rs.getString("PatientID"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Phone"));
                count++;
            }
            Logger.log(this.getClass().getSimpleName(), "Executed: " + statement);
            Logger.log(this.getClass().getSimpleName(), "Queried " + count + " patient");
            return p;
        } catch (SQLException ex) {
            Logger.log(this.getClass().getSimpleName(), "ERROR: " + ex.getMessage());
            return null;
        } finally {
            closeConnection();
        }
    }

    @Override
    public List<Patient> getAll() {
        var patients = new ArrayList<Patient>();
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * from patients;");
            ResultSet rs = statement.executeQuery();
            int count = 0;
            while (rs.next()) {
                var p = new Patient(
                        rs.getString("PatientID"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Phone"));
                patients.add(p);
                count++;
            }
            Logger.log(this.getClass().getSimpleName(), "Executed: " + statement);
            Logger.log(this.getClass().getSimpleName(), "Queried " + count + " patients");
            return patients;
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
                    "SELECT COUNT(*) from patients;");
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
