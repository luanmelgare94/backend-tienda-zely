package com.tienda.zely.util;

import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.tienda.zely.dto.product.ProductResponseDto;
import java.util.List;
import java.io.PrintWriter;

public class WriteCsvToResponse {

    public static void writeDataToCsvWithListObjects(PrintWriter writer,List<ProductResponseDto> customers) {
        String[] CSV_HEADER = { "codigoProducto", "nombre", "codigoTipoProducto", "precio" };
        StatefulBeanToCsv beanToCsv = null;
        try (
                CSVWriter csvWriter = new CSVWriter(writer,
                        '|',
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);
        ){
            csvWriter.writeNext(CSV_HEADER);
            // write List of Objects
            ColumnPositionMappingStrategy mappingStrategy =
                    new ColumnPositionMappingStrategy();

            mappingStrategy.setType(ProductResponseDto.class);
            mappingStrategy.setColumnMapping(CSV_HEADER);

            beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withMappingStrategy(mappingStrategy)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withSeparator('|')
                    .build();
            beanToCsv.write(customers);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public static void writeProducts(PrintWriter writer, List<ProductResponseDto> products) {

        try {

            ColumnPositionMappingStrategy<ProductResponseDto> mapStrategy
                    = new ColumnPositionMappingStrategy<>();
            mapStrategy.setType(ProductResponseDto.class);
            String[] columns = new String[]{"nombre", "precio"};
            mapStrategy.setColumnMapping(columns);
            StatefulBeanToCsv<ProductResponseDto> btcsv = new StatefulBeanToCsvBuilder<ProductResponseDto>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withMappingStrategy(mapStrategy)
                    .withSeparator(',')
                    .build();
            btcsv.write(products);
        } catch (CsvException ex) {
            LOGGER.error("Error mapping Bean to CSV", ex);
        }
    }*/

}




