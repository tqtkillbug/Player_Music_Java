package com.tientran.player;

import com.fasterxml.jackson.core.JsonParseException;
import com.tientran.player.model.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        List list = new ArrayList();
        Song song2 = new Song("anhdssd", "C:/yeu/ada", "C:/sd/em");
        list.add(song2);

        try {
            System.out.println(JacksonParser.INSTANCE.toJson(list));
            List<Song> list1 = JacksonParser.INSTANCE.toList("[{\"songName\":\"anhdssd\",\"imagePath\":\"C:/yeu/ada\",\"songPath\":\"C:/sd/em\"}]", Song.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        }
    }
}
