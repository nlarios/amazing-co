package com.tradeshift.amazing.persistence.repository;

import com.tradeshift.amazing.domain.entity.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
public interface NodeRepository extends CrudRepository<Node, Long> {

    @Query("SELECT n FROM Node n JOIN com.tradeshift.amazing.domain.entity.Hierarchy nh ON n.id = nh.hierarchyKey.descendant.id WHERE nh.hierarchyKey.ancestor.id = ?1 AND nh.depth > 0")
    List<Node> findAllDescendantsById(Long id);

    @Query("SELECT n FROM Node n JOIN com.tradeshift.amazing.domain.entity.Hierarchy nh ON n.id = nh.hierarchyKey.descendant.id WHERE nh.hierarchyKey.ancestor.id = ?1 AND nh.depth > 0")
    List<Node> findAllAncestorsById(Long id);

    Node findNodeById(Long id);

    @Modifying
    @Query("UPDATE Node n SET n.parentNode = ?1, n.height=?3 WHERE n.id = ?2")
    Integer updateNodeParent(Node parentNode,Long nodeId, int height);

    @Modifying
    @Query("UPDATE Node n SET n.height=?2 WHERE n.id = ?1")
    Integer updateNodeHeightById(Long nodeId, int height);
}
