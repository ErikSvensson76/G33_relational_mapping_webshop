package se.lexicon.lecture_webshop.dto;

public class AddressWithoutCountry {

    private final String addressId;
    private final String street;
    private final String zipCode;
    private final String city;

    public AddressWithoutCountry(String addressId, String street, String zipCode, String city) {
        this.addressId = addressId;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
    }

    public String getAddressId() {
        return addressId;
    }

    public String getStreet() {
        return street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }
}
