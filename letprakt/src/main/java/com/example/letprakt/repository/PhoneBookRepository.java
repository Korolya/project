package com.example.letprakt.repository;

import com.example.letprakt.model.PhoneBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneBookRepository extends JpaRepository<PhoneBook, Long> {
    Optional<PhoneBook> findByPhoneNumber(String phoneNumber);
}
