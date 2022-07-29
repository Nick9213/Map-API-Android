package com.example.mapapi.models;

public class UserLocationDetails {
    private String id;
    private String name;
    private String email;
    private String number;
    private String latitude;
    private String longitude;

    public UserLocationDetails() {
    }

    public UserLocationDetails(String id, String name, String email, String number, String latitude, String longitude) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.number = number;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
