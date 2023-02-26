package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class ProductPage extends BasePage{

    //Elements on the current page
    @FindBy(xpath = "/html/body/div[1]/header/div[2]/div/div/div[1]/div/ul/li[1]/a")
    private WebElement promoBtn;

    @FindBy(className = "btn-buy")
    private List<WebElement> buyBtns;

    @FindBy(xpath = "//*[@id=\"page-header-main-content\"]/div/div[4]/div[1]/small")
    private WebElement basket;

    @FindBy(className = "cart-dropdown")
    private WebElement dragBasket;

    @FindBy(className = "btn-remove-from-cart")
    private List<WebElement> removeBtns;

    @FindBy(xpath = "//a[text()='Виж количка']")
    private WebElement viewBasket;

    @FindBy(className = "order-btn")
    private WebElement orderBtn;

    @FindBy(xpath = "//*[@id=\"page-header-main-content\"]/div/div[4]/div[2]/div[2]/div/p[1]/span[2]/span[1]")
    private WebElement total;

    @FindBy(xpath = "//button[@aria-label='Плюс']")
    private List<WebElement> plusBtn;

    @FindBy(className = "noty_bar")
    private WebElement outOfStockMessage;

    @FindBy(xpath = "//span[text()='Финализиране на поръчка']")
    private WebElement finalizeMessage;

    public ProductPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void addItemToTheBasket(int countItems) throws InterruptedException {

        //Wait 1 second
        Thread.sleep(1000);

        //Remove all items form the basket if it is not empty
        if (getItemsinTheBasket() > 0) {

            Actions actions = new Actions(driver);
            actions.moveToElement(dragBasket).perform();
            for(WebElement removeBtn : removeBtns){
                removeBtn.click();
            }
        }

        //Open promo page
        promoBtn.click();

        //Explicit wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000));
        wait.until(ExpectedConditions.visibilityOf(buyBtns.get(0)));

        //Add items to the basket
        for(int i = 0; i < countItems; i++){

            buyBtns.get(0).click();
            Thread.sleep(1000);
        }
    }

    public int getItemsinTheBasket(){
        //Get number of items
        int Items = Integer.parseInt(basket.getText().split(" ")[0]);

        return Items;
    }

    public void totalAboveMinimum(float min) {
        //Instantiating Actions class
        Actions actions = new Actions(driver);
        //Move cursor over the basket
        actions.moveToElement(dragBasket).perform();
        //Get total and covert string to float
        float Total = Float.parseFloat(total.getText().replace(',', '.'));
        int i=0;
        //If the amount is less than the minimum add more items
        while (Total < min){
            plusBtn.get(i).click();
            try{
                outOfStockMessage.isDisplayed();
            } catch (Exception e) {
                i++;
            }
            //Update the value
            Total = Float.parseFloat(total.getText().replace(',', '.'));
        }

    }

    public void buyItems() {
        //Instantiating Actions class
        Actions actions = new Actions(driver);
        //Move cursor over the basket
        actions.moveToElement(dragBasket).perform();
        viewBasket.click();

        //Move scroll
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView()", orderBtn);

        //Click the button
        orderBtn.click();
    }

    public void assertCountItems(int countItems){
        //Check number of items with number in the basket
        Assert.assertEquals(getItemsinTheBasket(), countItems, "The count of items in the basket is different!");
    }

    public void assertBuy(){
        //Check the finalization message
        Assert.assertEquals(finalizeMessage.getText().split("text()=")[0], "Финализиране на поръчка", "The finalization message is missing!");
    }
}
