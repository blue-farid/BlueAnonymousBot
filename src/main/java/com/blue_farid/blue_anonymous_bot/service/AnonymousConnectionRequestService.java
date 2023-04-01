package com.blue_farid.blue_anonymous_bot.service;


import com.blue_farid.blue_anonymous_bot.model.AnonymousConnectionRequest;
import com.blue_farid.blue_anonymous_bot.model.Client;
import com.blue_farid.blue_anonymous_bot.model.Gender;
import com.blue_farid.blue_anonymous_bot.repository.AnonymousConnectionRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnonymousConnectionRequestService {
    private final AnonymousConnectionRequestRepository repository;

    public AnonymousConnectionRequest connect(Gender gender) {
        return repository.getAnonymousConnectionRequestByGenderOrderByCreationDateAsc(gender).get(0);
    }

    public AnonymousConnectionRequest connect() {
        return repository.getAnonymousConnectionRequestOrOrderByCreationDateAsc().get(0);
    }

    public AnonymousConnectionRequest submitRequest(Client from, Gender gender) {
        AnonymousConnectionRequest request = new AnonymousConnectionRequest();
        request.setRequestFrom(from);
        request.setGender(gender);
        return repository.save(request);
    }
}
