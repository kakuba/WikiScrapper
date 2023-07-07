package com.lastminute.recruitment.client;

import java.util.Objects;

public class JsonWikiClient {

    public String readJson(String link) {
        String name = link.replace("\"", "")
                .replace("http://wikiscrapper.test/", "/wikiscrapper/") + ".json";
        return Objects.requireNonNull(getClass().getResource(name)).getFile();
    }
}
