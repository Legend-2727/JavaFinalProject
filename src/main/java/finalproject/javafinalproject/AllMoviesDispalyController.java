package finalproject.javafinalproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.List;

public class AllMoviesDispalyController {

    @FXML
    private ListView<HBox> listView;
    private Client client;

    public void initiate(List<Movie> movies) throws IOException {
        for (var temp : movies) {
            var newLoader = new FXMLLoader();
            newLoader.setLocation(getClass().getResource("ShowMovie.fxml"));
            newLoader.load();
            ShowMovieController showMovieController = newLoader.getController();
            showMovieController.setClient(client);
            listView.getItems().add(showMovieController.setUp(temp));
        }
    }
    public void setClient(Client client) {
        this.client=client;
    }
}
