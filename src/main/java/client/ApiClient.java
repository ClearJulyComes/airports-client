package client;

import model.AirportResource;

import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;

public interface ApiClient {
    CompletableFuture<AirportResource> getAirport(int requestDto) throws URISyntaxException;
}
