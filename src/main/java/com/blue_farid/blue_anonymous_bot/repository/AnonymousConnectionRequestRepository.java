package com.blue_farid.blue_anonymous_bot.repository;

import com.blue_farid.blue_anonymous_bot.model.AnonymousConnectionRequest;
import com.blue_farid.blue_anonymous_bot.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnonymousConnectionRequestRepository extends JpaRepository<AnonymousConnectionRequest, Long> {
    List<AnonymousConnectionRequest> getAnonymousConnectionRequestByGenderOrderByCreationDateAsc(Gender gender);

    List<AnonymousConnectionRequest> getAnonymousConnectionRequestOrOrderByCreationDateAsc();
}
