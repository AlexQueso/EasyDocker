package com.include.easydocker.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestToApi {
    private HttpURLConnection connection;

    public RequestToApi(String host, String endpoint, String json) throws IOException {
        URL url = new URL(host + endpoint);
        this.connection = (HttpURLConnection)url.openConnection();

        this.connection.setRequestMethod("POST");
        this.connection.setRequestProperty("Content-Type", "application/json; utf-8");
        this.connection.setRequestProperty("Accept", "application/json");
        this.connection.setDoOutput(true);

        try(OutputStream os = this.connection.getOutputStream()) {
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
    }

    public BufferedReader listenTCPSocket(int port) throws IOException {
        ServerSocket welcomeSocket = new ServerSocket(port);
        Socket connectionSocket = welcomeSocket.accept();
        return new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
    }

    public String getResponse() throws IOException {
        StringBuilder response = new StringBuilder();
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine).append("\n");
            }
        }

        return response.toString();
    }


//    public static void main(String[] args){
//        Map<String, String> map = new HashMap<>();
//        map.put("dockerfile", "FROM ubuntu:16.04");
//        map.put("streaming", "");
//
//        String response = null;
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            String jsonBody = mapper.writerWithDefaultPrettyPrinter()
//                    .writeValueAsString(map);
//            RequestToApi connectionToService = new RequestToApi("http://localhost:5000", "/build", jsonBody);
//
//            BufferedReader br = connectionToService.listenTCPSocket(58741);
//            String line;
//            while((line = br.readLine()) != null)
//                System.out.println(line);
//
//            response = connectionToService.getResponse();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("response:");
//        System.out.println(response);
//    }

    public static void main(String[] args){
        try {
            ServerSocket welcomeSocket = new ServerSocket(9090);
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader br = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            String line;
            while((line = br.readLine()) != null)
                System.out.println(line);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}