package se.lexicon.lecture_webshop.data;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.lecture_webshop.entity.Address;

public interface AddressRepository extends CrudRepository<Address, String> {
}
