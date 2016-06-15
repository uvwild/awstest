package org.funtime.helper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;

/**
 * Created by uv on 14.06.2016 for awstest
 */
@JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
public abstract class ResponseEntityMixin<T> {
    @JsonProperty("headers")
    abstract MultiValueMap<String, String> getHeaders();
    //        HttpHeaders headers

    @JsonProperty("body")
    abstract T getBody();

    @JsonProperty("statusCode")
    abstract HttpStatus getStatusCode();

    @JsonCreator
    public ResponseEntityMixin(@JsonProperty("headers") MultiValueMap<String, String> headers, @JsonProperty("body") T body, @JsonProperty("statusCode") HttpStatus statusCode) {
    }
}
