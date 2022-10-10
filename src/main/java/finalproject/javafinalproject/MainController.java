package finalproject.javafinalproject;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.System.exit;

public class MainController {
    @FXML
    private TextField Username;

    private Client client;
    @FXML
    private Button LoginButton;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    public Label WelcomeMessage;
    @FXML
    public Label MovieCount;
    @FXML
    public Label CompanyCount;
    @FXML
    public Label MovieCount1;
    @FXML
    public Label CompanyCount1;
    @FXML
    public Label comments;
    void setMain(Client client) {
        this.client = client;
    }
    @FXML
    void Login(ActionEvent event) throws IOException {
        String name=Username.getText();
        InfoWithCommand infoWithCommand=new InfoWithCommand();
        infoWithCommand.setCommand("Username");
        infoWithCommand.setInformation(name);
        try {
            client.getNetworkUtil().write(infoWithCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickExit(ActionEvent actionEvent) throws IOException {
        client.getNetworkUtil().closeConnection();
        exit(0);
    }
}
