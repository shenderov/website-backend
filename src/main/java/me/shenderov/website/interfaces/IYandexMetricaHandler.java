package me.shenderov.website.interfaces;

import java.io.IOException;
import java.net.URISyntaxException;

public interface IYandexMetricaHandler {

    String getNewUsersStatistics() throws URISyntaxException, IOException;

    String getUsersStatistics() throws URISyntaxException, IOException;

    String getTrafficSourceStatistics() throws URISyntaxException, IOException;

    String getGeographyStatistics() throws URISyntaxException, IOException;

    String getUrlsStatistics() throws URISyntaxException, IOException;

}
