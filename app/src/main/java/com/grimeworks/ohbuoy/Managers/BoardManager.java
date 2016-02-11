package com.grimeworks.ohbuoy.Managers;

import com.grimeworks.ohbuoy.Models.*;

public class BoardManager {

    public static void moveShipOnBoard(Board board, Move move) {
        board.setShip(ShipManager.moveShip(board.getShip(), move));
    }
}
