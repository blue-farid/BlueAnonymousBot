package com.blue_farid.blue_anonymous_bot.repository;

import com.blue_farid.blue_anonymous_bot.model.AnonymousConnectionRequest;
import com.blue_farid.blue_anonymous_bot.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AnonymousConnectionRequestRepository extends JpaRepository<AnonymousConnectionRequest, Long> {

    @Query(value = "select a from AnonymousConnectionRequest a where a.requestFrom.gender = :selectedGender and a.requestFrom.id != :clientId and a.gender = :gender order by a.creationDate")
    List<AnonymousConnectionRequest> getAnonymousConnectionRequestByRequestFromGenderOrderByCreationDate(Long clientId, Gender selectedGender, Gender gender);

    @Query(value = "select a from AnonymousConnectionRequest a where a.requestFrom.id != :clientId order by a.creationDate")
    List<AnonymousConnectionRequest> getAllOrderByCreationDate(Long clientId);

    @Modifying
    @Transactional
    @Query(value = "delete from AnonymousConnectionRequest a where a.requestFrom.id = :clientId")
    void deleteByRequestFromId(Long clientId);
}
