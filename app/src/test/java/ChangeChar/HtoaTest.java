package ChangeChar;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HtoaTest {

    @Test
    public void doChange() {
        Htoa htoa = new Htoa();
        assertEquals("a", htoa.doChange("H"));//測試輸入字串參數 H 時，會不會輸出a
        assertEquals("g", htoa.doChange("g"));//測試輸入字串參數 g 時，會不會輸出g

    }
}