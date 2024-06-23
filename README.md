# Git hub Link

https://github.com/swasthik09/InvoiceGenerator/tree/main

## Quickstart

1. Clone the repository
2. Open the project in your IDE: IntelliJ IDEA (recommended) or Eclipse or sts
3. Configure the database connection in `application.properties` file (check the [Database](#database) section below for more info)
4. Run the project by opening boot dashboard.
5. Open http://localhost:8080/ in your browser!

## Database

H2 or any SQL databases can be used as the database for this project. The database connection can be configured in the `application.properties` file, with the appropriate values for the following properties:
```properties
    spring.h2.console.enabled = true
    spring.datasource.url = h2:mem:testdb
    spring.h2.console.path = /h2-console
```
Having done that, you must create some base data in the database. You can do that by running the sql queries on the database. Check out Google for how to do that, because it depends on what tool you are using to access said database. 

## Endpoints

- http://localhost:8080/
- GET  http://localhost:8080/invoices  : Get all Invoices
- POST http://localhost:8080/invoices  : Create Invoice
- POST http://localhost:8080/invoices/1/payments :Make Payemnt by Invoice ID
- POST http://localhost:8080/invoices/process-overdue : Overdue Invoice for all pending invoices.

## Unit Testing Using Junit 5 and Mockito

1) Test method to create Invoice.
2) Test method to get all Invoices.
3) Test method to make payment using Invoice id
   - Payment full paid.
   - Payment partially paid.
   - Invoice does not exist.
4) Test Method to Make over due payments
   - test with no pending invoices.
   - test with peding invoices.

## Other tech used 

Spring MVC , Spring Data JPA etc
