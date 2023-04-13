package repository.db;

import domain.Appointment;
import utils.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class AppointmentRepoDB extends DatabaseRepository<String, Appointment> {

    public AppointmentRepoDB(String url) {
        super(url);
    }

    @Override
    protected void createTable() {
        try {
            openConnection();
            final Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS appointments(" +
                            "AppointmentID VARCHAR(80) PRIMARY KEY, " +
                            "DentistID VARCHAR(25) NOT NULL , " +
                            "PatientID VARCHAR(25) NOT NULL , " +
                            "Date VARCHAR(50) NOT NULL ," +
                            "Comment VARCHAR(200) NOT NULL );");
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
            exit(1);
        } finally {
            closeConnection();
        }
    }

    @Override
    public boolean add(String id, Appointment appointment) {
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO appointments VALUES (?, ?, ?, ?, ?);");
            statement.setString(1, appointment.getID());
            statement.setString(2, appointment.getDentistID());
            statement.setString(3, appointment.getPatientID());
            statement.setString(4, appointment.getDate().toString());
            statement.setString(5, appointment.getComments());
            int ra = statement.executeUpdate();
            Logger.log(this.getClass().getSimpleName(), "Executed: " + statement);
            Logger.log(this.getClass().getSimpleName(), "Added " + ra + " row");
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
                    "DELETE FROM appointments WHERE AppointmentID=?;");
            statement.setString(1, id);
            int ra = statement.executeUpdate();
            Logger.log(this.getClass().getSimpleName(), "Executed: " + statement);
            Logger.log(this.getClass().getSimpleName(), "Removed " + ra + " row");
            return true;
        } catch (SQLException e) {
            Logger.log(this.getClass().getSimpleName(), "ERROR: " + e.getMessage());
            return false;
        } finally {
            closeConnection();
        }
    }

    @Override
    public boolean update(String id, Appointment app) {
        try {
            openConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE appointments " +
                            "SET AppointmentID = ?, DentistID = ?, PatientID = ?, Date = ?, Comment = ? " +
                            "WHERE AppointmentID = ?;");
            statement.setString(1, app.getID());
            statement.setString(2, app.getDentistID());
            statement.setString(3, app.getPatientID());
            statement.setString(4, app.getDate().toString());
            statement.setString(5, app.getComments());
            statement.setString(6, id);
            int ra = statement.executeUpdate();
            Logger.log(this.getClass().getSimpleName(), "Executed: " + statement);
            Logger.log(this.getClass().getSimpleName(), "Updated " + ra + " row");
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
    public Appointment findById(String id) {
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * from appointments as a WHERE a.AppointmentID = ?;");
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            Appointment a = null;
            int counter = 0;
            if (rs.next()) {
                a = Appointment.build(
                        rs.getString("AppointmentID"),
                        rs.getString("DentistID"),
                        rs.getString("PatientID"),
                        rs.getString("Date"),
                        rs.getString("Comment"));
                counter++;
            }
            Logger.log(this.getClass().getSimpleName(), "Executed: " + statement);
            Logger.log(this.getClass().getSimpleName(), "Queried " + counter + " rows");
            return a;
        } catch (SQLException ex) {
            Logger.log(this.getClass().getSimpleName(), "ERROR: " + ex.getMessage());
            return null;
        } finally {
            closeConnection();
        }
    }

    @Override
    public List<Appointment> getAll() {
        var apps = new ArrayList<Appointment>();
        try {
            openConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * from appointments;");
            ResultSet rs = statement.executeQuery();
            int counter = 0;
            while (rs.next()) {
                var a = Appointment.build(
                        rs.getString("AppointmentID"),
                        rs.getString("DentistID"),
                        rs.getString("PatientID"),
                        rs.getString("Date"),
                        rs.getString("Comment"));
                apps.add(a);
                counter++;
            }
            Logger.log(this.getClass().getSimpleName(), "Executed: " + statement);
            Logger.log(this.getClass().getSimpleName(), "Queried " + counter + " rows");
            return apps;
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
                    "SELECT COUNT(*) from appointments;");
            ResultSet rs = statement.executeQuery();
            Logger.log(this.getClass().getSimpleName(), "Executed: " + statement);
            if (rs.next())
                return rs.getInt(1);
            statement.close();
            return 0;
        } catch (SQLException ex) {
            Logger.log(this.getClass().getSimpleName(), "ERROR: " + ex.getMessage());
            return 0;
        } finally {
            closeConnection();
        }
    }
}
