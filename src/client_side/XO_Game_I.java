package client_side;

import javafx.scene.Scene;

import java.rmi.Remote;

public interface XO_Game_I  extends Remote {
//    public Scene get_primary_scene() throws Exception;
    public void select_opposite_field(int index) throws Exception;
    public void freze() throws Exception;
    public void unfreze() throws Exception;
    public void make_win() throws Exception;
    public void make_lose() throws Exception;
}
