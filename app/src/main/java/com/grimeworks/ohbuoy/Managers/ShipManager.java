package com.grimeworks.ohbuoy.Managers;

import com.grimeworks.ohbuoy.Models.*;
import com.grimeworks.ohbuoy.Utils.GameUtils;

/*
* Class: ShipManager
* Description: Will manage all interactions between ships and other objects
* Will also perform modifications to the ship objects
*
 */
public class ShipManager {

    public static Ship moveShip (Ship ship, Move move) {
        //We will be rotating the ship on the spot
        if (move.getDirection() == GameUtils.LEFT_MOVE ||
                move.getDirection() == GameUtils.RIGHT_MOVE){
            ship.setCurrentDirection(GameUtils.calculateRotation(ship.getCurrentDirection(), move.getDirection(), move.getModifier()));
        } else { //We will be moving the ship along x or y axis
            ship.setCurrentLocation(GameUtils.calculateMoving(ship.getCurrentLocation(), ship.getCurrentDirection(),
                    move.getDirection(), move.getModifier()));
        }
        return ship;
    }

    public static String reportShipCurrentLocation(Ship ship) {
        return "Ship is currently at "
                + ship.getCurrentLocation().getLatitude() + "," + ship.getCurrentLocation().getLongitude() +
                " bearing " + GameUtils.getBearingFromDeg(ship.getCurrentDirection());
    }

    public static boolean canShipMakeValidMove(double previous, double future) {
        if ((previous == GameUtils.BOARD_START && future <= GameUtils.BOARD_START) ||
            (previous == GameUtils.BOARD_SIZE && future >= GameUtils.BOARD_SIZE) ||
                    (previous == GameUtils.BOARD_SIZE && future < GameUtils.BOARD_START) ||
                    (previous == GameUtils.BOARD_START && future > GameUtils.BOARD_SIZE)){
            return false;
        } else
            return true;
    }

}