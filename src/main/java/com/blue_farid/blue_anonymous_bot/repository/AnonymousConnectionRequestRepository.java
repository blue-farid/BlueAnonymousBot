package com.blue_farid.blue_anonymous_bot.repository;

import com.blue_farid.blue_anonymous_bot.model.AnonymousConnectionRequest;
import com.blue_farid.blue_anonymous_bot.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnonymousConnectionRequestRepository extends JpaRepository<AnonymousConnectionRequest, Long> {
    List<AnonymousConnectionRequest> getAnonymousConnectionRequestByGenderOrderByCreationDate(Gender gender);

    @Query(value = "select a from AnonymousConnectionRequest a order by a.creationDate")
    List<AnonymousConnectionRequest> getAllOrderByCreationDate();
}
