package com.lastminute.recruitment.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastminute.recruitment.domain.WikiScrapper;
import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WikiScrapperResourceIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WikiScrapper wikiScrapper;


    @Test
    public void testScrapWikipedia() throws Exception {
        doNothing().when(wikiScrapper).read(anyString());

        mvc.perform( MockMvcRequestBuilders
                .post("/wiki/scrap")
                .content("http://testLink.com")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testScrapWikipediaNotFound() throws Exception {
        doThrow(WikiPageNotFound.class).when(wikiScrapper).read(anyString());

        mvc.perform( MockMvcRequestBuilders
                .post("/wiki/scrap")
                .content("http://testLink.com")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
