package com.wowItemsAPI.service;

import com.wowItemsAPI.entity.Item;
import com.wowItemsAPI.entity.Price;
import com.wowItemsAPI.helper.DateValidation;
import com.wowItemsAPI.repository.ItemRepository;
import com.wowItemsAPI.repository.PriceRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class PriceServiceImpl implements PriceService {

    private PriceRepository priceRepository;

    @Autowired
    public PriceServiceImpl(PriceRepository priceRepository){this.priceRepository = priceRepository;}


    @Override
    public List<Price> findAll() {
        List<Price> priceList = priceRepository.findAll();
        if (priceList.isEmpty()){
            priceList = new ArrayList<>();
            Price price = new Price();
            price.setId(0);
            price.setAmount(0);
            price.setDate(new Date());
            price.setPrice(BigDecimal.ZERO);
            priceList.add(price);
        }
        return priceList;
    }

    @Override
    public Price findById(int id) {
        Optional<Price> result = priceRepository.findById(id);

        Price price = null;

        if (result.isPresent()) {
            price = result.get();
        }
        else {
            throw new RuntimeException("Did not find employee id - " + id);
        }

        return price;
    }

    @Override
    public boolean save(Price price) {
        DateValidation dateValidation = new DateValidation();
        if (price.getDate() == null) {
            price.setDate(dateValidation.stringToDate());
        } else if (dateValidation.isValid(price.getDate())){
            return false;
        }
        priceRepository.save(price);
        return true;
    }

    @Override
    public void deleteById(int id) {
        priceRepository.deleteById(id);
    }
}
