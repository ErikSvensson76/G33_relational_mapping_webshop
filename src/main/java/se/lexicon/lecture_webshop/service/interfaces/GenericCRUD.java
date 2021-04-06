package se.lexicon.lecture_webshop.service.interfaces;

import java.util.Collection;

public interface GenericCRUD <ID, DTO>{
    Collection<DTO> findAll();
    DTO findById(ID id);
    DTO create(DTO form);
    DTO update(ID id, DTO form);
    boolean delete(ID id);
}
