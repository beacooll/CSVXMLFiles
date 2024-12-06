package org.example;

public record Address(String city, String street, Integer house, Integer floor) {

    @Override
    public String toString() {
        return
                 city  + ", " +
                 street + ", " +
                 house + ", " +
                 floor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address)) return false;
        return floor.equals(address.floor) &&
                city.equals(address.city) &&
                street.equals(address.street) &&
                house.equals(address.house);
    }

    public Integer getFloor() {
        return floor;
    }

    public String getCity() {
        return city;
    }
}