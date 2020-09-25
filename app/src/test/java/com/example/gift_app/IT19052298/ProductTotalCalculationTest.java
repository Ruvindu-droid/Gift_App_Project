package com.example.gift_app.IT19052298;
import com.example.gift_app.CartActivity;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

//IT19052298

public class ProductTotalCalculationTest {


    private CartActivity cartActivity;

    @Before

    public void setUp(){

        cartActivity = new CartActivity();


    }

    @Test

    public void is_total_product_price_correct(){

        float result = cartActivity.calculateProductPrice((float) 10.0,2);
        assertEquals(20.0,result,0.01);
    }

    @Test

    public void is_total_price_correct(){
        float result = cartActivity.calculateTotalProductPrice((float) 30.0, 20.0f);
        assertEquals(50.0,result,0.01);
    }



}
