package com.majorMedia.BackOfficeDashboard.repository;

import com.majorMedia.BackOfficeDashboard.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.businesses")
    Page<User> findAllWithBusinesses(Pageable pageable);


    Optional<User> findById(Long id);
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.businesses WHERE u.firstName LIKE %:searchKey% OR u.lastName LIKE %:searchKey%")
    Page<User> findAllByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseWithBusinesses(String searchKey, Pageable pageable);
    Page<User> findAllByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCase(String firstName , String lastName , Pageable paging);
}
