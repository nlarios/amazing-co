package com.tradeshift.amazing.application.api;

import com.tradeshift.amazing.core.mapper.EntityDtoMapper;
import com.tradeshift.amazing.core.service.node.NodeService;
import com.tradeshift.amazing.domain.dto.NodeDTO;
import com.tradeshift.amazing.domain.entity.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path="nodes")
public class AmazingController {

    private NodeService nodeService;
    private EntityDtoMapper entityDtoMapper;

    @Autowired
    public AmazingController(NodeService nodeService, EntityDtoMapper entityDtoMapper){
        this.nodeService = nodeService;
        this.entityDtoMapper = entityDtoMapper;
    }


    @RequestMapping(value = "/descendants/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllNodeDescendants(@PathVariable("id") String id) {
        List<Node> descendants = nodeService.findAllNodeDescendants(id);
        List<NodeDTO> nodeDTOS = descendants.stream().map(d-> entityDtoMapper.map(d)).collect(Collectors.toList());
        return new ResponseEntity<>(nodeDTOS,HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateNodeParent(@PathVariable("id") String id, @RequestParam("parentId") String parentId) {
        Node node = nodeService.updateParentNode(id, parentId);
        NodeDTO nodeDTO = entityDtoMapper.map(node);
        return new ResponseEntity<>(nodeDTO,HttpStatus.OK);
    }

}
