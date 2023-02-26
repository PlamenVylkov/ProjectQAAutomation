package com.selenium.course.tests;

import base.TestUtil;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductPage;

public class BuyItemsTest extends TestUtil {

    @Test
    public void buyItems() throws InterruptedException {
        LoginPage lgnPage = new LoginPage(driver);
        //Login with the hardcore user
        lgnPage.login("obnqlwgxtmtrclbham@bbitf.com", "123456");

        ProductPage prPage = new ProductPage(driver);
        //Add items to the basket
        prPage.addItemToTheBasket(4);
        //Add additional items if the purchase is less than the minimum. Enter the minimum value.
        prPage.totalAboveMinimum(30);
        //Buy the items
        prPage.buyItems();
        //Check the finalization message
        prPage.assertBuy();
    }
}
