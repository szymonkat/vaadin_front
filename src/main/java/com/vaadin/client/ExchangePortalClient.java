package com.vaadin.client;

import com.vaadin.dto.ExchangePortalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

@Component
public class ExchangePortalClient {
    @Autowired
    private ClientConfig clientConfig;

    @Autowired
    private RestTemplate restTemplate;

    public URI getUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(clientConfig.getBackApiAddress() + "/exchange/")
                .build().encode().toUri();
        return url;
    }

    public List<ExchangePortalDto> getExchangePortals() {
        try {
            ExchangePortalDto[] boardsResponse = restTemplate.getForObject(getUrl(), ExchangePortalDto[].class);
            return Arrays.asList(ofNullable(boardsResponse).orElse(new ExchangePortalDto[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public ExchangePortalDto getExchangePortalById(Long exchangePortalId) {
        URI url = UriComponentsBuilder.fromHttpUrl(clientConfig.getBackApiAddress() + "/exchange/" + exchangePortalId)
                .build().encode().toUri();
        try {
            ExchangePortalDto boardsResponse = restTemplate.getForObject(url, ExchangePortalDto.class);
            return boardsResponse;
        } catch (RestClientException e) {
            return new ExchangePortalDto();
        }
    }
}