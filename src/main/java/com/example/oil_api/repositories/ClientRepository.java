package com.example.oil_api.repositories;

import com.example.oil_api.models.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    @Query("SELECT c FROM Client c WHERE CONCAT(c.nip, ' ', c.name, ' ', c.address) LIKE %:query%")
    Page<Client> findByCombinedFields(@Param("query") Pageable pageable, String query);
}
