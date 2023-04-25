package com.skorobagatiy.ClientDB.utils;

import com.skorobagatiy.ClientDB.models.UserDto;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserExcelMarshaller {
    private static final Logger logger = LogManager.getLogger(UserExcelMarshaller.class);
    private String usersTemplateFilePath;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static String DEVELOPER;

    @Autowired
    public UserExcelMarshaller(@Value("${reporting.users.template.file.path}") String usersTemplateFilePath) {
        this.usersTemplateFilePath = usersTemplateFilePath;
    }

    @Value("${reporting.creator-developer}")
    public void setProperty(final String propertyName) {
        DEVELOPER = propertyName;
    }

    public ByteArrayInputStream parseUsersToExcel(List<UserDto> userDtoList)  {
        logger.info(" ==== UserExcelMarshaller started parsing for all users ==== ");

        Map beanParams = new HashMap();
        beanParams.put("report_info", "Report for all user, " + " for " + LocalDate.now());
        return getByteArrayInputStream(userDtoList, beanParams, usersTemplateFilePath);
    }

    private ByteArrayInputStream getByteArrayInputStream(List<UserDto> userDtoList,
                                                         Map beanParams, String usersFilePath) {
        LocalDateTime localDateTime = LocalDateTime.now();
        beanParams.put("date_report", "Time of request: " + localDateTime.format(DATE_FORMATTER) +
                " at " + localDateTime.format(TIME_FORMATTER));
        beanParams.put("report_developer", " * - created by "+DEVELOPER);
        beanParams.put("users", userDtoList);

        XLSTransformer transformer = new XLSTransformer();
        try (InputStream in = new BufferedInputStream(
                UserExcelMarshaller.class.getResourceAsStream(usersFilePath));
             ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Workbook workbook = transformer.transformXLS(in, beanParams);
            workbook.write(out);
            out.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException | InvalidFormatException e) {
            logger.error("Fail to export data to Excel file {}", e.getMessage(), e);
            try {
                throw new IOException("Fail to export data to Excel file", e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
