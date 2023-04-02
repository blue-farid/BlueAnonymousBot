package com.blue_farid.blue_anonymous_bot.repository;

import com.blue_farid.blue_anonymous_bot.model.AnonymousConnectionRequest;
import com.blue_farid.blue_anonymous_bot.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AnonymousConnectionRequestRepository extends JpaRepository<AnonymousConnectionRequest, Long> {

    @Query(value = "select a from AnonymousConnectionRequest a where a.requestFrom.gender = :gender order by a.creationDate")
    List<AnonymousConnectionRequest> getAnonymousConnectionRequestByRequestFromGenderOrderByCreationDate(Gender gender);

    @Query(value = "select a from AnonymousConnectionRequest a order by a.creationDate")
    List<AnonymousConnectionRequest> getAllOrderByCreationDate();

    @Modifying
    @Transactional
    @Query(value = "delete from AnonymousConnectionRequest a where a.requestFrom.id = :clientId")
    void deleteByRequestFromId(Long clientId);
}
