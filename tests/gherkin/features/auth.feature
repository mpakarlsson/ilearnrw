Feature: Athenticate user on server
    In order to log a user in a username
    and password must be supplied to the auth service.

    Scenario: Authenticate users
        Given the username "<username>"
        And the password "<password>"
        When i authenticate against api.ilearnrw.eu
        Then i should receive a token

        Examples:
            | username              | password |
            | admin                 | admin    |
            | Giorgos_Expertidis    | test     |
