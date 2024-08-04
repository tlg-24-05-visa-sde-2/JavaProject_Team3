package gov.trivia.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class PlayerTest {

    /*The Player class is tested to ensure it correctly handles the player's name,
     * including the constructor, getName, and setName methods.
     */




    @Test
    public void testConstructor() {
        Player player = new Player("Tobi");
        assertEquals("Tobi", player.getName());
    }

    @Test
    public void testGetName() {
        Player player = new Player("Tobi");
        assertEquals("Tobi", player.getName());
    }

    @Test
    public void testSetName() {
        Player player = new Player("Tobi");
        player.setName("Kelvin");
        assertEquals("Kelvin", player.getName());
    }
}
