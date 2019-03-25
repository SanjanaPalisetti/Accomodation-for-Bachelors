package com.example.Bachelors;

public class UserReg {
    private String Name, Contanct, Email, address, Age, Sex , type;

    public UserReg() {

    }

    public UserReg(String name, String contanct, String email, String address, String age, String sex, String type ) {
        Name = name;
        Contanct = contanct;
        Email = email;
        address = address;
        Age = age;
        Sex = sex;
        this.type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContanct() {
        return Contanct;
    }

    public void setContanct(String contanct) {
        Contanct = contanct;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}