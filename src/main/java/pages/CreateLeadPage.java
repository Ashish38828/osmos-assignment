package pages;

import com.microsoft.playwright.Page;

public class CreateLeadPage extends BasePage {

    // Basic Information Locators
    private final String nameInput = "#create-name";
    private final String emailInput = "#create-email";
    private final String phoneInput = "#create-phone";
    private final String companyInput = "#create-company";
    private final String jobTitleInput = "#create-job-title";

    // Lead Classification Locators
    private final String industryDropdown = "//button[@data-testid='create-form-industry-select']";
    private final String sourceDropdown = "//button[@data-testid='create-form-source-select']";
    private final String priorityDropdown = "//button[@data-testid='create-form-priority-select']";
    private final String statusDropdown = "//button[@data-testid='create-form-status-select']";

    // Deal Information Locators
    private final String dealValueInput = "//input[@data-testid='create-form-deal-value-input']";
    private final String expectedCloseDateInput = "//input[@data-testid='create-form-expected-close-input']";
    private final String followUpDateInput = "//input[@data-testid='create-form-follow-up-input']";

    // Additional Details Locators
    private final String isQualifiedCheckbox = "//button[@data-testid='create-form-is-qualified-checkbox']";
    private final String emailOptInCheckbox = "//button[@data-testid='create-form-email-opt-in-checkbox']";
    private final String notesTextarea = "//textarea[@data-testid='create-form-notes-textarea']";

    // Create Lead Button
    private final String createLeadBtn = "//button[@data-testid='create-form-submit-btn']";

    public CreateLeadPage(Page page) {
        super(page);
    }

    public void fillAndSubmitLead(String name, String email, String phone, String company, String jobTitle,
                                  String industry, String source, String priority, String status, 
                                  String dealValue, String expectedCloseDate, String followUpDate,
                                  boolean isQualified, boolean emailOptIn, String notes) {
        
        // 1. Basic Information
        type(nameInput, name);
        type(emailInput, email);
        type(phoneInput, phone);
        type(companyInput, company);
        type(jobTitleInput, jobTitle);
        
        // 2. Lead Classification
        selectCustomDropdown(industryDropdown, industry);
        selectCustomDropdown(sourceDropdown, source);
        selectCustomDropdown(priorityDropdown, priority);
        selectCustomDropdown(statusDropdown, status);
        
        // 3. Deal Information
        type(dealValueInput, dealValue);
        setDateByJS(expectedCloseDateInput, expectedCloseDate); 
        setDateByJS(followUpDateInput, followUpDate);
        
        // 4. Additional Details
        setCustomCheckbox(isQualifiedCheckbox, isQualified);
        setCustomCheckbox(emailOptInCheckbox, emailOptIn);
        type(notesTextarea, notes);
        
        // 5. Submit
        click(createLeadBtn);
    }
}
