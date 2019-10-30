package pl.biegajski.shop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ordered_item")
@Getter
@Setter
@NoArgsConstructor
public class OrderedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Item item;

    @Column(nullable = false)
    private int count;
}
