package com.tradeshift.amazing.core.service.node;

import com.tradeshift.amazing.domain.entity.Hierarchy;
import com.tradeshift.amazing.domain.entity.HierarchyKey;
import com.tradeshift.amazing.domain.entity.Node;
import com.tradeshift.amazing.persistence.repository.HierarchyRepository;
import com.tradeshift.amazing.persistence.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AmazingNodeService implements NodeService{

    private NodeRepository nodeRepository;
    private HierarchyRepository hierarchyRepository;

    @Autowired
    public AmazingNodeService(NodeRepository nodeRepository, HierarchyRepository hierarchyRepository){
        this.nodeRepository = nodeRepository;
        this.hierarchyRepository = hierarchyRepository;
    }

    @Override
    public Node insertNode(Long id, Long parentId) {
        return null;
    }

    @Override
    public List<Node> findAllNodeDescendants(String id) {
        UUID nodeId = UUID.fromString(id);
        return nodeRepository.findAllDescendantsById(nodeId);
    }

    @Transactional
    @Override
    public Node updateParentNode(String id, String newParentId) {
        UUID nodeId = UUID.fromString(id);
        Node node = nodeRepository.findNodeById(nodeId);
        UUID parentId = UUID.fromString(newParentId);

        List<Hierarchy> ancestors = hierarchyRepository.findAllAncestorsById(nodeId);
        List<Hierarchy> descendants = hierarchyRepository.findAllDescendantsById(nodeId);

        List<Hierarchy> newSubtreeHierarchies = buildNewHierarchies(ancestors, descendants, node);

        List<UUID> descendantIds = descendants.stream()
                .map(d -> d.getHierarchyKey().getDescendant().getId()).collect(Collectors.toList());

        hierarchyRepository.deleteOldHierarchies(descendantIds, nodeId);

        newSubtreeHierarchies.stream().forEach( hierarchy -> {
            hierarchyRepository.save(hierarchy);
        });

        Node resultNode = nodeRepository.updateNodeParent(parentId, nodeId);

        return resultNode;
    }

    private List<Hierarchy> buildNewHierarchies(List<Hierarchy> ancestors, List<Hierarchy> descendants, Node node){
        List<Hierarchy> newSubtreeHierarchies = new ArrayList<>();
        Map<Node,Integer> depths = new HashMap<>();

        for(Hierarchy ancestor: ancestors) {
            HierarchyKey key = new HierarchyKey(ancestor.getHierarchyKey().getAncestor(), node);
            newSubtreeHierarchies.add((new Hierarchy(key,ancestor.getDepth()+1)));
            depths.put(ancestor.getHierarchyKey().getAncestor(), ancestor.getDepth()+1);
        }

        for(Hierarchy descendant: descendants) {
            for (Map.Entry<Node, Integer> depth: depths.entrySet()) {
                HierarchyKey key = new HierarchyKey(depth.getKey(), descendant.getHierarchyKey().getDescendant());
                newSubtreeHierarchies.add(new Hierarchy(key,depth.getValue() + descendant.getDepth()));
            }
        }
        return newSubtreeHierarchies;
    }
}
