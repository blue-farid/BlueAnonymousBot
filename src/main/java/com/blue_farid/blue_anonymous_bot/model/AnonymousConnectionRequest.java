package com.blue_farid.blue_anonymous_bot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "ANONYMOUS_CONNECTION_REQUEST")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AnonymousConnectionRequest {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client requestFrom;

    private Gender gender;

    @CreationTimestamp
    private Date creationDate;

}
