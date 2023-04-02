package com.blue_farid.blue_anonymous_bot.service;


import com.blue_farid.blue_anonymous_bot.model.AnonymousConnectionRequest;
import com.blue_farid.blue_anonymous_bot.model.Client;
import com.blue_farid.blue_anonymous_bot.model.Gender;
import com.blue_farid.blue_anonymous_bot.repository.AnonymousConnectionRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AnonymousConnectionRequestService {
    private final AnonymousConnectionRequestRepository repository;

    public AnonymousConnectionRequest connect(Gender gender) {
        List<AnonymousConnectionRequest> list =  repository.getAnonymousConnectionRequestByGenderOrderByCreationDate(gender);
        return list.isEmpty() ? null : list.get(0);
    }

    public AnonymousConnectionRequest connect() {
        List<AnonymousConnectionRequest> list = repository.getAllOrderByCreationDate();
        return list.isEmpty() ? null : list.get(0);
    }

    public AnonymousConnectionRequest submitRequest(Client from, Gender gender) {
        AnonymousConnectionRequest request = new AnonymousConnectionRequest();
        request.setRequestFrom(from);
        request.setGender(gender);
        return repository.save(request);
    }
}
