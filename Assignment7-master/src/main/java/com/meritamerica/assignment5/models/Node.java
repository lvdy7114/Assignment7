package com.meritamerica.assignment5.models;

public class Node<E> {
    private E info;
    private Node<E> next;

    public Node() {
        info = null;
        next = null;
    }

    public Node(E info) {
        setInfo(info);
    }

    public Node(E info, Node<E> next) {
        setInfo(info);
        setNext(next);
    }

    public E getInfo() {
        return info;
    }

    public void setInfo(E info) {
        this.info = info;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }
}