package com.blue_farid.blue_anonymous_bot.service;


import com.blue_farid.blue_anonymous_bot.model.AnonymousConnectionRequest;
import com.blue_farid.blue_anonymous_bot.model.Client;
import com.blue_farid.blue_anonymous_bot.model.Gender;
import com.blue_farid.blue_anonymous_bot.repository.AnonymousConnectionRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnonymousConnectionRequestService {
    private final AnonymousConnectionRequestRepository repository;

    public AnonymousConnectionRequest connect(Long clientId, Gender gender) {
        List<AnonymousConnectionRequest> list =  repository.getAnonymousConnectionRequestByRequestFromGenderOrderByCreationDate(clientId, gender);
        return list.isEmpty() ? null : list.get(0);
    }

    public AnonymousConnectionRequest connect(Client client) {
        List<AnonymousConnectionRequest> list = repository.getAllOrderByCreationDate(client.getId());
        // sort ascending
        return list.isEmpty() ? null : list.stream().filter(a ->
                a.getGender().equals(Gender.BI) || a.getGender().equals(client.getGender()))
                .findFirst().orElse(null);
    }

    public AnonymousConnectionRequest submitRequest(Client from, Gender gender) {
        AnonymousConnectionRequest request = new AnonymousConnectionRequest();
        request.setRequestFrom(from);
        request.setGender(gender);
        return repository.save(request);
    }

    public void deleteByRequestFromId(Long clientId) {
        repository.deleteByRequestFromId(clientId);
    }
}
