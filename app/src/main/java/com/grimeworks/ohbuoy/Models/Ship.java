package com.grimeworks.ohbuoy.Models;


import android.location.Location;

import java.util.ArrayList;

public class Ship {
    private Location currentLocation;
    private double currentDirection;
    public ArrayList<Location> previousLocations;
    public ArrayList<Integer> previousBearings;

    public Ship(Location startLocation, double startingBearing) {
        this.currentLocation = startLocation;
        this.currentDirection = startingBearing;
        previousBearings = new ArrayList<>();
        previousLocations = new ArrayList<>();
    }

    public double getCurrentDirection() {
        return currentDirection;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public ArrayList<Integer> getPreviousBearings() {
        return previousBearings;
    }

    public ArrayList<Location> getPreviousLocations() {
        return previousLocations;
    }

    public void setCurrentDirection(double currentDirection) {
        this.currentDirection = currentDirection;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }



}
