package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirportResource {
    Airport airport;
    String requestId;
    long requestTimeStamp;
    String responseId;
    long responseTimeStamp;
}
