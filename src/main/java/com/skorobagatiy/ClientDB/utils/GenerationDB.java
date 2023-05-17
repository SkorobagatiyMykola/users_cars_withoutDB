package com.skorobagatiy.ClientDB.utils;

import com.skorobagatiy.ClientDB.models.Car;
import com.skorobagatiy.ClientDB.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerationDB {
    public static int USER_COUNT = 15;
    private static int USER_ID = 0;
    private static int MIN_USER_AGE = 15;
    private static int MAX_USER_AGE = 75;
    private static String[] USER_NAMES = {"Ivan", "Ira", "Mykola", "Petro", "Pavlo", "Olga", "Tetiana"};
    private static String[] USER_SURNAMES = {"Tkachenko", "Petrenko", "Vasilenko", "Tkach", "Shevchenko", "Drozd"};
    public static int MAX_CARS = 7;
    private static int MIN_CAR_SPEED = 90;
    private static int MAX_CAR_SPEED = 180;
    private static String[] CAR_MODELS = {"BMW", "Mercedes", "Lada", "Volvo", "Citroen", "Honda"};

    private  List<User> users;

    public GenerationDB() {
        users = new ArrayList<>();
        for (int i = 0; i < USER_COUNT; i++) {
            int id = ++USER_ID;
            String name = getRandomNameFromArray(USER_NAMES);
            String surname = getRandomNameFromArray(USER_SURNAMES);
            int age = getRandomNumber(MIN_USER_AGE, MAX_USER_AGE);
            String email = name.toLowerCase() + "_" + surname.toLowerCase() + age + "@gmail.com";
            List<Car> cars = generationUserCars();

            User user = new User.Builder()
                    .withId(id)
                    .withName(name)
                    .withSurname(surname)
                    .withAge(age)
                    .withEmail(email)
                    .withCars(cars)
                    .build();

            users.add(user);
        }
    }

    public List<User> getUsers() {
        return users;
    }

    private static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    private static String getRandomNameFromArray(String[] array) {
        return array[getRandomNumber(0, array.length)];
    }

    private List<Car> generationUserCars() {
        List<Car> carList = new ArrayList<>();
        int countCars = getRandomNumber(0, MAX_CARS);
        for (int i = 0; i < countCars; i++) {
            String model = getRandomNameFromArray(CAR_MODELS);
            int speed = getRandomNumber(MIN_CAR_SPEED, MAX_CAR_SPEED);
            Car car = new Car(model, speed);
            carList.add(car);
        }
        return carList;
    }
}
