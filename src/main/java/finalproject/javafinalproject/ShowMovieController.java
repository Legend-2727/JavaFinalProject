package finalproject.javafinalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class ShowMovieController {
    @FXML
    public HBox MoviePane;
    @FXML
    private Label MovieName;
    @FXML
    private Label Genres;
    @FXML
    private Label Length;
    @FXML
    private Label Year;
    @FXML
    private Label Revenue;
    @FXML
    private Label Budget;
    private Client client;
    private Movie movie;
    @FXML
    private ImageView ThumbNails=new ImageView();

    public HBox setUp(Movie m){
        MovieName.setText(m.getName()+"  ");
        Genres.setText(m.getGenre()[0]+","+m.getGenre()[1]+","+m.getGenre()[2]);
        Length.setText(Integer.toString(m.getRunningTime()));
        Year.setText(Integer.toString(m.getYearOfOrigin()));
        Revenue.setText(Integer.toString(m.getRevenue()));
        Budget.setText(Integer.toString(m.getBudget()));
        try {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(m.getImageLinks())));
            ThumbNails.setImage(image);
        }
        catch (Exception e)
        {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("MovieThumbnails/dummyposter.jpg")));
            ThumbNails.setImage(image);
        }
        movie=m;
        return MoviePane;
    }
    public void setClient(Client client) {
        this.client=client;
    }

    public void Transfer(ActionEvent actionEvent) throws IOException {
        client.TransferMovie(movie);
    }
}
