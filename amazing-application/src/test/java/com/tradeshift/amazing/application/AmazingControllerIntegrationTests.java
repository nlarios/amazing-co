package com.tradeshift.amazing.application;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.bind.v2.model.core.TypeRef;
import com.tradeshift.amazing.core.mapper.EntityDtoMapper;
import com.tradeshift.amazing.domain.dto.NodeDTO;
import com.tradeshift.amazing.domain.entity.Node;
import com.tradeshift.amazing.persistence.repository.NodeRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class AmazingControllerIntegrationTests {

    private static final ObjectMapper mapper = new ObjectMapper();

    @LocalServerPort
    private int port;
    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @Autowired
    private NodeRepository nodeRepository;


    @Test
    public void getDescendantsTest() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/nodes/descendants/0"), HttpMethod.GET, entity, String.class);
        String actualResponse = response.getBody();
        List<NodeDTO> actualNodes = mapper.readValue(actualResponse, new TypeReference<List<NodeDTO>>() {});

        assertTrue(actualNodes.size()>1);
    }

    @Test
    public void changeParentTest() throws Exception {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/nodes/1?parentId=2"), HttpMethod.PUT, entity, String.class);
        ResponseEntity<String> descendantsResponse = restTemplate.exchange(
                createURLWithPort("/nodes/descendants/0"), HttpMethod.GET, entity, String.class);
        String actualResponse = descendantsResponse.getBody();
        List<NodeDTO> descendants = mapper.readValue(actualResponse, new TypeReference<List<NodeDTO>>() {});
        descendants =  descendants.stream().filter(d->d.getParentId() == 2).collect(Collectors.toList());
        assertTrue(descendants.get(0).getParentId()==2);
    }
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
