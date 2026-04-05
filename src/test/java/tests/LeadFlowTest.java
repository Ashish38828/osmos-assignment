package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CreateLeadPage;
import pages.DashboardPage;
import pages.LoginPage;

public class LeadFlowTest extends BaseTest {

    @Test
    public void testSuccessfulLoginAndCreateLead() {
        // Initialize Page Objects with the Playwright Page instance
        LoginPage loginPage = new LoginPage(page);
        DashboardPage dashboardPage = new DashboardPage(page);
        CreateLeadPage createLeadPage = new CreateLeadPage(page);

        // --- Test Data ---
        String targetUrl = "https://v0-lead-manager-app.vercel.app";
        String testEmail = "admin@company.com";
        String testPassword = "Admin@123";
        
        String leadName = "Jane Doe";
        String leadEmail = "email@gmail.com";
        String leadPhone = "555-019-8372";
        String leadCompany = "Tech Corp";
        String leadJobTitle = "Chief Technology Officer";
        
        String leadIndustry = "Technology";
        String leadSource = "Website";
        String leadPriority = "Medium";
        String leadStatus = "New";
        
        String dealValue = "50000";
        String expectedCloseDate = "12/31/2026"; 
        String followUpDate = "10/15/2026";
        
        boolean isQualified = true;
        boolean emailOptIn = true;
        String notes = "Automated test lead creation notes with Playwright.";

        // --- Test Execution Steps ---

        // Step 1: Login
        loginPage.navigateTo(targetUrl);
        loginPage.login(testEmail, testPassword);

        // Step 2: Navigate to Create Lead
        dashboardPage.goToCreateLead();

        // Step 3: Fill out the entire form and submit
        createLeadPage.fillAndSubmitLead(
            leadName, leadEmail, leadPhone, leadCompany, leadJobTitle,
            leadIndustry, leadSource, leadPriority, leadStatus, 
            dealValue, expectedCloseDate, followUpDate,
            isQualified, emailOptIn, notes
        );

        // Step 4: Verify the newly created lead appears at the top of the Dashboard list
        String firstLead = dashboardPage.getFirstLeadName();
        Assert.assertEquals(firstLead, leadName, "The newly created lead should appear at the top of the dashboard list");
    }
}
