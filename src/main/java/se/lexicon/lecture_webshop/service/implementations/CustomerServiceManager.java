package se.lexicon.lecture_webshop.service.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.lecture_webshop.data.AddressRepository;
import se.lexicon.lecture_webshop.data.CustomerRepository;
import se.lexicon.lecture_webshop.dto.AddressDTO;
import se.lexicon.lecture_webshop.dto.CustomerDTO;
import se.lexicon.lecture_webshop.dto.CustomerForm;
import se.lexicon.lecture_webshop.entity.Address;
import se.lexicon.lecture_webshop.entity.AppUser;
import se.lexicon.lecture_webshop.entity.Customer;
import se.lexicon.lecture_webshop.exception.AppResourceNotFoundException;
import se.lexicon.lecture_webshop.service.interfaces.CustomerService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static se.lexicon.lecture_webshop.service.implementations.AddressServiceManager.COULD_NOT_FIND_ADDRESS_WITH_ID;

@Service
public class CustomerServiceManager implements CustomerService {

    public static final String COULD_NOT_FIND_CUSTOMER_WITH = "Could not find customer with ";
    public static final String EMAIL = "email ";
    public static final String ID = "id ";
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final AddressRepository addressRepository;

    @Autowired
    public CustomerServiceManager(CustomerRepository customerRepository, ModelMapper modelMapper, AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.addressRepository = addressRepository;
    }

    public CustomerDTO toCustomerDTO(Customer customer){
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public Collection<CustomerDTO> toCustomerDTOs(List<Customer> customers){
        Collection<CustomerDTO> customerDTOS = new ArrayList<>();
        for(Customer customer : customers){
            customerDTOS.add(toCustomerDTO(customer));
        }
        return customerDTOS;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<CustomerDTO> searchByFullName(String fullName) {
        Collection<Customer> customers = customerRepository.findByFullNameContains(fullName.trim());
        return toCustomerDTOs((List<Customer>) customers);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDTO findByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new AppResourceNotFoundException(COULD_NOT_FIND_CUSTOMER_WITH + EMAIL +email));

        return toCustomerDTO(customer);
    }


    @Override
    @Transactional(readOnly = true)
    public Collection<CustomerDTO> findAll() {
        return toCustomerDTOs((List<Customer>) customerRepository.findAll());
    }


    @Override
    @Transactional(readOnly = true)
    public CustomerDTO findById(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new AppResourceNotFoundException(COULD_NOT_FIND_CUSTOMER_WITH + ID + id));
        return toCustomerDTO(customer);
    }


    @Override
    @Transactional
    public CustomerDTO create(CustomerForm form) {
        Customer customer = new Customer();
        customer.setFirstName(form.getFirstName().trim());
        customer.setLastName(form.getLastName().trim());
        customer.setEmail(form.getEmail().trim());

        AppUser appUser = new AppUser();
        appUser.setUsername(form.getUsername());
        appUser.setPassword(form.getPassword());
        appUser.setRole("customer");
        customer.setUserDetails(appUser);

        Address address = null;
        if(form.getAddress() != null){
            AddressDTO addressDTO = form.getAddress();
            Optional<Address> optional = addressRepository.findAddressBy(addressDTO.getStreet().trim(), addressDTO.getZipCode().trim(), addressDTO.getCity().trim(), addressDTO.getCountry().trim());
            if(optional.isPresent()){
                address = optional.get();
            }else{
                address = new Address();
                address.setStreet(addressDTO.getStreet().trim());
                address.setZipCode(addressDTO.getZipCode().trim());
                address.setCity(addressDTO.getCity().trim());
                address.setCountry(addressDTO.getCountry().trim());
            }
        }

        customer.setAddress(address);

        customer = customerRepository.save(customer);


        return toCustomerDTO(customer);
    }


    @Override
    @Transactional
    public CustomerDTO update(String id, CustomerDTO form) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new AppResourceNotFoundException(COULD_NOT_FIND_CUSTOMER_WITH+ID+id));

        if(form.getFirstName() != null){
            customer.setFirstName(form.getFirstName().trim());
        }
        if(form.getLastName() != null){
            customer.setLastName(form.getLastName().trim());
        }
        if(form.getEmail() != null){
            customer.setEmail(form.getEmail().trim());
        }

        customer = customerRepository.save(customer);

        return toCustomerDTO(customer);
    }

    @Override
    @Transactional
    public CustomerDTO changeAddress(String customerId, AddressDTO newAddress){
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new AppResourceNotFoundException(COULD_NOT_FIND_CUSTOMER_WITH+ID+customerId));

        Address address;
        if(newAddress.getAddressId() != null){
            address = addressRepository.findById(newAddress.getAddressId()).orElseThrow(() -> new AppResourceNotFoundException(COULD_NOT_FIND_ADDRESS_WITH_ID+newAddress.getAddressId()));
        }else {
            Optional<Address> optional = addressRepository.findAddressBy(newAddress.getStreet().trim(), newAddress.getZipCode().trim(), newAddress.getCity().trim(), newAddress.getCountry().trim());
            address = optional.orElseGet(() -> new Address(
                    null,
                    newAddress.getStreet().trim(),
                    newAddress.getZipCode().trim(),
                    newAddress.getCity().trim(),
                    newAddress.getCountry().trim()
            ));
        }
        customer.setAddress(address);

        customer = customerRepository.save(customer);

        return toCustomerDTO(customer);
    }


    @Override
    @Transactional
    public boolean delete(String s) {
        return false;
    }
}
