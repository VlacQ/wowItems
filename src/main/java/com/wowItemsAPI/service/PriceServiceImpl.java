package com.wowItemsAPI.service;

import com.wowItemsAPI.entity.Price;
import com.wowItemsAPI.util.DateValidation;
import com.wowItemsAPI.repository.PriceRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class PriceServiceImpl implements PriceService {

    private PriceRepository priceRepository;

    private DateValidation dateValidation;

    @Autowired
    public PriceServiceImpl(PriceRepository priceRepository, DateValidation dateValidation){
        this.priceRepository = priceRepository;
        this.dateValidation = dateValidation;
    }


    @Override
    public List<Price> findAll() {
        List<Price> priceList = priceRepository.findAll();
        if (priceList.isEmpty()){
            priceList = new ArrayList<>();
            Price price = new Price();
            price.setId(0L);
            price.setQuantity(0);
            price.setDate(new Date());
            price.setAmount(BigDecimal.ZERO);
            priceList.add(price);
        }
        return priceList;
    }

    @Override
    public Price findById(Long id) {
        Optional<Price> result = priceRepository.findById(id);

        Price price = null;

        price = result.orElseGet(Price::new);

        return price;
    }

    @Override
    public boolean save(Price price) {
        if (price.getDate() == null) {
            price.setDate(dateValidation.stringToDate());
        } else if (dateValidation.isValid(price.getDate())){
            return false;
        }

        BigDecimal temp = price.getAmount();
        temp = temp.setScale(4, BigDecimal.ROUND_UNNECESSARY).divide(new BigDecimal(price.getQuantity()), BigDecimal.ROUND_UNNECESSARY);

        price.setAmount(temp);

        priceRepository.save(price);
        return true;
    }

    @Override
    public void deleteById(Long id) {
        priceRepository.deleteById(id);
    }
}
