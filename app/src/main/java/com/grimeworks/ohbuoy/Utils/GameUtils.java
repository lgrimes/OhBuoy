package com.grimeworks.ohbuoy.Utils;/* -----------------------------------
 * Created by laurengrimes - Grimeworks
 * On 10/02/16
 *
 * com.grimeworks.ohbuoy.Utils
 * 
 * -----------------------------------
 */

import android.location.Location;

import com.grimeworks.ohbuoy.Managers.ShipManager;
import com.grimeworks.ohbuoy.Models.Move;

public class GameUtils {
    //Move constants
    public static char LEFT_MOVE = 'L';
    public static char RIGHT_MOVE = 'R';
    public static char FORWARD_MOVE = 'F';
    public static char BACKWARD_MOVE = 'B';
    //Direction constants
    public static double NORTH = 360;
    public static double SOUTH = 180;
    public static double EAST = 90;
    public static double WEST = 270;
    public static double TURN_ANGLE = 90;
    public static double FULL_ROTATION = 360;
    //Board Setup
    public static int BOARD_SIZE = 10;
    public static int BOARD_START = 0;
    public static double DOCK_START = 0;

    public static Move parseUserInputToMove(String input){
        //Validated that input should be in the format of DIRECTION-MODIFIER
        if (GameUtils.validateUserInput(input)) {
            Move newMove = new Move();
            newMove.setDirection(input.toUpperCase().charAt(0));
            newMove.setModifier(Character.getNumericValue(input.toUpperCase().charAt(1)));
            return newMove;
        }
        return null;
    }

    public static Boolean validateUserInput (String input) {
        if (input.length() == 2) {
            String firstChar = input.substring(0,1).toUpperCase();
            String secondChar = input.substring(1);
            //We need the first character to be either L,R,F,B
            if (firstChar.equals(String.valueOf(LEFT_MOVE)) ||
                    firstChar.equals(String.valueOf(RIGHT_MOVE)) ||
                    firstChar.equals(String.valueOf(FORWARD_MOVE)) ||
                    firstChar.equals(String.valueOf(BACKWARD_MOVE))) {
                try {
                    int modifier = Integer.parseInt(secondChar);
                    if (modifier > 0 && modifier <= 9)
                        return true;
                } catch (NumberFormatException ex){
                    return false;
                }
            }
        }
        return false;
    }

    //region Rotation Methods
    public static double calculateRotation (double startingBearing, char direction, int modifier){
        if (direction == GameUtils.LEFT_MOVE)
            return GameUtils.calculateLeftRotation(startingBearing, modifier);
        else
            return GameUtils.calculateRightRotation(startingBearing, modifier);
    }

    public static double calculateLeftRotation(double startingBearing, int modifier) {
        // Instead of tyring to deal with left negative rotations ie (-90),
        // we minus the modifier * 90 from a full rotation
        double newBearing = startingBearing - (modifier * TURN_ANGLE);
        if (newBearing <= 0)
            newBearing = newBearing + FULL_ROTATION;
        return newBearing;
    }

    public static double calculateRightRotation(double startingBearing, int modifier) {
        double newBearing = startingBearing + modifier * TURN_ANGLE;
        if (newBearing > FULL_ROTATION)
            newBearing = newBearing - FULL_ROTATION;
        return newBearing;
    }

    //endregion

    //region Moving Methods
    public static Location calculateMoving (Location startLocation, double currentDirection, char direction, int modifier){
        // We will move along the Y axis of the board
        Location newLocation = startLocation;
        if (currentDirection == GameUtils.NORTH || currentDirection == GameUtils.SOUTH){
            if ((direction == GameUtils.FORWARD_MOVE && currentDirection == GameUtils.NORTH) ||
                    (direction == GameUtils.BACKWARD_MOVE && currentDirection == GameUtils.SOUTH)) {
                //Lets decrease the Y position
                if (ShipManager.canShipMakeValidMove(startLocation.getLongitude(),startLocation.getLongitude() - modifier))
                    newLocation.setLongitude(startLocation.getLongitude() - modifier);
            } else {
                //Lets increase the Y position
                if (ShipManager.canShipMakeValidMove(startLocation.getLongitude(),startLocation.getLongitude() + modifier))
                    newLocation.setLongitude(startLocation.getLongitude() + modifier);
            }
            if (newLocation.getLongitude() > GameUtils.BOARD_SIZE)
                newLocation.setLongitude(GameUtils.BOARD_SIZE);
            else if (newLocation.getLongitude() < GameUtils.BOARD_START)
                newLocation.setLongitude(GameUtils.BOARD_START);
        } else { // We will move along the X axis
            if ((direction == GameUtils.FORWARD_MOVE && currentDirection == GameUtils.WEST) ||
                    (direction == GameUtils.BACKWARD_MOVE && currentDirection == GameUtils.EAST)) {
                //Lets decrease the X position
                if (ShipManager.canShipMakeValidMove(startLocation.getLatitude(),startLocation.getLatitude() - modifier))
                    newLocation.setLatitude(startLocation.getLatitude() - modifier);
            } else {
                //Lets increase the Y position
                if (ShipManager.canShipMakeValidMove(startLocation.getLatitude(),startLocation.getLatitude() + modifier))
                    newLocation.setLatitude(startLocation.getLatitude() + modifier);
            }

            if (newLocation.getLatitude() > GameUtils.BOARD_SIZE)
                newLocation.setLatitude(GameUtils.BOARD_SIZE);
            else if (newLocation.getLatitude() < GameUtils.BOARD_START)
                newLocation.setLatitude(GameUtils.BOARD_START);
        }
        return newLocation;
    }

    //endregion

    public static char getBearingFromDeg(double direction){
        switch ((int)direction){
            case 360: return 'N';
            case 0: return 'N';
            case 90: return 'E';
            case 180: return 'S';
            case 270: return 'W';
        }
        return 'N';
    }
}
