package com.valfed.githubclient.network;

import android.net.Uri;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpClient {
    private static final String REPOSITORY_SEARCH_URL = "https://api.github.com/search/repositories";
    private static final String QUERY_PARAM = "q";

    public String getRepositories(String query) throws IOException {
        String requestUrl = Uri.parse(REPOSITORY_SEARCH_URL)
            .buildUpon()
            .appendQueryParameter(QUERY_PARAM, query)
            .build()
            .toString();

        return getResponse(requestUrl);
    }

    @NonNull
    private String getResponse(String requestUrl) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            connection.connect();
            InputStream in;
            int status = connection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                in = connection.getInputStream();
            } else {
                in = connection.getErrorStream();
            }
            return convertStreamToString(in);
        } finally {
            connection.disconnect();
        }
    }

    private String convertStreamToString(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        stream.close();

        return sb.toString();
    }
}
