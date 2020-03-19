package com.include.easydocker.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpHandler {
    private String host;

    public HttpHandler(String host) {
        this.host = host;
    }

    public String sendPostRequest(String endpoint, String json) throws IOException {
        URL url = new URL(host + endpoint);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);

        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        StringBuilder response = new StringBuilder();
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                System.out.println(responseLine + "\n");
                response.append(responseLine).append("\n");
            }
        }

        return response.toString();
    }


    public static void main(String[] args){
        HttpHandler httpHandler = new HttpHandler("http://localhost:5001");

        Map<String, String> map = new HashMap<>();
        map.put("dockerfile", "FROM ubuntu:16.04 \n" +
                " RUN apt-get update -y && apt-get install -y python-pip python-dev");

        String response = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonResult = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(map);
            response = httpHandler.sendPostRequest("/build", jsonResult);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(response);
    }

}