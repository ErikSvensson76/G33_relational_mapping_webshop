package se.lexicon.lecture_webshop.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import se.lexicon.lecture_webshop.entity.AppUser;

import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser, String> {
    @Query("SELECT appUser FROM AppUser appUser WHERE appUser.username = :username")
    Optional<AppUser> findByUserName(@Param("username") String username);
}
