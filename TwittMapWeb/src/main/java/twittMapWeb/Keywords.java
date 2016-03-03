package twittMapWeb;

/**
 * Created by fmy9209 on 3/2/16.
 */
public class Keywords {
    public String keyword(String text) {
        String result = "";
        if (text.contains("food")) {
            result = "food";
        } else if (text.contains("love")) {
            result = "love";
        } else if (text.contains("job")) {
            result = "job";
        } else if (text.contains("fashion")) {
            result = "fashion";
        } else if (text.contains("lol") || text.contains("LoL") || text.contains("LOL")) {
            result = "lol";
        } else {
            result = null;
        }
        return result;
    }

}
