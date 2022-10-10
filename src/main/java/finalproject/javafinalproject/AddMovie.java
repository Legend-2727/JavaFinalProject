package finalproject.javafinalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddMovie {
    @FXML
    private TextField MovieName;
    @FXML
    private TextField Genre1;
    @FXML
    private TextField Genre2;
    @FXML
    private TextField Genre3;
    @FXML
    private TextField Runtime;
    @FXML
    private TextField Budget;
    @FXML
    private TextField Revenue;
    @FXML
    private TextField Origin;
    private ProductionCompany productionCompany;
    private Client client;
    private Stage stage;
    public void MovieAdded(ActionEvent event) throws IOException {
        String Name = MovieName.getText();
        String origin = Origin.getText();
        String[] genre = new String[3];
        genre[0] = Genre1.getText();
        genre[1] = Genre2.getText();
        genre[2] = Genre3.getText();
        String runtime = Runtime.getText();
        String budget = Budget.getText();
        String revenue = Revenue.getText();
        String company = this.productionCompany.getName();
        Movie movie = new Movie(Name, Integer.parseInt(origin), genre, Integer.parseInt(runtime), company, Integer.parseInt(budget), Integer.parseInt(revenue));
        movie.setImageLinks("MovieThumbnails/dummyposter.jpg");
        client.addMovie(movie);
        stage.close();
    }
    public void setUp(ProductionCompany productionCompany,Client client)
    {
        this.productionCompany=productionCompany;
        this.client=client;
    }

    public void setStage(Stage popUp) {
        this.stage=popUp;
    }

    public void Cancel(ActionEvent actionEvent) {
        stage.close();
    }
}
