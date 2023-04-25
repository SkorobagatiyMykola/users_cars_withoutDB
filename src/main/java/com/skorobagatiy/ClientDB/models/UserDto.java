package com.skorobagatiy.ClientDB.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDto {
    private int id;
    private String name;
    private String surname;
    private int age;
    private String email;
    private int countCars;
    private String carModels;

    public UserDto() {
    }

    public UserDto(int id, String name, String surname, int age, String email, int countCars, String carModels) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.countCars = countCars;
        this.carModels = carModels;
    }


    public static UserDto convertUserDto(User user) {
        UserDto result = new UserDto();
        result.setId(user.getId());
        result.setName(user.getName());
        result.setSurname(user.getSurname());
        result.setAge(user.getAge());
        result.setEmail(user.getEmail());
        result.setCountCars(user.getCars().isEmpty() ? 0 : user.getCars().size());
        result.setCarModels(user.getCars().isEmpty() ?
                " - " : user.getCars().stream().map(car->car.getModel()).collect(Collectors.joining(", ")));

        return result;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCountCars() {
        return countCars;
    }

    public void setCountCars(int countCars) {
        this.countCars = countCars;
    }

    public String getCarModels() {
        return carModels;
    }

    public void setCarModels(String carModels) {
        this.carModels = carModels;
    }
}
