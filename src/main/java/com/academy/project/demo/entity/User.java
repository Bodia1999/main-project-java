package com.academy.project.demo.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "email"
        })
})
@Getter
@Setter
@NoArgsConstructor
//@ToString
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank
    @Size(max = 40)
    private String name;

    @NotBlank
    @Size(max = 100)
    private String surname;

    private String phoneNumber;

//    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(min = 4, max = 100)
    private String password;

    private String stripeCustomerId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<CreditCard> creditCard;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    public User(@NotBlank @Size(max = 40) String name,
                @NotBlank @Size(min = 4, max = 100) String surname,
                @NotBlank @Size(max = 40) @Email String email,
                @NotBlank @Size(max = 100) String password ,
                String phoneNumber,
                String stripeCustomerId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.stripeCustomerId = stripeCustomerId;
    }


}
