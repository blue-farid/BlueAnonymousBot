package com.blue_farid.blue_anonymous_bot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AnonymousConnectionRequest {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client requestFrom;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @CreationTimestamp
    private Date creationDate;

}
