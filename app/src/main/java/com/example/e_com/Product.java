package com.example.e_com;
public class Product {
    String name,img_url,description;
    long price;
  public Product(){

  }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Product(String name, String img_url, String description, long price) {
        this.name = name;
        this.img_url = img_url;
        this.description = description;
        this.price = price;
    }
}