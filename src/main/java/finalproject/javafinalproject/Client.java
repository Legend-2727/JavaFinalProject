package finalproject.javafinalproject;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;


public class Client extends Application {
    private NetworkUtil networkUtil;
    public Parent root;
    private Stage stage;
    public AnchorPane mainPane;
    public ProductionCompany productionCompany;
    public int pageNumber=-1;
    public void start(Stage PrimaryStage) throws Exception {
        connectToServer();
        pageNumber=0;
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainController controller=fxmlLoader.getController();
        final String content = "Welcome to Ocean of Movies";
        final Animation titleAnimation = new Transition() {
            {
                setCycleDuration(Duration.millis(3000));
            }

            @Override
            protected void interpolate(double frac) {
                final int length = content.length();
                final int n = Math.round(length * (float) frac);
                controller.WelcomeMessage.setText(content.substring(0,n));
            }
        };
        titleAnimation.play();
        var anim1 = numberGeneratingAnimation(100, controller.MovieCount1);
        var anim2 = numberGeneratingAnimation(40, controller.CompanyCount1);
        titleAnimation.setOnFinished(event -> {
            anim1.play();
            controller.MovieCount.setText("+ Movies");

        });
        anim1.setOnFinished(event -> {
            anim2.play();
            controller.CompanyCount.setText("+ Companies");

        });
        final String msg = "The One You're\nLooking For";
        final Animation msgAnimation = new Transition() {
            {
                setCycleDuration(Duration.millis(3000));
            }

            @Override
            protected void interpolate(double frac) {
                final int length = content.length();
                final int n = Math.round(length * (float) frac);
                controller.comments.setText(msg.substring(0,n));
            }
        };
        msgAnimation.play();
        controller.setMain(this);
        stage=PrimaryStage;
        stage.setTitle("Ocean of Movies");
        stage.setX(400);
        stage.setY(150);
        stage.setScene(scene);
        stage.show();
    }

