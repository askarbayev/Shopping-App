package com.example.askarbayev1.demo1;

public class Item {
    private int id;
    private String name;
    private String type;
    private double price;
    private int quantity;

    Item(int id, String name, String type, double price, int quantity){
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }

    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return id;
    }
    public void setPrice(double price){
        this.price=price;
    }
    public double getPrice(){
        return price;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }
    public void setQuantity(int quantity){
        this.quantity=quantity;
    }
    public int getQuantity(){
        return quantity;
    }
    public void setType(String type){
        this.type=type;
    }
    public String getType(){
        return type;
    }
    public String toString(){
        return getId()+" - "+getName()+" - "+getType()+" - "+getPrice();
    }


}
