package com.selenium.course.tests;

import base.TestUtil;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductPage;

public class AddItemsTest extends TestUtil {

    @Test
    public void addItemToTheCart() throws InterruptedException {
        LoginPage lgnPage = new LoginPage(driver);
        //Login
        lgnPage.login("obnqlwgxtmtrclbham@bbitf.com", "123456");


        ProductPage prPage = new ProductPage(driver);

        //Add items to the basket
        prPage.addItemToTheBasket(4);

        //Check items count
        prPage.assertCountItems(4);
    }
}
