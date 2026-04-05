# UI Manual Test Cases: Lead Manager Flow (Login → Create Lead → List Lead)

## Test Environment & Pre-conditions
* **Application URL:** `https://v0-lead-manager-app.vercel.app`
* **Test Credentials:** `admin@company.com` / `Admin@123`
* **Global Pre-condition:** Tester is logged out before starting each test (unless otherwise specified).

---

## TC-01: Verify the complete End-to-End flow (Happy Path)
**Objective:** Ensure a user can successfully log in, create a complete lead, and view that lead in the system list.

**Sample Test Data:**
* **Basic Info:** Name: `Jane Doe`, Email: `email@gmail.com`, Phone: `555-019-8372`, Company: `Tech Corp`, Title: `CTO`
* **Classification:** Industry: `Technology`, Source: `Website`, Priority: `Medium`, Status: `New`
* **Deal Info:** Value: `50000`, Expected Close: `12/31/2026`, Follow-up: `10/15/2026`
* **Details:** Qualified: `Checked`, Opt-in: `Checked`, Notes: `E2E manual test execution.`

| Step | Action | Expected Result | Flow Stage |
| :--- | :--- | :--- | :--- |
| **1** | Navigate to the application URL. | The Login screen is displayed. | Login |
| **2** | Enter valid credentials and click Login. | User is authenticated and redirected to the Dashboard/List view. | Login |
| **3** | Click the "Create New Lead" button. | The Create Lead form opens. | Create Lead |
| **4** | Fill out Basic Information using the sample test data. | Data is accepted in the input fields. | Create Lead |
| **5** | Select Classification dropdowns using the sample test data. | Dropdowns open, allow selection, and display the chosen text. | Create Lead |
| **6** | Enter Deal Value and select the dates using the sample test data. | Dates format correctly (MM/DD/YYYY). | Create Lead |
| **7** | Toggle the Qualified/Email Opt-in checkboxes and add Notes. | Toggles visually indicate active state. | Create Lead |
| **8** | Click the "Create Lead" submit button. | Form submits successfully. User is redirected back to the Dashboard. | Create Lead |
| **9** | Observe the Leads List table on the Dashboard. | The newly created lead (`Jane Doe`) appears at the top of the list, displaying the correct Email and Status. | List Lead |

---

## TC-02: Verify flow interruption at Login (Negative Path)
**Objective:** Ensure the flow cannot begin if the user fails authentication.

**Sample Test Data:**
* **Invalid Login:** `wrong.user@company.com` / `BadPassword1!`

| Step | Action | Expected Result | Flow Stage |
| :--- | :--- | :--- | :--- |
| **1** | Navigate to the application URL. | The Login screen is displayed. | Login |
| **2** | Enter the invalid Email and/or invalid Password. | Input is accepted. | Login |
| **3** | Click the Login button. | Login fails. An appropriate error message is displayed. User is NOT redirected to the Dashboard. | Login |

---

## TC-03: Verify flow interruption during Create Lead validation (Negative Path)
**Objective:** Ensure the system prevents a user from progressing to the "List Lead" stage if the Create Lead form is missing mandatory data.

**Sample Test Data:**
* **Missing Info:** Name: `[Blank]`, Email: `[Blank]`

| Step | Action | Expected Result | Flow Stage |
| :--- | :--- | :--- | :--- |
| **1** | Log in with valid credentials. | User is redirected to the Dashboard. | Login |
| **2** | Click the "Create New Lead" button. | The Create Lead form opens. | Create Lead |
| **3** | Leave mandatory fields (Name, Email) completely blank, but fill out the rest of the form. | Fields accept input. | Create Lead |
| **4** | Click the "Create Lead" submit button. | The form does NOT submit. Inline validation errors appear under Name and Email. User is NOT redirected back to the list. | Create Lead |

---

## TC-04: Verify flow cancellation (Alternative Path)
**Objective:** Ensure a user can safely abort the flow during the creation step without altering the List Lead view.

**Sample Test Data:**
* **Partial Data:** Name: `Canceled Lead`, Deal Value: `99999`

| Step | Action | Expected Result | Flow Stage |
| :--- | :--- | :--- | :--- |
| **1** | Log in with valid credentials. | User is redirected to the Dashboard. | Login |
| **2** | Note the top lead currently displayed in the list. | Data is noted for comparison. | List Lead |
| **3** | Click the "Create New Lead" button. | The Create Lead form opens. | Create Lead |
| **4** | Partially fill out the form using the partial test data. | Data is accepted. | Create Lead |
| **5** | Click the "Cancel" button instead of Submit. | User is safely redirected back to the Dashboard. | Create Lead |
| **6** | Observe the Leads List table. | The list remains unchanged from Step 2. `Canceled Lead` does NOT appear in the list. | List Lead |

