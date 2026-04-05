# Manual API Test Cases: Lead Manager Flow

## Test Environment & Details
* **API Base URL:** `https://v0-lead-manager-app.vercel.app/api`
* **Auth Requirement:** All protected endpoints require a JWT token in the request header formatted as `Authorization: Bearer {token}`.
* **Test Scope:** Login, Create Lead, and Get Leads.
* **Testing Focus:** Successful requests, invalid inputs, authorization cases, and basic error handling.

---

## TC-01: Verify the complete End-to-End API flow (Successful Requests)
**Objective:** Ensure a client can successfully authenticate, receive a token, use that token to create a lead, and fetch the paginated list of leads.

**Sample Test Data:**
* **Login Payload (`POST /api/login`)**:

    {
      "email": "admin@company.com",
      "password": "Admin@123"
    }

* **Create Lead Payload (`POST /api/leads`)**:

    {
      "name": "Name",
      "email": "email@gmail.com",
      "priority": "Medium",
      "status": "New",
      "isQualified": false,
      "emailOptin": false,
      "notes": "Test note"
    }

| Step | Endpoint & Method | Action | Expected Result |
| :--- | :--- | :--- | :--- |
| **1** | `POST /api/login` | Send the Login payload with `Content-Type: application/json`. | **Status:** `200`.<br>**Response:** Returns `success: true`, the user's `email`, and a `token` (e.g., `token_1768298330279_px698b`). |
| **2** | `POST /api/leads` | Add `Authorization: Bearer {token}` and `Content-Type: application/json` to headers. Send Create Lead Payload. | **Status:** `201 Created` or `200 OK`.<br>**Response:** Returns the created lead data indicating successful database insertion. |
| **3** | `GET /api/leads` | Send a GET request with `Authorization: Bearer {token}`. | **Status:** `200 OK`.<br>**Response:** Returns a paginated list of leads. The lead created in Step 2 is present in the dataset. |

---

## TC-02: Verify Authorization-related cases (Negative Path)
**Objective:** Ensure protected endpoints reject requests lacking valid authentication tokens.

| Step | Endpoint & Method | Action | Expected Result |
| :--- | :--- | :--- | :--- |
| **1** | `GET /api/leads` | Send the GET request with NO `Authorization` header. | **Status:** `401 Unauthorized` or `403 Forbidden`.<br>**Response:** Access denied; paginated list is not returned. |
| **2** | `POST /api/leads` | Send the Create Lead request using an invalid or expired token format (e.g., `Authorization: Bearer bad_token_123`). | **Status:** `401 Unauthorized` or `403 Forbidden`.<br>**Response:** Lead creation is blocked. |

---

## TC-03: Verify Invalid Inputs for Login (Basic Error Handling)
**Objective:** Ensure the system properly handles incorrect authentication attempts.

**Sample Test Data:**
* **Payload:**

    {
      "email": "wrong@company.com",
      "password": "BadPassword123"
    }

| Step | Endpoint & Method | Action | Expected Result |
| :--- | :--- | :--- | :--- |
| **1** | `POST /api/login` | Send the payload with invalid credentials. | **Status:** `401 Unauthorized` or `400 Bad Request`.<br>**Response:** Returns `success: false` (or omits the success flag) and does NOT return a `token`. Contains an appropriate error message. |

---

## TC-04: Verify Invalid Inputs for Create Lead (Basic Error Handling)
**Objective:** Ensure the Create Lead API enforces payload validation and handles malformed data.

**Sample Test Data:**
* **Payload (Missing Name and Email):**

    {
      "priority": "Medium",
      "status": "New",
      "isQualified": false
    }

| Step | Endpoint & Method | Action | Expected Result |
| :--- | :--- | :--- | :--- |
| **1** | `POST /api/login` | Authenticate using `admin@company.com` and `Admin@123`. | Returns a valid JWT token. |
| **2** | `POST /api/leads` | Send the incomplete payload using the valid token. | **Status:** `400 Bad Request` or `422 Unprocessable Entity`.<br>**Response:** Lead is not created. Response explicitly indicates validation errors for missing mandatory fields (e.g., name, email). |

---

## TC-05: Verify Pagination for Get Leads API (Successful Requests)
**Objective:** Ensure the `GET /api/leads` endpoint correctly handles pagination parameters to limit the number of records returned.

**Sample Test Data:**
* **Query Parameters:** `?page=1&limit=5`

| Step | Endpoint & Method | Action | Expected Result |
| :--- | :--- | :--- | :--- |
| **1** | `POST /api/login` | Authenticate and grab token. | Token received. |
| **2** | `GET /api/leads?page=1&limit=5` | Send GET request with token and pagination query parameters. | **Status:** `200 OK`.<br>**Response:** Returns a maximum of 5 lead records. The response payload should ideally include pagination metadata (e.g., `totalRecords`, `currentPage`, `totalPages`). |

---

## TC-06: Verify Invalid Data Types in Create Lead (Invalid Inputs)
**Objective:** Ensure the API strictly enforces data types and rejects payloads where boolean fields or enums are passed incorrectly.

**Sample Test Data:**
* **Payload (String passed instead of Boolean):**

    {
      "name": "Type Test",
      "email": "type@test.com",
      "priority": "Medium",
      "status": "New",
      "isQualified": "yes", 
      "emailOptin": "true",
      "notes": "Testing data types"
    }

| Step | Endpoint & Method | Action | Expected Result |
| :--- | :--- | :--- | :--- |
| **1** | `POST /api/login` | Authenticate and grab token. | Token received. |
| **2** | `POST /api/leads` | Send the payload with invalid data types using the valid token. | **Status:** `400 Bad Request`.<br>**Response:** Returns an error indicating that `isQualified` and `emailOptin` must be boolean values, not strings. |

---

## TC-07: Verify Duplicate Lead Creation (Basic Error Handling)
**Objective:** Determine how the API handles a request to create a lead using an email address that already exists in the system.

**Sample Test Data:**
* **Payload:** Send the exact same payload used in TC-01 (containing `email@gmail.com`).

| Step | Endpoint & Method | Action | Expected Result |
| :--- | :--- | :--- | :--- |
| **1** | `POST /api/login` | Authenticate and grab token. | Token received. |
| **2** | `POST /api/leads` | Send the payload to create the lead. | **Status:** `201 Created` or `200 OK`. |
| **3** | `POST /api/leads` | Send the exact same payload a second time. | **Status:** `409 Conflict` (or `400 Bad Request`).<br>**Response:** The system should reject the duplicate and return an error message such as "A lead with this email already exists". |

---

## TC-08: Verify HTTP Method Restrictions (Basic Error Handling)
**Objective:** Ensure the API endpoints reject incorrect HTTP methods, which is a standard security and routing check.

| Step | Endpoint & Method | Action | Expected Result |
| :--- | :--- | :--- | :--- |
| **1** | `POST /api/login` | Authenticate and grab token. | Token received. |
| **2** | `PUT /api/leads` | Send a `PUT` request (instead of `POST` or `GET`) to the `/api/leads` endpoint using the valid token. | **Status:** `405 Method Not Allowed` or `404 Not Found`.<br>**Response:** The API correctly routes the error and does not crash or expose stack traces. |
