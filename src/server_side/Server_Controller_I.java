package server_side;

import client_side.XO_Game;
import client_side.XO_Game_I;

import java.rmi.Remote;

public interface Server_Controller_I extends Remote {
    public String echo(String msg) throws Exception;
    public void set_x_player(XO_Game_I player) throws Exception;
    public void set_o_player(XO_Game_I player) throws Exception;
    public void player_select_field(String type, int index) throws Exception;
}
