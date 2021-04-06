package se.lexicon.lecture_webshop.dto;

public class CustomerDTO {

    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private AddressDTO address;
    private AppUserDTO userDetails;

    public CustomerDTO() {
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public AppUserDTO getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(AppUserDTO userDetails) {
        this.userDetails = userDetails;
    }
}
