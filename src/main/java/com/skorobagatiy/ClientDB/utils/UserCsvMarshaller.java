package com.skorobagatiy.ClientDB.utils;

import com.skorobagatiy.ClientDB.models.UserDto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.valueOf;

@Component
public class UserCsvMarshaller {

    private static final Logger logger = LogManager.getLogger(UserCsvMarshaller.class);
    private static final String[] headers = {"id", "user_name", "user_surname", "age",
            "email", "count_cars", "car_models"};

    public ByteArrayInputStream parseUsersToCsv(List<UserDto> userDtoList) throws IOException {
        logger.info(" === UserCsvMarshaller started parsing for all users ==== ");
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {

            printHeaders(csvPrinter, headers);
            for (UserDto dto : userDtoList) {
                List<String> data = Arrays.asList(
                        valueOf(dto.getId()),
                        dto.getName(),
                        dto.getSurname(),
                        valueOf(dto.getAge()),
                        dto.getEmail(),
                        valueOf(dto.getCountCars()),
                        dto.getCarModels()
                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            logger.error("Fail to export data to CSV file {}", e.getMessage(), e);
            throw new IOException("Fail to export data to CSV file ", e);
        }
    }

    private static void printHeaders(CSVPrinter csvPrinter, String[] headers) throws IOException {
        csvPrinter.printRecord(Arrays.asList(headers));
    }
}
