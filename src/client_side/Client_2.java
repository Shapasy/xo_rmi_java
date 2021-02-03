package client_side;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import server_side.Server_Controller_I;

import java.rmi.Naming;

public class Client_2 extends Application {
    private String name;
    private XO_Game game;
    private Server_Controller_I server;

    public Client_2() throws Exception{
        this.name = "Client_2";
        this.server = (Server_Controller_I) Naming.lookup("rmi://localhost:5099/server");
        this.game = new XO_Game(this.name,"O",this.server);
        this.server.set_o_player((XO_Game_I) this.game);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene primaryScene = this.game.get_primary_scene();
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle(this.name);
        primaryStage.setResizable(false);
//        primaryStage.getIcons().add(new Image("player_icon.png"));
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
