package com.tradeshift.amazing.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
public class TreeHierarchy {

    @Id
    @ManyToMany(mappedBy = "id")
    private UUID ancestorId;

    @Id
    @ManyToMany(mappedBy = "id")
    private UUID descendantId;

    private Integer depth;

    public UUID getAncestorId() {
        return ancestorId;
    }

    public void setAncestorId(UUID ancestorId) {
        this.ancestorId = ancestorId;
    }

    public UUID getDescendantId() {
        return descendantId;
    }

    public void setDescendantId(UUID descendantId) {
        this.descendantId = descendantId;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }
}
