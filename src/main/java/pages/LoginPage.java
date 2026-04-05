package pages;

import com.microsoft.playwright.Page;

public class LoginPage extends BasePage {

    // Locators in Playwright are simple Strings
    private final String emailInput = "#email";
    private final String passwordInput = "#password";
    private final String loginBtn = "//button[@type='submit']";

    public LoginPage(Page page) {
        super(page);
    }

    public void navigateTo(String url) {
        page.navigate(url);
    }

    public void login(String email, String password) {
        type(emailInput, email);
        type(passwordInput, password);
        click(loginBtn);
    }
}
