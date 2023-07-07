package com.lastminute.recruitment.rest;

import com.lastminute.recruitment.domain.WikiScrapper;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/wiki")
@RestController
public class WikiScrapperResource {

    private WikiScrapper wikiScrapper;

    @Autowired
    public WikiScrapperResource(WikiScrapper wikiScrapper) {
        this.wikiScrapper = wikiScrapper;
    }

    @PostMapping("/scrap")
    public void scrapWikipedia(@RequestBody String link) {
        try {
            this.wikiScrapper.read(link);
        } catch (WikiPageNotFound e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage()
            );
        }
    }

}
