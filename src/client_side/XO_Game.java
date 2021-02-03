package client_side;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import server_side.Server_Controller_I;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class XO_Game extends UnicastRemoteObject implements XO_Game_I {
    private String name;
    private String type;
    private Button[] fields;
    private Pane freze_screen;
    private Pane win_screen;
    private Pane lose_screen;
    private Server_Controller_I server;

    public XO_Game(String name, String type, Server_Controller_I server) throws RemoteException {
        super();
        this.server = server;
        this.name = name;
        this.type = type;
        this.fields = new Button[9];
        for(int i=0;i<9;i++){
            fields[i] = new Button("\uD83D\uDD32");
            fields[i].setMinSize(200,200);
            fields[i].setStyle("-fx-font-size:90;");
            fields[i].setOnAction(event -> {
                this.field_handler((Button) event.getSource());
            });
        }
        this.freze_screen = this.get_Pane("Freze","blue");
        this.freze_screen.setVisible(false);
        this.win_screen = this.get_Pane("You Win","green");
        this.win_screen.setVisible(false);
        this.lose_screen = this.get_Pane("You Lose","red");
        this.lose_screen.setVisible(false);
    }

    private Pane get_Pane(String msg,String color){
        Pane pane = new Pane();
        Label label = new Label(msg);
        label.setStyle("-fx-font-size:150px;-fx-text-fill:"+color+";");
        label.setMinSize(610,715);
        label.setAlignment(Pos.CENTER);
        pane.getChildren().add(label);
        return pane;
    }

    private void field_handler(Button curr_button){
        this.select_field(curr_button);
    }

    private void select_field(Button curr_button){
        if(this.type == "X"){
            curr_button.setText("X");
            curr_button.setStyle("-fx-font-size:90;-fx-text-fill:red;");
        }else{
            curr_button.setText("O");
            curr_button.setStyle("-fx-font-size:90;-fx-text-fill:green;");
        }
        int i=0;
        while(i < 9 && curr_button != this.fields[i]) i++;
        System.out.println("Curr Field : "+String.valueOf(i));
        curr_button.setDisable(true);
        try {
            this.server.player_select_field(this.type,i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Parent createContent(){
        Pane root = new StackPane();
        Pane sub_root = new Pane();
        sub_root.setPrefSize(610,715);
        root.setStyle("-fx-background:#333333");
        VBox bord = new VBox(5);
        Label title = new Label(name+" ("+this.type+")");
        title.setMinSize(610,100);
        title.setAlignment(Pos.CENTER);
        title.setStyle("-fx-text-fill:#40ff00;-fx-font-size:40px;");
        bord.getChildren().add(title);
        for(int i=0;i<3;i++){
            HBox row = new HBox(5);
            for(int j=i*3;j<((i*3)+3);j++){
                row.getChildren().add(this.fields[j]);
            }
            bord.getChildren().add(row);
        }
        sub_root.getChildren().add(bord);
        root.getChildren().add(sub_root);
        root.getChildren().add(this.freze_screen);
        root.getChildren().add(this.win_screen);
        root.getChildren().add(this.lose_screen);
        return root;
    }

    public Scene get_primary_scene() throws Exception{
        return new Scene(this.createContent());
    }

    public void select_opposite_field(int index) throws Exception{
        if(index < 0 || index >= 9) return;
        Button curr_button = this.fields[index];
        if(this.type == "O"){
            curr_button.setText("X");
            curr_button.setStyle("-fx-font-size:90;-fx-text-fill:red;");
        }else{
            curr_button.setText("O");
            curr_button.setStyle("-fx-font-size:90;-fx-text-fill:green;");
        }
        curr_button.setDisable(true);
    }

    public void freze() throws Exception{
        this.freze_screen.setVisible(true);
    }

    public void unfreze() throws Exception{
        this.freze_screen.setVisible(false);
    }

    public void make_win() throws Exception{
        this.unfreze();
        this.win_screen.setVisible(true);
    }
    public void make_lose() throws Exception{
        this.unfreze();
        this.lose_screen.setVisible(true);
    }

}

