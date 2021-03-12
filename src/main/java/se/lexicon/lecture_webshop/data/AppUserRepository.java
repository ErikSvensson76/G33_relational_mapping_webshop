package se.lexicon.lecture_webshop.data;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.lecture_webshop.entity.AppUser;

public interface AppUserRepository extends CrudRepository<AppUser, String> {
}
