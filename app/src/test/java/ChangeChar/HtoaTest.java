package ChangeChar;

import org.junit.Test;

import static org.junit.Assert.*;

public class HtoaTest {

    @Test
    public void doChange() {
        Htoa htoa = new Htoa();
        assertEquals("a", htoa.doChange("H"));
        assertEquals("g", htoa.doChange("g"));
    }
}