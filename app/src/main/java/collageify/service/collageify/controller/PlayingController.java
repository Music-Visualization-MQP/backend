package collageify.service.collageify.controller;

import collageify.db.SQLAccess;
import collageify.service.collageify.Playing;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PlayingController {
    Map<Integer, Playing> synchronizedMap = Collections.synchronizedMap(new HashMap<>());

    private SQLAccess sql = new SQLAccess();
    PlayingController(){


    }



}
