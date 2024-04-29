package com.minejava.portal.persistence.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity(name = "user_model")
public class UserEntity {

    @Id
    private Integer userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
