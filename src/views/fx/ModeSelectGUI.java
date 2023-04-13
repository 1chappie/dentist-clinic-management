package views.fx;

import exception.ConfigurationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import utils.Logger;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import static java.lang.System.exit;

public class ModeSelectGUI implements Initializable {
    @FXML
    private ChoiceBox<String> repoChoice;
    @FXML
    private Button loadButton;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        repoChoice.getItems().addAll("memory", "csv", "binary", "database", "json");
    }

    @FXML
    protected void onLoadButtonClick(ActionEvent trigger) throws IOException {
        Properties properties = new Properties();
        try (InputStream pf = new FileInputStream("config/settings.properties")) {
            properties.load(pf);
            String repo = repoChoice.getValue();
            if (repo == null) {
                return;
            }
            String comment = "#ACCEPTED REPOSITORY MODES:\n" +
                    "#[csv] - .csv files\n" +
                    "#[binary] - .bin files\n" +
                    "#[database] - SQLite3 database\n" +
                    "#[memory] - stored on the heap during execution, not persistent\n" +
                    "#[json] - .json files\n" +
                    "#repository paths can be found in \"config/paths.properties\"";
            properties.setProperty("RepositoryMode", repo);
            properties.store(new FileWriter("config/settings.properties"), comment);
            switchToDashboardScene(trigger);
        } catch (IOException e) {
            e.printStackTrace();
            exit(1);
        }
    }

    public void switchToDashboardScene(ActionEvent trigger) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("DashboardGUI.fxml"));
        var stage = (Stage) ((Node) trigger.getSource()).getScene().getWindow();
        var scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
