package com.include.easydocker.Rabbit;

import com.fasterxml.jackson.annotation.JsonProperty;


public class DockerResponse {

    private String response;

    public DockerResponse(@JsonProperty("response") String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "DockerResponse{" +
                "response='" + response + '\'' +
                '}';
    }
}