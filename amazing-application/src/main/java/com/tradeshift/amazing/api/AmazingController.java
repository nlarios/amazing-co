package com.tradeshift.amazing.api;

import com.tradeshift.amazing.core.service.node.NodeService;
import com.tradeshift.amazing.domain.entity.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(path = "nodes", produces = MediaType.APPLICATION_JSON_VALUE)
public class AmazingController {

    private NodeService nodeService;

    @Autowired
    public AmazingController(NodeService nodeService){
        this.nodeService = nodeService;
    }


    @RequestMapping(path = "/descendants/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<?> getAllNodeDescendants(@PathParam("id") String id) {
        List<Node> descendants = nodeService.findAllNodeDescendants(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/nodes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ResponseEntity<?> updateNodeParent(@PathParam("id") String id, @RequestParam("parentId") String parentId) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
