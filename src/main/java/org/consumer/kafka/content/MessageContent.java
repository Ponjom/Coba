package org.consumer.kafka.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MessageContent {
    @JsonProperty("body")
    private String body;
    @JsonProperty("properties")
    private List<Object> properties;
    @JsonProperty("headers")
    private List<Object> headers;
}
