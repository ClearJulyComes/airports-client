package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestDto {
    String id;
    String threadName;
    String requestId;
    long requestTimeStamp;
}
