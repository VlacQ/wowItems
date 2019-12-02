package com.wowItemsAPI.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.validation.constraints.Min;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PriceItem extends Price {
    @Min(1)
    private int item;
}
