package com.lastminute.recruitment.domain;

import com.lastminute.recruitment.domain.error.WikiPageNotFound;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WikiScrapperTest {

    @Mock
    private WikiReader wikiReader;

    @Mock
    private WikiPageRepository repository;

    @InjectMocks
    private WikiScrapper wikiScrapper;

    @Test
    public void testRead() {
        // given
        final String link = "http://test.com";

        WikiPage wikiPage = new WikiPage("testTitle", "testContent", "http://selflink.com", Collections.singletonList(link));

        given(wikiReader.read(anyString())).willReturn(wikiPage);
        doNothing().when(repository).save(any());

        // when
        wikiScrapper.read(link);

        // then
        verify(wikiReader, times(1)).read(anyString());
        verify(repository, times(1)).save(any());

    }

    @Test
    public void testReadPageNotFound() {
        // given
        final String link = "http://test.com";

        doThrow(WikiPageNotFound.class).when(wikiReader).read(anyString());

        // when
        Assertions.assertThrows(WikiPageNotFound.class, () -> wikiScrapper.read(link));

        // then
        verify(wikiReader, times(1)).read(anyString());
        verify(repository, times(0)).save(any());

    }
}
