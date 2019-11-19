package com.tradeshift.amazing.core.service.node;

import com.tradeshift.amazing.domain.entity.Hierarchy;
import com.tradeshift.amazing.domain.entity.HierarchyKey;
import com.tradeshift.amazing.domain.entity.Node;
import com.tradeshift.amazing.persistence.repository.HierarchyRepository;
import com.tradeshift.amazing.persistence.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
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
    public List<Node> findAllNodeDescendants(Long id) {
        return nodeRepository.findAllDescendantsById(id);
    }

    @Transactional
    @Override
    public Integer updateParentNode(Long nodeId, Long newParentId) {

        Node node = nodeRepository.findNodeById(nodeId);
        Node newParentNode = nodeRepository.findNodeById(newParentId);

        List<Hierarchy> ancestors = hierarchyRepository.findAllAncestorsById(nodeId);
        List<Hierarchy> descendants = hierarchyRepository.findAllDescendantsById(nodeId);

        List<Hierarchy> newSubtreeHierarchies = buildNewHierarchies(ancestors, descendants, node);

        deleteOldHierarchies(descendants, nodeId);

        saveNewHierarchies(newSubtreeHierarchies);

        updateHeightsOfDescendants(descendants, newParentNode);

        Integer result = nodeRepository.updateNodeParent(newParentNode, nodeId, newParentNode.getHeight() + 1);

        return result;
    }

    void updateHeightsOfDescendants(List<Hierarchy> descendants, Node parentNode){
        descendants.stream().forEach(descendant -> {
            nodeRepository.updateNodeHeightById(descendant.getHierarchyKey().getDescendant().getId(), parentNode.getHeight() + descendant.getDepth() + 1);
        });

    }

    void saveNewHierarchies(List<Hierarchy> hierarchies) {
        hierarchies.stream().forEach( hierarchy -> {
            hierarchyRepository.save(hierarchy);
        });
    }

    void deleteOldHierarchies(List<Hierarchy> descendants, Long nodeId){
        List<Long> descendantIds = descendants.stream()
                .map(d -> d.getHierarchyKey().getDescendant().getId()).collect(Collectors.toList());
        System.out.println(descendantIds);
        hierarchyRepository.deleteOldHierarchies(descendantIds, nodeId);
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
