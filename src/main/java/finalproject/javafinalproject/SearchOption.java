package finalproject.javafinalproject;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SearchOption {
    private Client client;
    private Stage stage;

    public void SearchByTitle(ActionEvent actionEvent) throws IOException {
        System.out.println("here");
        client.searchDetails("Movie Title");
        stage.close();
    }

    public void SearchByGenre(ActionEvent actionEvent) throws IOException {
        stage.close();
        client.searchDetails("Genre");
    }

    public void SearchByRuntime(ActionEvent actionEvent) throws IOException {
        stage.close();
        client.searchDetailsRuntime("Range of Runtime");
    }

    public void SearchByYear(ActionEvent actionEvent) throws IOException {
        stage.close();
        client.searchDetails("Released Year");
    }

    public void setUp(Client client) {
        this.client=client;
    }

    public void setStage(Stage popUp) {
        this.stage=popUp;
    }

    public void OnClickBackButton(ActionEvent actionEvent) {
        stage.close();
    }
}
