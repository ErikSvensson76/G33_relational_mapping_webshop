package se.lexicon.lecture_webshop.service.interfaces;

import org.springframework.transaction.annotation.Transactional;
import se.lexicon.lecture_webshop.dto.AddressDTO;
import se.lexicon.lecture_webshop.dto.CustomerDTO;
import se.lexicon.lecture_webshop.dto.CustomerForm;

import java.util.Collection;

public interface CustomerService{
    @Transactional(readOnly = true)
    Collection<CustomerDTO> searchByFullName(String fullName);

    @Transactional(readOnly = true)
    CustomerDTO findByEmail(String email);

    @Transactional(readOnly = true)
    Collection<CustomerDTO> findAll();

    @Transactional(readOnly = true)
    CustomerDTO findById(String id);

    @Transactional
    CustomerDTO create(CustomerForm form);

    @Transactional
    CustomerDTO update(String id, CustomerDTO form);

    @Transactional
    CustomerDTO changeAddress(String customerId, AddressDTO newAddress);

    @Transactional
    boolean delete(String s);
}
