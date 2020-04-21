package com.example.altimetrikproject.common;

import okhttp3.HttpUrl;

public class RequestBuilder {

    public static HttpUrl buildURL() {
        return new HttpUrl.Builder()
                .scheme("http") //http
                .host("starlord.hackerearth.com")
                .addPathSegment("kickstarter")
                .build();
    }
}
