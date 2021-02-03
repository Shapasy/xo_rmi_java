package server_side;

import client_side.XO_Game_I;

import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.HashSet;

public class Server_Controller extends UnicastRemoteObject implements Server_Controller_I {

    private HashMap<XO_Game_I, HashSet<Integer>> players_fields;
    public XO_Game_I x_player;
    public XO_Game_I o_player;

    protected Server_Controller() throws Exception {
        super();
        this.players_fields = new HashMap<XO_Game_I,HashSet<Integer>>();
    }

    @Override
    public String echo(String msg) throws Exception {
        return "Server: "+msg;
    }

    @Override
    public void player_select_field(String type, int index) throws Exception{
        XO_Game_I player = (type.equals("X")) ? x_player : o_player;
        XO_Game_I next_player = (type.equals("X")) ? o_player : x_player;
        this.players_fields.get(player).add(index);
        next_player.select_opposite_field(index);
        if (this.is_winnable_state(player)) {
            player.make_win();
            next_player.make_lose();
            return;
        }
        next_player.unfreze();
        player.freze();
    }

    private boolean is_winnable_state(XO_Game_I player){
        HashSet<Integer> selected_fields = this.players_fields.get(player);
        int[][] winnable_states = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
        for(int[] winnable_state : winnable_states){
            boolean found_all = true;
            for(int field : winnable_state){
                if(!selected_fields.contains(field)){
                    found_all = false;
                }
            }
            if(found_all) return true;
        }
        return false;
    }

    @Override
    public void set_x_player(XO_Game_I player) throws Exception {
        this.x_player =  player;
        this.players_fields.put(this.x_player,new HashSet<Integer>());
    }

    @Override
    public void set_o_player(XO_Game_I player) throws Exception {
        this.o_player = player;
        this.players_fields.put(this.o_player,new HashSet<Integer>());
        this.o_player.freze();
    }


}
