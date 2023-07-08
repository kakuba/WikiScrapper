package com.lastminute.recruitment;

import com.lastminute.recruitment.client.HtmlWikiClient;
import com.lastminute.recruitment.client.JsonWikiClient;
import com.lastminute.recruitment.domain.*;
import com.lastminute.recruitment.persistence.InMemoryWikiPageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
public class WikiScrapperConfiguration {

    @Bean
    @Profile("!json")
    public WikiReader htmlWikiReader() {
        HtmlWikiClient htmlWikiClient = new HtmlWikiClient();
        return link -> new WikiPage("", htmlWikiClient.readHtml(link), link, List.of(link));
    }

    @Bean
    @Profile("json")
    public WikiReader jsonWikiReader() {
        JsonWikiClient jsonWikiClient = new JsonWikiClient();
        return link -> new WikiPage("", jsonWikiClient.readJson(link), link, List.of(link));
    }

    @Bean
    public WikiPageRepository wikiPageRepository() {
        return new InMemoryWikiPageRepository();
    }

    @Bean
    public WikiScrapper wikiScrapper(WikiPageRepository wikiPageRepository, WikiReader wikiReader) {
        return new WikiScrapper(wikiReader, wikiPageRepository);
    }
}
