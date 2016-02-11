package com.grimeworks.ohbuoy.Models;/* -----------------------------------
 * Created by laurengrimes - Grimeworks
 * On 10/02/16
 *
 * com.grimeworks.ohbuoy.Models
 * 
 * -----------------------------------
 */

import android.location.Location;

public class Board {
    private Ship ship;
    private Location dockLocation;

    public Board(Ship ship, Location dockLocation) {
        this.ship = ship;
        this.dockLocation = dockLocation;
    }

    public Location getDockLocation() {
        return dockLocation;
    }

    public void setDockLocation(Location dockLocation) {
        this.dockLocation = dockLocation;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Ship getShip() {
        return ship;
    }
}