---

## TC-05: Verify data integrity in the List Lead view (Data Mapping)
**Objective:** Ensure that the data entered in the "Create Lead" form is mapped perfectly to the columns displayed in the "List Lead" dashboard.

**Sample Test Data:**
* **Lead Info:** Name: `Integrity Check`, Email: `data@test.com`, Status: `Qualified`, Deal Value: `75000`

| Step | Action | Expected Result | Flow Stage |
| :--- | :--- | :--- | :--- |
| **1** | Log in and navigate to Create Lead. | Form opens. | Login |
| **2** | Create a lead using the specific sample data above. | Lead is created. | Create Lead |
| **3** | Locate `Integrity Check` in the Dashboard list. | Lead is found in the table. | List Lead |
| **4** | Verify every visible column for that row. | The list displays `Integrity Check`, `data@test.com`, the `Qualified` status badge, and `$75,000` exactly as entered. No data is truncated or mismatched. | List Lead |

---

## TC-06: Verify duplicate lead handling (Business Logic)
**Objective:** Determine how the system handles a user attempting to create a lead that already exists in the list.

**Sample Test Data:**
* **Target Email:** `existing@company.com`

| Step | Action | Expected Result | Flow Stage |
| :--- | :--- | :--- | :--- |
| **1** | Log in and create a lead with the email `existing@company.com`. | Lead is created and appears in the list. | Create / List |
| **2** | Click "Create New Lead" again. | Form opens. | Create Lead |
| **3** | Fill out the form using `existing@company.com` again. | Data is accepted. | Create Lead |
| **4** | Click "Create Lead". | System gracefully handles the duplicate (e.g., displays an "Email already exists" error or merges the record). It should not crash or silently create a corrupt record. | Create Lead |

---

## TC-07: Verify email format validation (Input Boundaries)
**Objective:** Ensure the system does not allow garbage data in the email field to pollute the Lead List.

**Sample Test Data:**
* **Invalid Emails:** `plainaddress`, `#@%^%#$@#$@#.com`, `@domain.com`, `Joe Smith <email@domain.com>`

| Step | Action | Expected Result | Flow Stage |
| :--- | :--- | :--- | :--- |
| **1** | Log in and navigate to Create Lead. | Form opens. | Login |
| **2** | Enter an invalid email format from the sample data. | Input is typed. | Create Lead |
| **3** | Fill out remaining mandatory fields and click Submit. | Form submission is blocked. An inline error (e.g., "Please enter a valid email address") is displayed. | Create Lead |

---

## TC-08: Verify Date picker logic (Business Logic)
**Objective:** Ensure the Expected Close Date and Follow-up Date fields handle illogical chronological data correctly.

**Sample Test Data:**
* **Expected Close Date:** `01/01/2000` (A date deep in the past)

| Step | Action | Expected Result | Flow Stage |
| :--- | :--- | :--- | :--- |
| **1** | Log in and navigate to Create Lead. | Form opens. | Login |
| **2** | Fill out basic mandatory fields. | Fields accept data. | Create Lead |
| **3** | Enter `01/01/2000` as the Expected Close Date. | Date is entered. | Create Lead |
| **4** | Click Submit. | System flags this as a validation error (e.g., "Close date cannot be in the past") preventing bad data from reaching the Lead List. | Create Lead |

---

## TC-09: Verify flow enforcement via direct URL (Security/Routing)
**Objective:** Ensure a user cannot bypass the Login stage and jump straight to Create Lead or List Lead.

**Sample Test Data:**
* **Target URLs:** `https://v0-lead-manager-app.vercel.app/dashboard` or `/create`

| Step | Action | Expected Result | Flow Stage |
| :--- | :--- | :--- | :--- |
| **1** | Ensure you are completely logged out (clear cookies/cache or use Incognito). | User is unauthenticated. | Login |
| **2** | Paste the direct URL for the Dashboard or Create Lead page into the browser address bar and hit Enter. | Navigation is attempted. | Create / List |
| **3** | Observe the application's response. | The application intercepts the request and forcefully redirects the user back to the Login screen. No lead data or forms are exposed. | Login |
