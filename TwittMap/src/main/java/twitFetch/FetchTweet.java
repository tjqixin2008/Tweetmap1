package twitFetch;

//import org.json.JSONException;
//import org.json.JSONObject;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;//??????
//import java.util.*;

public class FetchTweet {

    private EsHelper esHelper = new EsHelper();



    void fetchTwits() {

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("1iTOUnRyoc0pP5QZda9fiToYW")
                .setOAuthConsumerSecret("qNpusO5WBETNUUS70SzIokWdrhTCSvBdFlYAQO4EFsURl1CC3")
                .setOAuthAccessToken("703297899938115584-E8BZGgbklgk368F4ncfO2uyFYfcO3kv")
                .setOAuthAccessTokenSecret("eQq6dgUccrWauTyec7x10hXyF2IBSYkCtwZ8iC6PsqwCX");

        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                if (status.getGeoLocation() != null && status.getUser() != null) {
                    System.out.print(status);
                    esHelper.uploadTweet(status);
                }
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                //System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                System.out.println("Got stall warning:" + warning);
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };

        twitterStream.addListener(listener);
        twitterStream.sample();
    }

    public static void main(String[] args) {
        FetchTweet fetcher = new FetchTweet();
        fetcher.fetchTwits();
    }
}
