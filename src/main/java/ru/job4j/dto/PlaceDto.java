package ru.job4j.dto;

public class PlaceDto {
    private boolean free;
    private int row;
    private int placeNumber;

    public PlaceDto() {
    }

    public PlaceDto(boolean free, int row, int placeNumber) {
        this.free = free;
        this.row = row;
        this.placeNumber = placeNumber;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }
}