    public void setProductionCompany(ProductionCompany productionCompany) {
        this.productionCompany = productionCompany;
    }

    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }
    public void setHomeScreen() throws IOException {
        pageNumber=1;
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("SecondScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        SecondScene controller=fxmlLoader.getController();
        controller.setLabels(productionCompany.getName(),Long.toString(productionCompany.TotalProfit()));
        controller.setClient(this);
        controller.setProductionCompany(this.productionCompany);
        String name=productionCompany.getName().toUpperCase();
        String link="Company/"+name.replaceAll("[^a-zA-Z0-9]", "_")+".jpg";
        controller.setImageView(link);
        stage.setScene(scene);
        stage.setX(100);
        stage.setY(100);
        stage.show();
    }
    public void connectToServer() throws IOException {
        String serverAddress = "127.0.0.1";
        int serverPort = 33333;
        networkUtil = new NetworkUtil(serverAddress, serverPort);
        new ReaderThread(this);
    }
    public void showAllMovies() throws IOException {
        pageNumber=2;
        var loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AllMovies.fxml"));
        root = loader.load();
        stage.setScene(new javafx.scene.Scene(root));
        AllMovieListController allMovieListController=loader.getController();
        allMovieListController = loader.getController();
        allMovieListController.setClient(this);
        allMovieListController.initiate(productionCompany);
        allMovieListController.setLabels("All Movies",productionCompany.getMovieCount());
        showMyMovies(productionCompany.getMovies());
    }

    private void showMyMovies(List<Movie> movies) throws IOException {
        var loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AllMoviesDisplay.fxml"));
        Parent newRoot = loader.load();
        mainPane.getChildren().add(newRoot);
        AllMoviesDispalyController allMoviesDispalyController= loader.getController();
        allMoviesDispalyController.setClient(this);
        allMoviesDispalyController.initiate(movies);
        stage.setX(200);
        stage.setY(100);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    public void showMaxRevenueMovies() throws IOException {
        pageNumber=3;
        var loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AllMovies.fxml"));
        root = loader.load();
        stage.setScene(new javafx.scene.Scene(root));
        AllMovieListController allMovieListController;
        allMovieListController = loader.getController();
        allMovieListController.setClient(this);
        allMovieListController.initiate(productionCompany);
        allMovieListController.setLabels("Movies With \nMaximum Revenue",productionCompany.MaximumRevenueMovies().size());
        showMyMovies(productionCompany.MaximumRevenueMovies());
    }

    public void ShowLatestMovies() throws IOException {
        pageNumber=4;
        var loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AllMovies.fxml"));
        root = loader.load();
        stage.setScene(new javafx.scene.Scene(root));
        AllMovieListController allMovieListController;
        allMovieListController = loader.getController();
        allMovieListController.setClient(this);
        allMovieListController.initiate(productionCompany);
        allMovieListController.setLabels("Most Recent Movies",productionCompany.RecentMovie().size());
        showMyMovies(productionCompany.RecentMovie());
    }

    public void TransferMovie(Movie movie) throws IOException {
        Stage popUp = new Stage();
        var loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("TransferPopUp.fxml"));
        Parent newRoot = loader.load();
        TransferPopUp transferPopUp = loader.getController();
        transferPopUp.setMain(this);
        transferPopUp.setStage(popUp);
        transferPopUp.setMovie(movie);
        popUp.setScene(new javafx.scene.Scene(newRoot));
        popUp.show();
    }

    public void TransferStart(Movie movie,String companyName) throws IOException {
        TransferredMovie transferredMovie=new TransferredMovie(movie,companyName);
        this.networkUtil.write(transferredMovie);
        System.out.println("Trstrted");
    }

    public void afterTransfer(NewMovieTransferred newMovieTransferred) throws IOException {
        productionCompany.addMovies(newMovieTransferred.getMovie());
        System.out.println(productionCompany.getMovieCount());
        if(pageNumber==1) setHomeScreen();
        if(pageNumber==2) showAllMovies();
        else if(pageNumber==3) showMaxRevenueMovies();
        else if(pageNumber==4) ShowLatestMovies();
    }

    public void removedPhase(Movie movie) throws IOException {
        productionCompany.removeMovie(movie);
        System.out.println(productionCompany.getMovieCount());
        showAllMovies();
    }

    public void ShowAddMovieStage() throws IOException {
        Stage popUp = new Stage();
        var loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddMovie.fxml"));
        Parent newRoot = loader.load();
        AddMovie addMovie = loader.getController();
        addMovie.setUp(this.productionCompany,this);
        addMovie.setStage(popUp);
        popUp.setScene(new javafx.scene.Scene(newRoot));
        popUp.show();
    }

    public void addMovie(Movie movie) throws IOException {
        this.productionCompany.addMovies(movie);
        networkUtil.write(movie);
    }

    public void showAlert() throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incorrect Credentials");
        alert.setHeaderText("Incorrect Credentials");
        alert.setContentText("The username and password you provided is not correct.");
        alert.showAndWait();
    }

    public void searchOption() throws IOException {
        Stage popUp = new Stage();
        var loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SearchOption.fxml"));
        Parent newRoot = loader.load();
        SearchOption searchOption = loader.getController();
        searchOption.setUp(this);
        searchOption.setStage(popUp);
        popUp.setScene(new javafx.scene.Scene(newRoot));
        popUp.show();
    }

    public void searchDetails(String info) throws IOException {
        Stage popUp = new Stage();
        var loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("GetSearchInfo.fxml"));
        Parent newRoot = loader.load();
        GetSearchInfo getSearchInfo = loader.getController();
        getSearchInfo.setUp(this);
        getSearchInfo.setStage(popUp);
        getSearchInfo.setLabel(info);
        getSearchInfo.setType(info);
        popUp.setScene(new javafx.scene.Scene(newRoot));
        popUp.show();
    }
    public void doSearch(String info, String type) throws IOException {
        if(type.equals("Movie Title"))
        {
            pageNumber=4;
            var loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AllMovies.fxml"));
            root = loader.load();
            stage.setScene(new javafx.scene.Scene(root));
            AllMovieListController allMovieListController;
            allMovieListController = loader.getController();
            allMovieListController.setClient(this);
            allMovieListController.initiate(productionCompany);
            allMovieListController.setLabels("",productionCompany.searchMovieByName(info).size());
            showMyMovies(productionCompany.searchMovieByName(info));
        }
        else if(type.equals("Genre"))
        {
            pageNumber=4;
            var loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AllMovies.fxml"));
            root = loader.load();
            stage.setScene(new javafx.scene.Scene(root));
            AllMovieListController allMovieListController;
            allMovieListController = loader.getController();
            allMovieListController.setClient(this);
            allMovieListController.initiate(productionCompany);
            allMovieListController.setLabels("Movies with genre "+info,productionCompany.searchMovieByGenre(info).size());
            showMyMovies(productionCompany.searchMovieByGenre(info));
        }
        else if(type.equals("Released Year"))
        {
            pageNumber=4;
            var loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AllMovies.fxml"));
            root = loader.load();
            stage.setScene(new javafx.scene.Scene(root));
            AllMovieListController allMovieListController;
            allMovieListController = loader.getController();
            allMovieListController.setClient(this);
            allMovieListController.initiate(productionCompany);
            allMovieListController.setLabels("Movies Released in "+info,productionCompany.searchMovieByYear(Integer.parseInt(info)).size());
            showMyMovies(productionCompany.searchMovieByYear(Integer.parseInt(info)));
        }
    }

    public void searchDetailsRuntime(String info) throws IOException {
        Stage popUp = new Stage();
        var loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Runtime.fxml"));
        Parent newRoot = loader.load();
        GetSearchInfo getSearchInfo = loader.getController();
        getSearchInfo.setUp(this);
        getSearchInfo.setStage(popUp);
        getSearchInfo.setType(info);
        getSearchInfo.setLabel(info);
        popUp.setScene(new javafx.scene.Scene(newRoot));
        popUp.show();
    }

    public void doSearchbyRuntime(int parseInt, int parseInt1) throws IOException {
        pageNumber=4;
        var loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AllMovies.fxml"));
        root = loader.load();
        stage.setScene(new javafx.scene.Scene(root));
        AllMovieListController allMovieListController;
        allMovieListController = loader.getController();
        allMovieListController.setClient(this);
        allMovieListController.initiate(productionCompany);
        allMovieListController.setLabels("Movies Between Runtime "+parseInt+" to "+parseInt1,productionCompany.searchMovieByLength(parseInt,parseInt1).size());
        showMyMovies(productionCompany.searchMovieByLength(parseInt,parseInt1));
    }
    //animation
    public Animation numberGeneratingAnimation(int n, Label label){
        return new Transition() {
            {
                setCycleDuration(Duration.millis(2000));
            }

            @Override
            protected void interpolate(double frac) {
                final int n1 = Math.round(n * (float) frac);
                label.setText(String.valueOf(n1));
            }
        };
    }

}
