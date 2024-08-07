package com.blue_farid.blue_anonymous_bot.repository;

import com.blue_farid.blue_anonymous_bot.model.Client;
import com.blue_farid.blue_anonymous_bot.model.Role;
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

    List<Client> findByRolesContains(Role role);

    @Query("SELECT c FROM Client c ORDER BY c.createdAt DESC")
    List<Client> findAllOrderByCreatedAtDesc();
}
