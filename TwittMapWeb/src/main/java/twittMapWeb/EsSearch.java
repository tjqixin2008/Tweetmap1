package twittMapWeb;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import twitter4j.JSONException;
import java.io.IOException;
import java.util.*;


/**
 * Created by fmy9209 on 3/2/16.
 */
public class EsSearch {

    public List<Tweet> searchKey(String key) throws IOException, JSONException {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder("http://search-tweet-es-htqqifxx67sifj7m47tf3ejdxa.us-east-1.es.amazonaws.com")
                .multiThreaded(true)
                .build());
        JestClient client = factory.getObject();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        Search search = new Search.Builder(searchSourceBuilder.toString())
                // multiple index or types can be added.
                .addIndex(key)
                //.addType(type)
                .build();
        SearchResult result = client.execute(search);
        List<Tweet> tweets = result.getSourceAsObjectList(Tweet.class);
        return tweets;
    }
//    public static void main(String[] args) throws IOException, JSONException {
//        EsSearch es = new EsSearch();
//        try {
//            es.searchKey("job");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}
