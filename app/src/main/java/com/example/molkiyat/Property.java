package com.example.molkiyat;

import android.net.Uri;

import java.lang.ref.SoftReference;

public class Property extends HomeId {

    private  String district;
    private  String locationArea;
    private  String street;
    private  String address;
    private  String homeNumber;
    private  String city;
    private Uri imageUrl;
    private String floor;
    private String area;
    private String bathrooms;
    private String rooms;
    private String title;
    private String image;
    private String price;
    private String propertyType;

    public Property() {
    }

    public Property(String bathrooms, String rooms, String title, String image, String price, String purpose) {
        this.bathrooms = bathrooms;
        this.rooms = rooms;
        this.title = title;
        this.image = image;
        this.price = price;
        this.purpose = purpose;

    }

    public Property(String purpose, String propertyType, String propertyName, String description, String price, String area, String rooms, String bathrooms, String floor, Uri mImageUri) {
        this.bathrooms = bathrooms;
        this.rooms = rooms;
        this.title = propertyName;
        this.imageUrl = mImageUri;
        this.price = price;
        this.purpose = purpose;
        this.propertyType = propertyType;
        this.description = description;
        this.area = area;
        this.floor = floor;
    }

    public Property(String city, String district, String locationArea, String address, String street, String homeNumber, Property mProperty) {
        this.district=district;
        this.locationArea=locationArea;
        this.street=street;
        this.address=address;
        this.homeNumber=homeNumber;
        this.city=city;
        this.bathrooms =mProperty.getBathrooms();
        this.rooms = mProperty.getRooms();
        this.title = mProperty.getTitle();
        this.imageUrl = mProperty.getImageUrl();
        this.price = mProperty.getPrice();
        this.purpose = mProperty.getPurpose();
        this.propertyType = mProperty.getPropertyType();
        this.description = mProperty.getDescription();
        this.area = mProperty.getArea();
        this.floor = mProperty.getFloor();

    }
    public Uri getImageUrl() {
        return imageUrl;
    }
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLocationArea() {
        return locationArea;
    }

    public void setLocationArea(String locationArea) {
        this.locationArea = locationArea;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setImageUrl(Uri imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String purpose;
    private String description;


    public String getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(String bathrooms) {
        this.bathrooms = bathrooms;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }


    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getPurpose() {
        return purpose;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
