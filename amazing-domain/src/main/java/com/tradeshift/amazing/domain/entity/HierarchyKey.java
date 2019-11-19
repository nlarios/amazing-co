package com.tradeshift.amazing.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class HierarchyKey implements Serializable {

    @JoinColumn(name="ancestor_id", referencedColumnName = "id")
    @ManyToOne
    private Node ancestor;

    @JoinColumn(name="descendant_id", referencedColumnName = "id")
    @ManyToOne
    private Node descendant;

    public HierarchyKey() {
    }

    public HierarchyKey(Node ancestor, Node descendant) {
        this.ancestor = ancestor;
        this.descendant = descendant;
    }

    public Node getAncestor() {
        return ancestor;
    }

    public void setAncestor(Node ancestor) {
        this.ancestor = ancestor;
    }

    public Node getDescendant() {
        return descendant;
    }

    public void setDescendant(Node descendant) {
        this.descendant = descendant;
    }
}
