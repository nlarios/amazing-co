package com.tradeshift.amazing.domain.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name="node_hierarchy")
public class Hierarchy implements Serializable {

   @EmbeddedId
   private HierarchyKey hierarchyKey;

    private Integer depth;

    public Hierarchy() {
    }

    public Hierarchy(HierarchyKey hierarchyKey, Integer depth) {
        this.hierarchyKey = hierarchyKey;
        this.depth = depth;
    }

    //    public Node getAncestor() {
//        return ancestor;
//    }
//
//    public void setAncestor(Node ancestor) {
//        this.ancestor = ancestor;
//    }
//
//    public Node getDescendant() {
//        return descendant;
//    }
//
//    public void setDescendant(Node descendant) {
//        this.descendant = descendant;
//    }


    public HierarchyKey getHierarchyKey() {
        return hierarchyKey;
    }

    public void setHierarchyKey(HierarchyKey hierarchyKey) {
        this.hierarchyKey = hierarchyKey;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }
}
