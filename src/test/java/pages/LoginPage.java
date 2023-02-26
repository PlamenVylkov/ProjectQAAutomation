package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class LoginPage extends BasePage {

    //Elements on the current page
    @FindBy(className = "s-profile")
    private WebElement profileButton;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(className = "login-btn")
    private WebElement loginBtn;

    @FindBy(className = "profile-dropdown")
    private WebElement dragAndDropMenu;

    @FindBy(xpath = "//a[text()='Изход']")
    private WebElement logoutLink;

    @FindBy(xpath = "//li[text()='Невалидно име/парола']")
    private WebElement errorMessage;

    //Constructor
    public LoginPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    //Methods
    public LoginPage login(String userName, String password) {
        //Entering username and password
        profileButton.click();

        //Entering email address
        emailInput.click();
        emailInput.clear();
        emailInput.sendKeys(userName);

        //Entering password
        passwordInput.click();
        passwordInput.clear();
        passwordInput.sendKeys(password);

        //Press login button
        loginBtn.click();
        return new LoginPage(driver);
    }

    public LoginPage AssertForLogoutBtn(){

        //Instantiating Actions class
        Actions actions = new Actions(driver);

        //Hovering on main menu
        actions.moveToElement(dragAndDropMenu).perform();

        //Assert for logout link
        Assert.assertTrue(logoutLink.isDisplayed(), "Logout link is not displayed");

        return new LoginPage(driver);
    }

    public LoginPage AssertForError(){

        //Assert for error message
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message is not displayed");

        return new LoginPage(driver);
    }

}
