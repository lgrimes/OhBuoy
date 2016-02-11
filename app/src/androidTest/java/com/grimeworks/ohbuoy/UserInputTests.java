package com.grimeworks.ohbuoy;
import com.grimeworks.ohbuoy.Utils.GameUtils;

import org.junit.Test;
import org.junit.Test;
import java.util.regex.Pattern;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.hamcrest.*;

public class UserInputTests {

    @Test
    public void userInput_incorrectDirection_returnsFalse(){
        assertFalse(GameUtils.validateUserInput("T5"));
    }

    @Test
    public void userInput_incorrectNumber_returnsFalse(){
        assertFalse(GameUtils.validateUserInput("F10"));
    }

    @Test
    public void userInput_twoAlpheCharacters_returnsFalse(){
        assertFalse(GameUtils.validateUserInput("FF"));
    }

    @Test
    public void userInput_twoNumbers_returnsFalse(){
        assertFalse(GameUtils.validateUserInput("22"));
    }
}
