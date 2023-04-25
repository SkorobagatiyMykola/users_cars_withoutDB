package com.skorobagatiy.ClientDB.services;

import com.skorobagatiy.ClientDB.exceptions.GenericSystemException;
import com.skorobagatiy.ClientDB.exceptions.NoSuchUserException;
import com.skorobagatiy.ClientDB.models.Car;
import com.skorobagatiy.ClientDB.models.CarDto;
import com.skorobagatiy.ClientDB.models.User;
import com.skorobagatiy.ClientDB.models.UserDto;
import com.skorobagatiy.ClientDB.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.skorobagatiy.ClientDB.models.CarDto.convertCarDto;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository usersRepository) {
        this.userRepository = usersRepository;
    }

    public List<UserDto> getUsers() {
        List<User> users = userRepository.getUsers();

        List<UserDto> userDTOList = users.stream().
                map(el-> UserDto.convertUserDto(el)).collect(Collectors.toList());

        return userDTOList;
    }

    public User getUserById(int userId) throws GenericSystemException {
        User user = userRepository.getUserById(userId);

        if (user == null) {
            String message = String.format("User with id %s is not present", userId);
            throw new NoSuchUserException(HttpStatus.BAD_REQUEST, message);
        }

        return user;
    }

    public List<CarDto> getCarsUserById(int userId) throws GenericSystemException {
        User user = userRepository.getUserById(userId);

        if (user == null) {
            String message = String.format("User with id %s is not present", userId);
            throw new NoSuchUserException(HttpStatus.BAD_REQUEST, message);
        }

        List<CarDto>  result = convertCarDto(user.getCars());

        return result;
    }

    public User createUser(User userRequest) {
        User user = userRepository.save(userRequest);
        return user;
    }

    public void deleteUser(int userId) throws GenericSystemException {

        boolean result = userRepository.deleteUserById(userId);

        if (!result) {
            String message = String.format("User with id %s is not present", userId);
            throw new NoSuchUserException(HttpStatus.BAD_REQUEST, message);
        }
    }

    public User updateUser(int userId, User updateUser) throws GenericSystemException {
        User user = userRepository.updateUserById(userId, updateUser);
        if (user == null) {
            String message = String.format("User with id %s is not present", userId);
            throw new NoSuchUserException(HttpStatus.BAD_REQUEST, message);
        }

        return user;
    }

    public User addCarForUser(int userId, Car carNew) throws GenericSystemException {
        User user = userRepository.getUserWithNewCar(userId, carNew);
        if (user == null) {
            String message = String.format("User with id %s is not present", userId);
            throw new NoSuchUserException(HttpStatus.BAD_REQUEST, message);
        }

        return user;
    }
}
