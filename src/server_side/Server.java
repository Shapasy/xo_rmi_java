package server_side;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server extends Application {
    private String name;
    private Registry registry;

    public Server() throws Exception{
        this.name = "Server";
        this.registry =  LocateRegistry.createRegistry(5099);
        registry.rebind("server",new Server_Controller());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Server");
        primaryStage.setMaxWidth(400);
        primaryStage.setMaxHeight(400);
        Pane root = new Pane();
        root.setStyle("-fx-background:#333333");
        Label title = new Label("Server is up & running");
        title.setAlignment(Pos.CENTER);
        title.setStyle("-fx-text-fill:#40ff00;-fx-font-size:30px;-fx-padding:10px;");
        root.getChildren().add(title);
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
//        primaryStage.getIcons().add(new Image("player_icon.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
