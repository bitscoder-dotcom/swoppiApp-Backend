package com.bitscoder.swoppiapp.entities;

import com.bitscoder.swoppiapp.enums.UserTypes;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@SuperBuilder
@DynamicUpdate
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name"),
                @UniqueConstraint(columnNames = "email")
        })
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BaseUser implements Serializable {

    @Id
    private String userId;
    private String name;
    private String email;
    private String password;
    private UserTypes userType;


    public BaseUser() {
        this.userId = generateCustomUUID();
    }

    private String generateCustomUUID() {
        return "TLM"+ UUID.randomUUID().toString().substring(0, 5);
    }
}
