package com.grimeworks.ohbuoy;
import com.grimeworks.ohbuoy.Managers.ShipManager;

import org.junit.Test;
import org.junit.Test;
import java.util.regex.Pattern;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.hamcrest.*;
public class ShipPositioningTests {
    @Test
    public void shipPositioning_outOfBoundsLat_returnsFalse(){
        assertFalse(ShipManager.canShipMakeValidMove((double)5, (double)10));
    }

//    @Test
//    public void shipPositioning_outOfBoundsLat_returnsFalse(){
//        assertFalse(ShipManager.canShipMakeValidMove((double) 5, (double) 10));
//    }
}
