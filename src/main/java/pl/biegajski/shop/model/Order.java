package pl.biegajski.shop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;

    @Column
    private boolean finished;

    @Column
    private float price;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderedItem> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    public void addToOrder(OrderedItem item) {
        items.add(item);
    }
}
