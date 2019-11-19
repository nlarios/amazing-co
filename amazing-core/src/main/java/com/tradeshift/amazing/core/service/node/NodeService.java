package com.tradeshift.amazing.core.service.node;

import com.tradeshift.amazing.domain.entity.Node;

import java.util.List;
import java.util.UUID;

public interface NodeService {
    Node insertNode(Long id, Long parentId);

    List<Node> findAllNodeDescendants(Long id);

    Integer updateParentNode(Long nodeId, Long newParentId);

}
