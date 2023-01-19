package com.skorobagatiy.ClientDB.repositories;

import com.skorobagatiy.ClientDB.models.Car;
import com.skorobagatiy.ClientDB.models.User;
import com.skorobagatiy.ClientDB.utils.GenerationDB;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private List<User> users = new GenerationDB().getUsers();
    private static int USER_COUNT = GenerationDB.USER_COUNT;

    public List<User> getUsers() {
        return users;
    }

    public User getUserById(int userId) {
        return users.stream().filter(user -> user.getId() == userId)
                .findAny().orElse(null);
    }

    public User save(User userRequest) {
        User user = new User.Builder()
                .withId(++USER_COUNT)
                .withName(userRequest.getName())
                .withSurname(userRequest.getSurname())
                .withAge(userRequest.getAge())
                .withEmail(userRequest.getEmail())
                .build();
        users.add(user);
        return user;
    }

    public User updateUserById(int userId, User updateUser) {
        User user = getUserById(userId);

        if (user != null) {
            user.setName(updateUser.getName());
            user.setSurname(updateUser.getSurname());
            user.setAge(updateUser.getAge());
            user.setEmail(updateUser.getEmail());
        }

        return user;
    }

    public boolean deleteUserById(int userId) {
        return users.removeIf(user -> user.getId() == userId);
    }

    public User getUserWithNewCar(int userId, Car carNew) {
        User user = getUserById(userId);

        if (user != null) {
            List<Car> carList = user.getCars();
            carList.add(carNew);
            user.setCars(carList);
        }

        return user;
    }
}
