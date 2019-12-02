package com.wowItemsAPI.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "price")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 1, message = "Quantity cannot be below 0")
    @Column(name = "quantity")
    private Integer quantity;

    @NotNull
    @NumberFormat(pattern = "0.0000")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount have to be above 0")
    @Column(name = "amount", precision = 5, scale = 4, columnDefinition = "Decimal(7,2)")
    private BigDecimal amount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date")
    private Date date;
}
