package com.tradeshift.amazing.persistence.repository;

import com.tradeshift.amazing.domain.entity.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface NodeRepository extends CrudRepository<Node, UUID> {



    @Query("SELECT n FROM Node n JOIN com.tradeshift.amazing.domain.entity.Hierarchy nh ON n.id = nh.hierarchyKey.descendant.id WHERE nh.hierarchyKey.ancestor.id = ?1 AND nh.depth > 0")
    List<Node> findAllDescendantsById(UUID id);

    @Query("SELECT n FROM Node n JOIN com.tradeshift.amazing.domain.entity.Hierarchy nh ON n.id = nh.hierarchyKey.descendant.id WHERE nh.hierarchyKey.ancestor.id = ?1 AND nh.depth > 0")
    List<Node> findAllAncestorsById(UUID id);

    Node findNodeById(UUID id);

    @Query("UPDATE Node n SET n.parentNode.id = ?1 WHERE n.id = ?2")
    Node updateNodeParent(UUID parentId,UUID nodeId);
}
