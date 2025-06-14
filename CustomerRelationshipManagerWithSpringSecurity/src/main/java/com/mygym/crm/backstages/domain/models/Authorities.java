package com.mygym.crm.backstages.domain.models;

import com.mygym.crm.backstages.domain.models.common.User;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "authorities_table")
@EqualsAndHashCode(exclude = {"user"})
@ToString(exclude = {"user"})
public class Authorities {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "authorities_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, name = "authority")
    private String authority;

}
