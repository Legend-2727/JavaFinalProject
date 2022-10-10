package finalproject.javafinalproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AllMovieListController {
    @FXML
    public Label Category;
    @FXML
    public Label TotalCount;
    public Client client;
    private ProductionCompany productionCompany;
    @FXML
    private AnchorPane anchorPane;
    public void setClient(Client client) {
        this.client=client;
    }
    public void initiate(ProductionCompany p)
    {
        this.productionCompany=p;
        client.mainPane = anchorPane;
    }
    public void setLabels(String category,int count)
    {
        this.Category.setText(category);
        this.TotalCount.setText("Total Movies: "+count);
    }

    public void Back(ActionEvent actionEvent) throws IOException {
        client.setHomeScreen();
    }
}
