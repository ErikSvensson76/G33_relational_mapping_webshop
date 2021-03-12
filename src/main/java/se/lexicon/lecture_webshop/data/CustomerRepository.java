package se.lexicon.lecture_webshop.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import se.lexicon.lecture_webshop.entity.Customer;

import java.util.Collection;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, String> {

    @Query("SELECT c FROM Customer c WHERE UPPER(c.email) = UPPER(:email)")
    Optional<Customer> findByEmail(@Param("email") String email);

    @Query("SELECT c FROM Customer c WHERE UPPER(CONCAT(c.firstName,' ',c.lastName)) LIKE UPPER(CONCAT('%', :query, '%'))")
    Collection<Customer> findByFullNameContains(@Param("query") String query);


}
