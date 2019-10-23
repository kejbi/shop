package pl.biegajski.shop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;

    @Column(nullable = false)
    private String role;

    @ManyToMany(mappedBy = "roles")
    private List<AppUser> users;
}
