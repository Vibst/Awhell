package com.example;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

public class Delete {
    public static void main(String[] args) throws IOException{
        String url = "http://192.168.7.176:7777/npcf-policyauthorization/v1/app-sessions/6/delete";
        OkHttpClient client = new OkHttpClient.Builder().protocols(java.util.Arrays.asList(Protocol.H2_PRIOR_KNOWLEDGE)).build();
        Request request = new Request.Builder().url(url).addHeader("Accept", "application/json").addHeader("accept", "application/problem+json").addHeader("content-type", "application/json").addHeader("user-agent", "PCSCF").build();
        try (Response response = client.newCall(request).execute())
        {
            System.out.println("HTTP Response Code: " + response.code());
            System.out.println(response.body().string());
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}