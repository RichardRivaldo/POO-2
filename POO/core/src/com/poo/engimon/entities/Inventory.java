package com.poo.engimon.entities;

import java.util.*;

public class Inventory<T> {
    protected ArrayList<T> array_inventory;

    // Constructor
    public Inventory() {
        this.array_inventory = new ArrayList<>();
    }

    // Add item
    public void add(T inventory_item) {
        this.array_inventory.add(inventory_item);
    }

    // Remove Item
    public void remove(int index) throws IndexOutOfBoundException {
        if (index - 1 > this.array_inventory.size())
            throw new IndexOutOfBoundException();
        this.array_inventory.remove(index);
    }

    // Set Item
    public void set(int index, T element) throws IndexOutOfBoundException {
        if (index - 1 > this.array_inventory.size())
            throw new IndexOutOfBoundException();
        this.array_inventory.set(index, element);
    }

    // Size
    public int size() {
        return this.array_inventory.size();
    }

    // Inventory Item List
    public ArrayList<T> getItemList(){
        return this.array_inventory;
    }

    // Check empty
    public Boolean isEmptyInvent(){
        return this.size() == 0;
    }
}