package io.github.mrergos.repository;

import io.github.mrergos.entity.MemberNkso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


public interface MemberNksoRepository extends CrudRepository<MemberNkso, String> {
    Page<MemberNkso> findByLastnameIsLikeIgnoreCaseOrFirstnameIsLikeIgnoreCaseOrMiddlenameIsLikeIgnoreCaseOrRegistryNumIsLikeIgnoreCase( String lastname, String firstname, String middlename, String registryNum, Pageable pageable);

    Page<MemberNkso> findAll(Pageable pageable);
}
