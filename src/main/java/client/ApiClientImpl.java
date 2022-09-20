package client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.RequestDto;
import model.AirportResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.httpcache4j.uri.URIBuilder;

import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ApiClientImpl implements ApiClient {
    HttpClient httpClient = HttpClient.newBuilder().build();
    Logger log = LogManager.getLogger(ApiClientImpl.class);

    @Override
    public CompletableFuture<AirportResource> getAirport(int id) throws URISyntaxException {
        var requestDto = createRequest(id);
        HttpRequest request = HttpRequest.newBuilder(URIBuilder.fromString("http://localhost:3000/airport")
                .addParameter("id", requestDto.getId())
                .addParameter("threadName", requestDto.getThreadName())
                .addParameter("requestId", requestDto.getRequestId())
                .addParameter("requestTimeStamp", String.valueOf(requestDto.getRequestTimeStamp())).toURI()).GET().build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(this::parseResponse);

    }

    private RequestDto createRequest(int id) {
        String threadName = Thread.currentThread().getName();
        String requestId = UUID.randomUUID().toString();
        long timeStamp = Instant.now().toEpochMilli();
        return new RequestDto(String.valueOf(id), threadName, requestId, timeStamp);
    }

    private AirportResource parseResponse(HttpResponse<String> response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body(), AirportResource.class);
        }catch (JsonProcessingException e) {
            log.error("Parse exception " + e);
            throw new ResponseParseException(e);
        }
    }
}
