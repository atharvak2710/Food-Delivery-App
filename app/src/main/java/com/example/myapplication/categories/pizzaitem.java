package com.example.myapplication.categories;

public class pizzaitem
{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private int imageResource;

    public pizzaitem(String name, int imageResource) {
        this.name = name;
        this.imageResource = imageResource;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
