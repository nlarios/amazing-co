package com.tradeshift.amazing.persistence.repository;

import com.tradeshift.amazing.domain.entity.Node;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface NodeRepository extends CrudRepository<Node, UUID> {


    @Query("")
    Collection<Node> findLeafNodes();


    List<Node> findPathForNode(Node node);

    @Query("SELECT child FROM Node node, Node child " +
            "WHERE (child.left BETWEEN node.left AND node.right) " +
            "AND (child.right BETWEEN node.left AND node.right) " +
            "AND child <> ?1 " +
            "AND node = ?1 ORDER BY child.left")
    List<Node> findChildNodesForNode(Node node);


    @Query("SELECT count(parent) FROM Node node, Node parent " +
            "WHERE node.left BETWEEN parent.left AND parent.right " +
            "AND parent <> ?1 AND node = ?1")
    int findNodeDepth(Node node);
}
