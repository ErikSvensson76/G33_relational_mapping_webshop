package se.lexicon.lecture_webshop.dto;

import se.lexicon.lecture_webshop.entity.Address;

import java.io.Serializable;

public class AddressDTO implements Serializable {

    private String addressId;
    private String street;
    private String zipCode;
    private String city;
    private String country;

    public AddressDTO(Address address){
        if(address != null){
            this.addressId = address.getAddressId();
            this.street = address.getStreet();
            this.zipCode = address.getZipCode();
            this.city = address.getCity();
            this.country = address.getCountry();
        }
    }

    public AddressDTO() {
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
