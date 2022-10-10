package finalproject.javafinalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SecondScene{
    private Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private ImageView imageView=new ImageView();
    ProductionCompany productionCompany;
    @FXML
    private Label CompanyTitle;
    @FXML
    private Label TotalProfitTitle;

        public void Logout(ActionEvent actionEvent) throws Exception {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Logout");
            alert.setHeaderText("You are about to logout!");
            alert.setContentText("Are you sure?");
            stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            if(alert.showAndWait().get()== ButtonType.OK)
            {
                client.getNetworkUtil().closeConnection();
                client.start(stage);
            }
    }

    public void setImageView(String link) {
            try {
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(link)));
                imageView.setImage(image);
            }
            catch (Exception e)
            {
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Company/dummy.jpg")));
                imageView.setImage(image);
            }
    }

    public void setLabels(String name, String profit) {
            CompanyTitle.setText(name);
            TotalProfitTitle.setText("Total Profit: "+profit);
    }

    public void setProductionCompany(ProductionCompany productionCompany) {
        this.productionCompany = productionCompany;
    }

    public void ShowAllMovies(ActionEvent actionEvent) throws IOException {
            client.showAllMovies();
    }

    public void ShowMaxRevenueMovies(ActionEvent actionEvent) throws IOException {
            client.showMaxRevenueMovies();
    }

    public void ShowLatestMovies(ActionEvent actionEvent) throws IOException {
            client.ShowLatestMovies();
    }

    public void AddMovies(ActionEvent actionEvent) throws IOException {
            client.ShowAddMovieStage();
    }

    public void TransferMovie(ActionEvent actionEvent) throws IOException {
        client.showAllMovies();
    }

    public void SearchMovies(ActionEvent actionEvent) throws IOException {
            client.searchOption();
    }
}
