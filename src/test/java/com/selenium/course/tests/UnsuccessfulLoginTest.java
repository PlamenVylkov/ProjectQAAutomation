package com.selenium.course.tests;

import base.TestUtil;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class UnsuccessfulLoginTest extends TestUtil {

    @Test (dataProvider = "wrongUsers" )
    public void successfulLogin(String username, String password) throws InterruptedException {
        //Entering email and password
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username,password);

        //Assert for error message
        loginPage.AssertForError();
    }

    @DataProvider (name = "wrongUsers")
    public Object [][] readWrongUsersFromCSV() throws FileNotFoundException {
        try {
            //Read CSV file
            CSVReader csvReader = new CSVReader(new FileReader("src/test/resources/wrongUsers.csv"));
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
