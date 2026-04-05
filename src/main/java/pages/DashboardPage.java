package pages;

import com.microsoft.playwright.Page;

public class DashboardPage extends BasePage {

    private final String createLeadNavBtn = "//button[@data-testid='leads-create-new-btn']";
    private final String firstLeadNameInList = "table tbody tr:first-child td.lead-name";

    public DashboardPage(Page page) {
        super(page);
    }

    public void goToCreateLead() {
        click(createLeadNavBtn);
    }

    public String getFirstLeadName() {
        // Explicitly wait for the list to populate before grabbing text
        page.waitForSelector(firstLeadNameInList);
        return getText(firstLeadNameInList);
    }
}
