package se.lexicon.lecture_webshop.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import se.lexicon.lecture_webshop.dto.AddressWithoutCountry;
import se.lexicon.lecture_webshop.entity.Address;

import java.util.Collection;

public interface AddressRepository extends CrudRepository<Address, String> {

    String ADDRESS_WITHOUT_COUNTRY = "se.lexicon.lecture_webshop.dto.AddressWithoutCountry(a.addressId, a.street, a.zipCode, a.city)";

    @Query("SELECT address FROM Address address WHERE UPPER(address.country) = UPPER(:country) ")
    Collection<Address> findByCountry(@Param("country") String country);

    @Query("SELECT address FROM Address address WHERE UPPER(address.city) = UPPER(:city)")
    Collection<Address> findByCity(@Param("city") String city);

    @Query("SELECT new "+ADDRESS_WITHOUT_COUNTRY+" FROM Address a")
    Collection<AddressWithoutCountry> findAllAddresses();


}
