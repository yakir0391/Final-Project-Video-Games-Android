package com.example.finalproject_videogames.services;

import android.os.StrictMode;

import com.example.finalproject_videogames.models.Game;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class gamesServiceAPI {

    public static ArrayList<Game> getGameList() {

        ArrayList<Game> arr = new ArrayList<>();

        String sURL = "https://www.giantbomb.com/api/games/?api_key=2b5704ca211042d7e36887cc9ca19bd263caeb05&format=json&field_list=name,deck,description,original_release_date,platforms,image";

        URL url = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            url = new URL(sURL);
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        try {
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject rootObject = root.getAsJsonObject();
            JsonArray rootArray = rootObject.getAsJsonArray("results");

            for (JsonElement je : rootArray) {
                JsonObject obj = je.getAsJsonObject();

                String name = obj.has("name") && !obj.get("name").isJsonNull() ? obj.get("name").getAsString() : "";
                String deck = obj.has("deck") && !obj.get("deck").isJsonNull() ? obj.get("deck").getAsString() : "";
                String originalReleaseDate = obj.has("original_release_date") && !obj.get("original_release_date").isJsonNull() ? obj.get("original_release_date").getAsString() : "";

                List<Game.Platform> platforms = new ArrayList<>();
                if (obj.has("platforms") && !obj.get("platforms").isJsonNull()) {
                    JsonArray platformsArray = obj.getAsJsonArray("platforms");
                    for (JsonElement platformElement : platformsArray) {
                        JsonObject platformObj = platformElement.getAsJsonObject();
                        String platformName = platformObj.has("name") && !platformObj.get("name").isJsonNull() ? platformObj.get("name").getAsString() : "";
                        platforms.add(new Game.Platform(platformName));
                    }
                }

                Game.Image image = null;
                if (obj.has("image") && !obj.get("image").isJsonNull()) {
                    JsonObject imageObj = obj.getAsJsonObject("image");
                    String imageUrl = imageObj.has("original_url") && !imageObj.get("original_url").isJsonNull() ? imageObj.get("original_url").getAsString() : "";
                    image = new Game.Image(imageUrl);
                }

                Game game = new Game(name, deck, originalReleaseDate, platforms, image);
                arr.add(game);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return arr;

    }

}
