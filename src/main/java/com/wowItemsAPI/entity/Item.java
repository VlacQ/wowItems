package com.wowItemsAPI.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;

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

    @Column(name = "median", columnDefinition = "Decimal(7,4)")
    private BigDecimal median = BigDecimal.ZERO;

    @Column(name = "average", columnDefinition = "Decimal(7,4)")
    private BigDecimal average = BigDecimal.ZERO;

    @Column(name = "standard_deviation", columnDefinition = "Decimal(7,4)")
    private BigDecimal standardDeviation = BigDecimal.ZERO;

    @Column(name = "max", columnDefinition = "Decimal(7,4)")
    private BigDecimal max = BigDecimal.ZERO;

    public void addPrice(Price price){
        if (this.priceList == null)
            this.priceList = new LinkedList<>();

        addSortedPrice(price);
        countMedian();
        countAverage();
        countStandardDeviation();
        countSingleMax(price);
    }

    private void addSortedPrice(Price price){
        if (this.priceList.size() == 0)
            this.priceList.add(price);
        else if (price.getDate().compareTo(this.priceList.get(0).getDate()) < 0)
            this.priceList.add(0, price);
        else if (price.getDate().compareTo(this.priceList.get(this.priceList.size()-1).getDate()) > 0)
            this.priceList.add(this.priceList.size(), price);
        else {
            for (int i = 0; i < this.priceList.size() - 1; i++) {
                if (price.getDate().compareTo(this.priceList.get(i).getDate()) <= 0){
                    this.priceList.add(i, price);
                    break;
                }
            }
        }
    }

    public void removePrice(Price price){
        this.priceList.remove(price);
        countMedian();
        countAverage();
        countStandardDeviation();
        countMax();
    }

    public void countValues(){
        countMedian();
        countAverage();
        countStandardDeviation();
        countMax();
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
        if (this.priceList.size() > 0){
            sum = sum.divide(BigDecimal.valueOf(array.length), BigDecimal.ROUND_HALF_EVEN).setScale(4, BigDecimal.ROUND_HALF_EVEN);
            this.setStandardDeviation(BigDecimalSqrt(sum, 4));
        } else {
            this.setStandardDeviation(BigDecimal.ZERO);
        }
    }


    private void countMax(){
        Iterator iterator = this.getPriceList().iterator();
        while (iterator.hasNext()){
            Price price = (Price) iterator.next();
            if (max.compareTo(price.getAmount()) < 0){
                this.setMax(price.getAmount());
            }
        }
    }


    private void countSingleMax(Price price){
        if (max.compareTo(price.getAmount()) < 0){
            this.setMax(price.getAmount());
        }
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

    private static BigDecimal BigDecimalSqrt(BigDecimal A, final int SCALE) {
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

