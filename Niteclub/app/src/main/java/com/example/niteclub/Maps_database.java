package com.example.niteclub;


import java.util.HashMap;

public class Maps_database {

    // Atributos
    private double Latitud;
    private double Longitud;
    private double AveragePrice;
    private double Rate;

    private HashMap<String, String> Address = new HashMap<>();
    private HashMap<String, String> Schudule = new HashMap<>();
    private HashMap<String, String> Menu = new HashMap<>();

    private String Cover;
    private String Name;
    private String Music;
    private String Type;
    private String Phone;

    private String UserName;
    private String Email;


    //Contructor
    public Maps_database() {
    }


    //SET AND GET


    public HashMap<String, String> getSchudule() {
        return Schudule;
    }

    public void setSchudule(HashMap<String, String> schudule) {
        Schudule = schudule;
    }

    public HashMap<String, String> getMenu() {
        return Menu;
    }

    public void setMenu(HashMap<String, String> menu) {
        Menu = menu;
    }

    public HashMap<String, String> getAddress() {
        return Address;
    }

    public void setAddress(HashMap<String, String> address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public double getRate() {
        return Rate;
    }

    public void setRate(double rate) {
        Rate = rate;
    }

    public String getCover() {
        return Cover;
    }

    public void setCover(String cover) {
        Cover = cover;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getLongitud() {
        return Longitud;
    }

    public void setLongitud(double longitud) {
        Longitud = longitud;
    }

    public double getAveragePrice() {
        return AveragePrice;
    }

    public void setAveragePrice(double averagePrice) {
        AveragePrice = averagePrice;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMusic() {
        return Music;
    }

    public void setMusic(String music) {
        Music = music;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public double getLatitud() {
        return Latitud;
    }

    public void setLatitud(double latitud) {
        Latitud = latitud;
    }
}
