package com.example.gift_app;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    //IT19062884 My adding

    private FinalPageRK finalPageRK;

    @Before
    public void setUp(){
        finalPageRK = new FinalPageRK();
    }

    @Test

    public void Total_Amount_is_correct(){
        Double result = finalPageRK.calculate_Final_Amount(10.00,20.00,30.00);
        assertEquals(60.00,result,0.01);
    }

//passed the test without any error

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}