package com.skorobagatiy.ClientDB.services;

import com.skorobagatiy.ClientDB.models.UserDto;
import com.skorobagatiy.ClientDB.utils.UserCsvMarshaller;
import com.skorobagatiy.ClientDB.utils.UserExcelMarshaller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class UserDownloadService {
    private static final Logger logger = LogManager.getLogger(UserDownloadService.class);
    private final UserExcelMarshaller userExcelMarshaller;
    private final UserCsvMarshaller userCsvMarshaller;
    private final UserService userService;

    public UserDownloadService(UserExcelMarshaller userExcelMarshaller, UserCsvMarshaller userCsvMarshaller, UserService userService) {
        this.userExcelMarshaller = userExcelMarshaller;
        this.userCsvMarshaller = userCsvMarshaller;
        this.userService = userService;
    }

    @Autowired


    public ByteArrayInputStream loadCsv() throws IOException {
        logger.info("Start UserDownloadService for all users download to CSV format.");


        List<UserDto> userDtoList = userService.getUsers();

        return userCsvMarshaller.parseUsersToCsv(userDtoList);
    }
    public ByteArrayInputStream loadExcel()  {
        logger.info("Start UserDownloadService for all users download to Excel format.");

        List<UserDto> userDtoList = userService.getUsers();

        return userExcelMarshaller.parseUsersToExcel(userDtoList);
    }

}
