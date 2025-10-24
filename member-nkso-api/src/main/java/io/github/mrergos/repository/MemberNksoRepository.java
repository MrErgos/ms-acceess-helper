package io.github.mrergos.repository;

import io.github.mrergos.entity.MemberNkso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface MemberNksoRepository extends CrudRepository<MemberNkso, String> {
//old version    Page<MemberNkso> findByLastnameIsLikeIgnoreCaseOrFirstnameIsLikeIgnoreCaseOrMiddlenameIsLikeIgnoreCaseOrRegistryNumIsLikeIgnoreCase( String lastname, String firstname, String middlename, String registryNum, Pageable pageable);

    @Query("""
            select m from MemberNkso m where
            (m.lastname LIKE :word1 OR m.firstname LIKE :word1 OR m.middlename LIKE :word1 OR m.registryNum LIKE :word1)
            and (m.lastname LIKE :word2 OR m.firstname LIKE :word2 OR m.middlename LIKE :word2 OR m.registryNum LIKE :word2)
            and (m.lastname LIKE :word3 OR m.firstname LIKE :word3 OR m.middlename LIKE :word3 OR m.registryNum LIKE :word3)
            and (m.lastname LIKE :word4 OR m.firstname LIKE :word4 OR m.middlename LIKE :word4 OR m.registryNum LIKE :word4)
            """)
    Page<MemberNkso> findMembersByFilters(String word1, String word2, String word3, String word4, Pageable pageable);

    @Query("""
            select m from MemberNkso m where
            (m.lastname LIKE :word1 OR m.firstname LIKE :word1 OR m.middlename LIKE :word1 OR m.registryNum LIKE :word1)
            and (m.lastname LIKE :word2 OR m.firstname LIKE :word2 OR m.middlename LIKE :word2 OR m.registryNum LIKE :word2)
            and (m.lastname LIKE :word3 OR m.firstname LIKE :word3 OR m.middlename LIKE :word3 OR m.registryNum LIKE :word3)
            """)
    Page<MemberNkso> findMembersByFilters(String word1, String word2, String word3, Pageable pageable);

    @Query("""
            select m from MemberNkso m where
            (m.lastname LIKE :word1 OR m.firstname LIKE :word1 OR m.middlename LIKE :word1 OR m.registryNum LIKE :word1)
            and (m.lastname LIKE :word2 OR m.firstname LIKE :word2 OR m.middlename LIKE :word2 OR m.registryNum LIKE :word2)
            """)
    Page<MemberNkso> findMembersByFilters(String word1, String word2, Pageable pageable);

    @Query("""
            select m from MemberNkso m where
            (m.lastname LIKE :word1 OR m.firstname LIKE :word1 OR m.middlename LIKE :word1 OR m.registryNum LIKE :word1)
            """)
    Page<MemberNkso> findMembersByFilters(String word1, Pageable pageable);

    Page<MemberNkso> findAll(Pageable pageable);
}
