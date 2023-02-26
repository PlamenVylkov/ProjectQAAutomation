package com.selenium.course.tests;

import base.TestUtil;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class SuccessfulLoginTest extends TestUtil {

    @Test (dataProvider = "correctUsers" )
    public void successfulLogin(String username, String password){
        //Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username,password);

        //Assert for logout button
        loginPage.AssertForLogoutBtn();
    }

    @DataProvider(name = "correctUsers")
    public Object [][] readCorrectUsersFromCSV() throws FileNotFoundException {
        try {
            //Read csv file
            CSVReader csvReader = new CSVReader(new FileReader("src/test/resources/correctUsers.csv"));
            List<String[]> csvData = csvReader.readAll();
            Object[] [] csvDataObj = new Object[csvData.size()][2];
            for (int i = 0; i < csvData.size(); i++){
                csvDataObj[i] = csvData.get(i);
            }
            return csvDataObj;
        }catch (IOException e){
            System.out.println("CSV file not found!");
            return null;
        }
        catch (CsvException e){
            return null;
        }
    }
}
