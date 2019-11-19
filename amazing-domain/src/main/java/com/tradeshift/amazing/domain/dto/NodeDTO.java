package com.tradeshift.amazing.domain.dto;

import java.util.UUID;

public class NodeDTO {

    private UUID id;

    private UUID parentId;

    private UUID rootNodeId;

    private String name;

    private Integer height;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public UUID getRootNodeId() {
        return rootNodeId;
    }

    public void setRootNodeId(UUID rootNodeId) {
        this.rootNodeId = rootNodeId;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
