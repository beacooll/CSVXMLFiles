package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CSVFile {
    private List adderessesBook;

    public CSVFile(){
        adderessesBook = new ArrayList<>();
    }

    private void readCSV(){
        Path path = Paths.get("src", "address.csv");

        try(BufferedReader csv = new BufferedReader(new FileReader(path.toString()))){
            String address = csv.readLine();
            String[] arr = address.split("\"");
            Address newAddress = new Address(arr[0], arr[2], arr[4]);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}

class Address{
    private String city;
    private String street;
    private int house;
    private int floor;
    public Address(String city, String street, int house, int floor){
        this.city = city;
        this.street = street;
        this.house = house;
        this.floor = floor;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getHouse() {
        return house;
    }

    public int getFloor() {
        return floor;
    }
}