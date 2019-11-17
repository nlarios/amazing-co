package com.tradeshift.amazing.core.service.node;

import com.tradeshift.amazing.domain.entity.Node;

import java.util.List;

public class AmazingNodeService implements NodeService{


    @Override
    public Node insertNode(Long id, Long parentId) {
        return null;
    }

    @Override
    public List<Node> findAllNodeDescendants(String id) {
        return null;
    }

    @Override
    public Node changeParentNode(String nodeId, String newParentId) {
        return null;
    }
}
