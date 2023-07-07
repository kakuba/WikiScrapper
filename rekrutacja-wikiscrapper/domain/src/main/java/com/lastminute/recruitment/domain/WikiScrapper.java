package com.lastminute.recruitment.domain;

import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WikiScrapper {

    private final WikiReader wikiReader;
    private final WikiPageRepository repository;

    @Autowired
    public WikiScrapper(WikiReader wikiReader, WikiPageRepository repository) {
        this.wikiReader = wikiReader;
        this.repository = repository;
    }


    public void read(String link) throws WikiPageNotFound {
        WikiPage wikiPage;
        try {
            wikiPage = this.wikiReader.read(link);
        } catch (NullPointerException e) {
            throw new WikiPageNotFound("Page under this link: " + link + " was not found");
        }
        this.repository.save(wikiPage);
    }

}
