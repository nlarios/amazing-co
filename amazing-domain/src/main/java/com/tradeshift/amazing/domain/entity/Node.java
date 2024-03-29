package com.tradeshift.amazing.domain.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="node")
public class Node {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Integer height;

    @Column
    private String name;

    @JoinColumn(name="parent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Node parentNode;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="root_id")
    private Node rootNode;

    public Node() {}

//    public UUID getId() {
//        return id;
//    }
//
//    public void setId(UUID id) {
//        this.id = id;
//    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Node getRootNode() {
        return rootNode;
    }

    public void setRootNode(Node rootNode) {
        this.rootNode = rootNode;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
