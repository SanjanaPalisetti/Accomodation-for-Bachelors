package com.example.Bachelors;

public class property {
    String ownername,location,propertyID;
    int avlrooms,cost;

    public property() {
    }

//    public property(String name, String location, String avl_rooms, String pt) {
//        this.Name = name;
//        this.Location = location;
//        this.Avl_rooms = avl_rooms;
//        this.Pt = pt;
//    }

    public String getOwnername()
    {
        return ownername;
    }

    public void setOwnername(String ownername)
    {
        this.ownername = ownername;
    }

    public String getpropertyID(){
        return propertyID;
    }

    public void setpropertyID(String propertyID){
        this.propertyID=propertyID;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public int getAvl_rooms()
    {
        return avlrooms;
    }

    public void setAvl_rooms(int avlrooms)
    {
        this.avlrooms = avlrooms;
    }

    public int getcost()
    {
        return cost;
    }

    public void setcost(int cost)
    {
        this.cost = cost;
    }

}
