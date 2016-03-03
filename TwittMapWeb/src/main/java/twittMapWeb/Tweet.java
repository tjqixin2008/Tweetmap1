package twittMapWeb;

/**
 * Created by fmy9209 on 3/2/16.
 */
import io.searchbox.annotations.JestId;
import twitter4j.JSONException;
import twitter4j.JSONObject;


public class Tweet {
    private String user;
    private String timestamp;
    private String text;
    private String keyword;
    private double latitude;
    private double longitude;
    private String url;


    public static Tweet fromJSON(JSONObject json) {

        try {
            return new Tweet (
                    json.getString("user"),
                    json.getString("timestamp"),
                    json.getString("text"),
                    json.getString("keyword"),
                    json.getString("latitude"),
                    json.getString("longitude"),
                    json.getString("url"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Tweet(String user, String timestamp, String text, String keyword,
                       String latitude, String longitude, String url) {
        //this.id = Long.valueOf(id);
        this.user = user;
        this.timestamp = timestamp;
        this.text = text;
        this.keyword = keyword;
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);

        this.url = url;
    }
    public String getContent(String string) {
        if (string.equals("user")) {
            return user;
        }
        if (string.equals("timestamp")) {
            return timestamp;
        }
        if (string.equals("text")) {
            return text;
        }
        if (string.equals("keyword")) {
            return keyword;
        }

        if (string.equals("user")) {
            return user;
        }
        return null;
    }
    public double getGeo(String string) {
        if (string.equals("longitude")) {
            return longitude;
        }
        if (string.equals("latitude")) {
            return latitude;
        }
        return 0;
    }



}
