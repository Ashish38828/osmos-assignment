package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BasePage {
    protected Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    protected void type(String locator, String text) {
        page.fill(locator, text);
    }

    protected void click(String locator) {
        page.click(locator);
    }

    // Handles custom UI dropdowns (like Radix UI)
    protected void selectCustomDropdown(String triggerLocator, String visibleText) {
        page.click(triggerLocator);
        String optionLocator = String.format("//*[@role='option' and normalize-space(text())='%s'] | //*[@role='option']//*[normalize-space(text())='%s']", visibleText, visibleText);
        page.click(optionLocator);
    }

    // Handles dates using Playwright's evaluate (JavaScript execution)
    protected void setDateByJS(String locator, String dateMMDDYYYY) {
        // Convert MM/dd/yyyy to yyyy-MM-dd
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        LocalDate parsedDate = LocalDate.parse(dateMMDDYYYY, inputFormatter);
        String domFormattedDate = parsedDate.format(outputFormatter);
        
        // Playwright evaluates JS directly on the targeted element
        String jsScript = String.format("el => { el.value = '%s'; el.dispatchEvent(new Event('change', { bubbles: true })); }", domFormattedDate);
        page.locator(locator).evaluate(jsScript);
    }

    // Handles custom UI checkboxes
    protected void setCustomCheckbox(String locator, boolean checkIt) {
        Locator checkboxBtn = page.locator(locator);
        boolean isCurrentlyChecked = "true".equalsIgnoreCase(checkboxBtn.getAttribute("aria-checked"));
        
        if (isCurrentlyChecked != checkIt) {
            checkboxBtn.click();
        }
    }

    protected String getText(String locator) {
        return page.locator(locator).innerText();
    }
}
