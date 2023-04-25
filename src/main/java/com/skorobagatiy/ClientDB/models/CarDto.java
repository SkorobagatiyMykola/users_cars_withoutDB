package com.skorobagatiy.ClientDB.models;

import java.util.ArrayList;
import java.util.List;

public class CarDto {
    private int id;
    private String model;
    private int speed;

    public CarDto() {
    }

    public static List<CarDto> convertCarDto(List<Car> carList) {
        List<CarDto> dtoList = new ArrayList<>();

        int i = 0;
        for (Car car : carList) {
            CarDto carDto = new CarDto();
            carDto.setId(++i);
            carDto.setModel(car.getModel());
            carDto.setSpeed(car.getSpeed());
            dtoList.add(carDto);
        }


        return dtoList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
