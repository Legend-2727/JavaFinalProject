package finalproject.javafinalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class GetSearchInfo {
    private Client client;
    private Stage stage;
    @FXML
    private Label Info;
    @FXML
    private TextField SearchInfo;
    private String type;
    @FXML
    private TextField lowLimit;
    @FXML
    private TextField maxLimit;
    public void setUp(Client client) {
        this.client=client;
    }

    public void setStage(Stage popUp) {
        this.stage=popUp;
    }
    public void setLabel(String label)
    {
        this.Info.setText("Enter "+label+" :");
    }
    public void Search(ActionEvent actionEvent) throws IOException {
        String info=SearchInfo.getText();
        client.doSearch(info,type);
        stage.close();
    }

    public void setType(String info) {
        this.type=info;
    }

    public void SearchRuntime(ActionEvent actionEvent) throws IOException {
        String low=lowLimit.getText();
        String high=maxLimit.getText();
        client.doSearchbyRuntime(Integer.parseInt(low),Integer.parseInt(high));
        stage.close();
    }

    public void cancel(ActionEvent actionEvent) {
        stage.close();
    }
}
