package com.tradeshift.amazing.model.entity;


import javax.persistence.*;

@Entity
public class Node {

    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private int height;

    @ManyToOne
    @JoinColumn(name="parent_id")
    private Node parentNode;

    @ManyToOne
    @JoinColumn(name="root_id")
    private Node rootNode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public Node getRootNode() {
        return rootNode;
    }

    public void setRootNode(Node rootNode) {
        this.rootNode = rootNode;
    }
}
