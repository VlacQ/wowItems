package com.wowItemsAPI.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "price")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    @Column(name = "amount")
    private int amount;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "date")
    private Date date;
}
