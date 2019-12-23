package com.wowItemsAPI.util;

import com.wowItemsAPI.entity.Item;
import com.wowItemsAPI.entity.Price;
import lombok.Data;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
@Configuration
public class ExcelReader {
    public List<Item> readExcelFile(MultipartFile file){
        File tempFile;
        Workbook wb;
        Sheet sheet = null;
        try {
            tempFile = File.createTempFile("prefix", "suffix");
            file.transferTo(tempFile);
            wb = WorkbookFactory.create(tempFile);
            sheet = wb.getSheetAt(0);
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }

        return getData(sheet);
    }

    private List<Item> getData(Sheet sheet){
        List<Item> itemList = new LinkedList<>();
        int counter = 1;
        Item item;
        Row row = sheet.getRow(counter);

        while (!isCellEmpty(row.getCell(0))){
            item = new Item();
            item.setName(row.getCell(0).getStringCellValue());

            item.setPriceList(getDataPrice(row));

            itemList.add(item);

            row = sheet.getRow(++counter);
        }

        return itemList;
    }

    private List<Price> getDataPrice(Row row){
        List<Price> priceList = new LinkedList<>();
        Price price;
        int column = 4;

        while (column < 30000){
            if (!isCellEmpty(row.getCell(column))){
                price = new Price();
                price.setQuantity((int)row.getCell(column + 1).getNumericCellValue());
                price.setAmount(new BigDecimal(Double.toString(row.getCell(column).getNumericCellValue())));
                priceList.add(price);
            }

            column += 3;
        }

        Date dt = new Date(new Date().getTime() - 86400000);

        for (int i = priceList.size() - 1; i >= 0; i--) {
            priceList.get(i).setDate(dt);
            dt = new Date(dt.getTime() - 86400000);
        }

        return priceList;
    }

    private boolean isCellEmpty(Cell cell){
        if (cell == null || cell.getCellTypeEnum() == CellType.BLANK)
            return true;

        return false;
    }

}
