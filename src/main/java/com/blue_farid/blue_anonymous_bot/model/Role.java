package com.blue_farid.blue_anonymous_bot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Role && ((Role) obj).getValue().equals(this.value);
    }

    public static Role getGodRole() {
        return new Role().setValue("ROLE_GOD");
    }

    public static Role getProRole() {
        return new Role().setValue("ROLE_PRO");
    }

    public static Role getReportRole() {
        return new Role().setValue("ROLE_REPORT");
    }
}
