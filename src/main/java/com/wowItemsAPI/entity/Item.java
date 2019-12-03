package com.wowItemsAPI.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "item")
public class Item {
    private final static BigDecimal TWO = new BigDecimal("2");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = true)
    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Price> priceList;

    @Column(name = "median")
    private BigDecimal median = BigDecimal.ZERO;

    @Column(name = "average")
    private BigDecimal average = BigDecimal.ZERO;

    @Column(name = "standard_deviation")
    private BigDecimal standardDeviation = BigDecimal.ZERO;

    public void addPrice(Price price){
        if (this.priceList == null)
            this.priceList = new ArrayList<>();

        this.priceList.add(price);
        countMedian();
        countAverage();
        countStandardDeviation();
    }

    public void removePrice(Price price){
        this.priceList.remove(price);
        countMedian();
        countAverage();
        countStandardDeviation();
    }


    private void countMedian(){
        BigDecimal array[] = sortArray();
        int mid = array.length / 2;

        if (array.length != 0){
            if (array.length % 2 == 0){
                this.setMedian(array[mid].add(array[mid-1]).divide(new BigDecimal("2"), BigDecimal.ROUND_HALF_EVEN));
            } else {
                this.setMedian(array[mid]);
            }
        } else {
            this.setMedian(BigDecimal.ZERO);
        }
    }


    private void countAverage(){
        BigDecimal array[] = sortArray();
        BigDecimal sum = BigDecimal.ZERO;

        for (BigDecimal element : array) {
            sum = sum.add(element);
        }

        if (array.length != 0){
            this.setAverage(sum.divide(new BigDecimal(array.length), BigDecimal.ROUND_HALF_EVEN));
        } else {
            this.setAverage(BigDecimal.ZERO);
        }
    }


    private void countStandardDeviation(){
        BigDecimal array[] = sortArray();
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal average = this.getAverage();

        for (BigDecimal element : array) {
            sum = sum.add(element.subtract(average).pow(2));
        }

        sum = sum.divide(BigDecimal.valueOf(array.length));

        this.setStandardDeviation(BigDecimalSqrt(sum, 4));
    }

    private BigDecimal[] sortArray(){
        Price arrayPrice[] = priceList.toArray(new Price[0]);
        BigDecimal array[] = new BigDecimal[arrayPrice.length];

        for (int i = 0; i < array.length; i++) {
            array[i] = arrayPrice[i].getAmount();
        }

        Arrays.sort(array);

        return array;
    }

    public static BigDecimal BigDecimalSqrt(BigDecimal A, final int SCALE) {
        BigDecimal x0 = new BigDecimal("0");
        BigDecimal x1 = new BigDecimal(Math.sqrt(A.doubleValue()));
        while (!x0.equals(x1)) {
            x0 = x1;
            x1 = A.divide(x0, SCALE, BigDecimal.ROUND_HALF_UP);
            x1 = x1.add(x0);
            x1 = x1.divide(TWO, SCALE, BigDecimal.ROUND_HALF_UP);

        }
        return x1;
    }
}

