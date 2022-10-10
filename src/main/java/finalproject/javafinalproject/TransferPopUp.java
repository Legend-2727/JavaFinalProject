package finalproject.javafinalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class TransferPopUp {
    private Client client;
    @FXML
    public TextField textField;
    private Stage stage;
    private Movie movie;
    public void setStage(Stage stage){
        this.stage=stage;
    }
    public void setMain(Client client) {
        this.client=client;
    }

    public void TransferStart(ActionEvent actionEvent) throws IOException {
        String name=textField.getText();
        client.TransferStart(movie,name);
        stage.close();
    }

    public void setMovie(Movie movie) {
        this.movie=movie;
    }

    public void OnClickCancelButton(ActionEvent actionEvent) {
        stage.close();
    }
}
