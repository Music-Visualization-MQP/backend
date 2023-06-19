package collageify.service.collageify.controller;

import collageify.db.SQLAccess;
import collageify.service.collageify.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PlayerController {
    Map<Integer, Player> synchronizedMap = Collections.synchronizedMap(new HashMap<>());

    private SQLAccess sql = new SQLAccess();
    PlayerController(){


    }



}
