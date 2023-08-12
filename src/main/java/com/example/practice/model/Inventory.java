package com.example.practice.model;

public class Inventory {

    private int weight;
    private String name;
    private int count;
    private int price;
    private boolean isFood;

    public Inventory(String name, int weight, int count, int price, boolean isFood) {
        this.weight = weight;
        this.name = name;
        this.count = count;
        this.price = price;
        this.isFood = isFood;
    }

    public int getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public int getPrice() {
        return price;
    }

    public boolean isFood() {
        return isFood;
    }

    public void soldout () {
        this.count = 0;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "weight=" + weight +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", price=" + price +
                ", isFood=" + isFood +
                '}';
    }
}
