package com.minejava.portal.persistence.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Document(collection = "user_model")
public class UserEntity {

    @Id
    private Integer userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
