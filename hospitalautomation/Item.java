/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitalautomation;

/**
 * The Item class represents a key-value pair.
 * This is typically used in dropdown menus or data mapping scenarios,
 * where the `key` is a unique identifier, and the `value` is the human-readable text.
 */
public class Item {
    private int key;  // The unique identifier
    private String value;  // The human-readable value

    
    public Item(int key, String value){
        super();
        this.key =key;
        this.value = value;
    }

    
    
    public int getKey(){
        return key;
    }
    public void setKey(int key){
        this.key = key;
    }
    public String getValue(){
        return value;
    }
    public void setValue(String value){
        this.value = value;
    }
    
    
    /**
     * Overrides the `toString` method to return the value of the item.
     * This ensures that when the Item object is displayed in a dropdown menu
     * (or similar UI component), the value (rather than the key) is shown.
     *
     * @return The value of the item.
     */
    @Override
    public String toString(){
        return this.value;
    }
}
