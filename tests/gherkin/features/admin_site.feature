Feature: Administration interface

    Scenario: Retrieve the login page
        When i browse to "apps/login"
        Then i see a login form

    Scenario: Log in as admin
        When i browse to "apps/login"
        And i see a login form
        And set the form value "username" to "admin"
        And set the form value "pass" to "admin"
        When i press Submit
        Then i get redirected to "apps/panel"
