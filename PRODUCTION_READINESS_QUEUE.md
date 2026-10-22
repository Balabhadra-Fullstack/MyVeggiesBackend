# Production Readiness Queue

This is the working order for getting the Veggies backend ready for production. Complete and verify each item before moving to the next. Security and request validation are intentionally scheduled last, as agreed. Do not expose the application publicly until those final items are complete.

## Current Baseline

- The backend has controllers and services for owners, vegetables/categories, customers/suppliers, purchases/sales, payments, and crate entries/returns.
- There is no Shell implementation in the current workspace. Confirm whether Shell is out of scope or needs to be restored before treating the feature list as complete.
- The existing suite has five tests: four limited service tests and one application-context test. This confirms a basic compile/startup path, not that all API workflows work correctly.
- No code changes have been made as part of this queue review.

## Queue

### 1. Define the feature scope

- [ ] Confirm the modules and API workflows the product must support.
- [ ] Decide whether Shell is excluded or needs to be implemented; no Shell source files are present now.
- [ ] Record expected behavior for sale crates, payment edits, and delete operations before changing those flows.

**Done when:** the required feature list and business rules are agreed, including how crate activity relates to a sale.

### 2. Fix sale and crate consistency

- [ ] Decide whether a sale owns its crate-entry record or crate entries are an independent ledger.
- [ ] Update the implementation to follow that rule. Currently `addSale` creates a separate crate entry, `updateSale` can create another entry instead of reconciling the previous one, and `deleteSale` does not remove or adjust a linked entry.
- [ ] Cover create, repeated update, crate-count change to zero, customer/date change, and delete behavior with tests.

**Done when:** repeating or editing a sale cannot silently duplicate or leave stale crate activity, and the agreed business rules are tested.

### 3. Verify core business workflows

- [ ] Add focused tests for purchase and sale create/update/delete, item replacement, and amount calculations.
- [ ] Add tests for customer/supplier payments and crate entry/return CRUD, including missing related records.
- [ ] Verify search, date filters, customer/supplier history, last-three results, and crate summaries.
- [ ] Test API request/response behavior against a test database; do not use the persistent local H2 data for destructive checks.

**Done when:** each required workflow has success and failure-path coverage, and the full suite passes.

### 4. Make API failures consistent

- [ ] Expand the global exception handling beyond customer/supplier not-found cases. Other missing records currently commonly throw `RuntimeException` and may surface as server errors.
- [ ] Define consistent status codes and response bodies for missing records, invalid state, and unexpected failures.
- [ ] Add controller tests for those responses.

**Done when:** expected client errors do not become generic 500 responses and error bodies are consistent and do not expose internals.

### 5. Review API response boundaries

- [ ] Review endpoints that return JPA entities directly, especially purchases, sales, customers, suppliers, vegetables, and payments.
- [ ] Check for recursive/lazy serialization, unwanted fields, and response shape changes that could break the frontend.
- [ ] Keep or introduce response DTOs only where they solve a concrete exposure or serialization problem; no blanket DTO rewrite is required.

**Done when:** API responses are stable, intentional, and covered by representative serialization tests.

### 6. Prepare production persistence

- [ ] Select and configure the production database (for example, PostgreSQL) with connection details supplied outside source control.
- [ ] Add schema migrations and use schema validation in production instead of `ddl-auto=update`.
- [ ] Disable SQL statement logging in production and verify profiles do not accidentally use the local H2 database.
- [ ] Document backup and restore procedures and test a restore.

Current settings are in `src/main/resources/application.properties`; they use a local H2 file, `ddl-auto=update`, and SQL logging.

**Done when:** a clean deployment can initialize/upgrade its schema through migrations and the database can be restored from backup.

### 7. Add release operations checks

- [ ] Define production profiles and deployment configuration.
- [ ] Add health/readiness monitoring and useful, privacy-conscious application logs.
- [ ] Verify startup, shutdown, backup, and recovery procedures in a production-like environment.

**Done when:** operators can tell whether the service is healthy and recover it without relying on a developer's local setup.

### 8. Final security and validation phase

- [ ] Add request DTO constraints and apply validation at controller boundaries; test malformed, missing, zero, and negative values against the agreed business rules.
- [ ] Add authentication and authorization, password hashing, and least-privilege access rules.
- [ ] Restrict CORS to the actual frontend origins and verify HTTPS/proxy configuration for deployment.
- [ ] Add security tests for unauthenticated access, role restrictions, and sensitive response fields.

**Done when:** invalid input is rejected consistently, protected endpoints require the intended permissions, and security tests pass. These items are intentionally last in the task queue, but must be finished before public deployment.

## Review Notes

The queue records findings from a source and test-suite review, not proof that every endpoint has been exercised. Revisit the queue as tests reveal behavior-specific defects; avoid marking a feature complete based only on successful compilation or application startup.