package com.example.kenny.selector;

public class Item {
    private String imageName;
    private String name;
    private int imageID;

    public Item(String imageName, String name) {
        this.imageName = imageName;
        this.name = name;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getImageID() {
        return imageID;
    }

    public String getImageName() {
        return imageName;
    }

    public String getName() {
        return name;
    }
}
