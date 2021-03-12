package se.lexicon.lecture_webshop.data;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.lecture_webshop.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, String> {
}
