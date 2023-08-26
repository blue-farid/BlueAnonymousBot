package com.blue_farid.blue_anonymous_bot.repository;

import com.blue_farid.blue_anonymous_bot.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByUsername(String username);

    Client findByDeepLinkIgnoreCase(String deeplink);

    @Query("SELECT c FROM Client c WHERE c.createdAt > :startTime")
    List<Client> findAllNewJoiners(Date startTime);

    @Modifying
    @Transactional
    @Query("update Client c set c.firstname = :#{#client.firstname}, c.lastname = :#{#client.lastname}, " +
            "c.username = :#{#client.username} where c.id = :#{#client.id}")
    void updateFirstnameAndLastnameAndTelegramUserAndUsername(Client client);
}
