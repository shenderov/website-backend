package me.shenderov.website.handlers;

import me.shenderov.website.config.SettingsConfig;
import me.shenderov.website.interfaces.IYandexMetricaHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

public class YandexMetricaHandler implements IYandexMetricaHandler {

    private final static Logger LOGGER = Logger.getLogger(SettingsConfig.class.getName());

    private static final SimpleDateFormat SDF = new SimpleDateFormat("YYYY-MM-dd");

    public String getNewUsersStatistics() throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder("https://api-metrika.yandex.net/stat/v1/data/bytime/");
         builder
                 .setParameter("ids", System.getProperty("application.statistics.ym.id"))
                 .setParameter("metrics", "ym:s:newUsers")
                 .setParameter("sort",  "-ym:s:newUsers")
                 .setParameter("group", "day")
                 .setParameter("date1", getStartDay())
                 .setParameter("date2", getCurrentDay());
        return getData(builder);
    }

    public String getUsersStatistics() throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder("https://api-metrika.yandex.net/stat/v1/data/bytime/");
        builder
                .setParameter("ids", System.getProperty("application.statistics.ym.id"))
                .setParameter("metrics", "ym:s:users")
                .setParameter("sort",  "-ym:s:users")
                .setParameter("group", "day")
                .setParameter("date1", getStartDay())
                .setParameter("date2", getCurrentDay());
        return getData(builder);
    }

    public String getTrafficSourceStatistics() throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder("https://api-metrika.yandex.net/stat/v1/data/bytime/");
        builder
                .setParameter("ids", System.getProperty("application.statistics.ym.id"))
                .setParameter("dimensions", "ym:s:lastSignTrafficSource")
                .setParameter("metrics", "ym:s:visits")
                .setParameter("sort",  "-ym:s:visits")
                .setParameter("group", "all")
                .setParameter("date1", getStartDay())
                .setParameter("date2", getCurrentDay());
        return getData(builder);
    }

    public String getGeographyStatistics() throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder("https://api-metrika.yandex.net/stat/v1/data/");
        builder
                .setParameter("ids", System.getProperty("application.statistics.ym.id"))
                .setParameter("dimensions", "ym:s:regionCountry,ym:s:regionArea,ym:s:regionCity")
                //.setParameter("dimensions", "ym:s:regionCountry")
                //.setParameter("metrics", "ym:s:visits,ym:s:users,ym:s:avgVisitDurationSeconds")
                .setParameter("metrics", "ym:s:visits,ym:s:users,ym:s:bounceRate,ym:s:pageDepth,ym:s:avgVisitDurationSeconds")
                .setParameter("sort",  "-ym:s:visits")
                .setParameter("filters",  "ym:s:regionCountry!n")
                .setParameter("group", "day")
                .setParameter("date1", getStartDay())
                .setParameter("date2", getCurrentDay());
        return getData(builder);
    }

    public String getUrlsStatistics() throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder("https://api-metrika.yandex.net/stat/v1/data/bytime/");
        builder
                .setParameter("ids", System.getProperty("application.statistics.ym.id"))
                .setParameter("dimensions", "ym:pv:URLHash")
                .setParameter("metrics", "ym:pv:pageviews")
                .setParameter("sort",  "-ym:pv:pageviews")
                .setParameter("filters",  "ym:pv:URLHash!n")
                .setParameter("date1", getStartDay())
                .setParameter("date2", getCurrentDay());
        return getData(builder);
    }

    private String getData(URIBuilder builder) throws URISyntaxException, IOException {
        HttpGet getRequest = new HttpGet(builder.build());
        getRequest.setHeader("Authorization", System.getProperty("application.statistics.ym.token"));
        String data = null;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClientBuilder.create().build();
            response = httpClient.execute(getRequest);
            data = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            assert response != null;
            response.close();
            httpClient.close();
        }
        return data;
    }

    private String getCurrentDay(){
        return SDF.format(System.currentTimeMillis());
    }

    private String getStartDay(){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.DAY_OF_YEAR, -30);
        return SDF.format(c.getTimeInMillis());
    }

}
