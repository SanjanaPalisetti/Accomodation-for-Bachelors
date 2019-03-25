package com.example.Bachelors;

public class property {
    String Name,Location,Avl_rooms,Pt;

    public property() {
    }

    public property(String name, String location, String avl_rooms, String pt) {
        this.Name = name;
        this.Location = location;
        this.Avl_rooms = avl_rooms;
        this.Pt = pt;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        this.Location = location;
    }

    public String getAvl_rooms() {
        return Avl_rooms;
    }

    public void setAvl_rooms(String avl_rooms) {
        this.Avl_rooms = avl_rooms;
    }

    public String getPt() {
        return Pt;
    }

    public void setPt(String pt) {
        this.Pt = pt;
    }

}

