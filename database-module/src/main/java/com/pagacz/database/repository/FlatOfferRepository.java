package com.pagacz.database.repository;

import com.pagacz.database.model.FlatOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlatOfferRepository extends JpaRepository<FlatOffer, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM flat_offer o WHERE o.WRITE_TO_DOCS = ?1 LIMIT 20")
    List<FlatOffer> getOffersByWriteToDocsStatus(char status);

    @Query(nativeQuery = true, value = "SELECT * FROM flat_offer o WHERE o.SEND_BY_EMAIL = ?1 LIMIT 20")
    List<FlatOffer> getOffersBySendByEmailStatus(char status);
}