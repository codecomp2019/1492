package com.example.memeder;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WebHandler {

    final OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            return e.toString();
        }
    }

}
