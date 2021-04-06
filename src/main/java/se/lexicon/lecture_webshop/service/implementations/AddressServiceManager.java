package se.lexicon.lecture_webshop.service.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.lecture_webshop.data.AddressRepository;
import se.lexicon.lecture_webshop.dto.AddressDTO;
import se.lexicon.lecture_webshop.entity.Address;
import se.lexicon.lecture_webshop.exception.AppResourceNotFoundException;
import se.lexicon.lecture_webshop.service.interfaces.AddressService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AddressServiceManager implements AddressService {

    public static final String COULD_NOT_FIND_ADDRESS_WITH_ID = "Could not find address with id ";
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AddressServiceManager(AddressRepository addressRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Collection<AddressDTO> findAll() {
        List<Address> addressList = (List<Address>) addressRepository.findAll();
        Collection<AddressDTO> addressDTOS = new ArrayList<>();
        for(Address address : addressList){
            addressDTOS.add(modelMapper.map(address, AddressDTO.class));
        }
        return addressDTOS;
    }

    @Override
    public AddressDTO findById(String id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AppResourceNotFoundException(COULD_NOT_FIND_ADDRESS_WITH_ID + id));
        return modelMapper.map(address, AddressDTO.class);
    }

    @Override
    @Transactional
    public AddressDTO create(AddressDTO form) {
        Address address = new Address(
                null,
                form.getStreet(),
                form.getZipCode(),
                form.getCity(),
                form.getCountry()
        );

        Address persisted = addressRepository.save(address);


        return modelMapper.map(persisted, AddressDTO.class);
    }

    @Override
    @Transactional
    public AddressDTO update(String id, AddressDTO form) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AppResourceNotFoundException(COULD_NOT_FIND_ADDRESS_WITH_ID + id));

        if(form.getCity() != null){
            address.setCity(form.getCity());
        }

        if(form.getCountry() != null){
            address.setCountry(form.getCountry());
        }

        if(form.getStreet() != null){
            address.setStreet(form.getStreet());
        }

        if(form.getZipCode() != null){
            address.setZipCode(form.getZipCode());
        }

        Address updated = addressRepository.save(address);

        return modelMapper.map(updated, AddressDTO.class);
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        addressRepository.deleteById(id);

        return !addressRepository.existsById(id);
    }
}
