package finalproject.javafinalproject;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ReaderThread implements Runnable{
    private final Client client;
    String name;
    public ReaderThread(Client client) {
        this.client = client;
        new Thread(this).start();
    }
    @Override
    public void run() {
        try {
            while (true) {
                Object o = client.getNetworkUtil().read();

                if (o != null) {
                    if (o instanceof ProductionCompany) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    client.setProductionCompany((ProductionCompany) o);
                                    client.setHomeScreen();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    if (o instanceof NewMovieTransferred) {
                        System.out.println(((NewMovieTransferred) o).getMovie().getProductionCompany());
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    client.afterTransfer((NewMovieTransferred) o);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    if (o instanceof RemovedMovie) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        client.removedPhase(((RemovedMovie) o).getMovie());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    }
                    if(o instanceof String)
                    {
                        if(o.equals("No Company")) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        client.showAlert();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                client.getNetworkUtil().closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
