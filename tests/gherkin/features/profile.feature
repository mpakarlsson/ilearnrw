Feature: Fetch profile
    Fetch profile

    Scenario: Fetch profile
        Given the username "<username>"
        And the password "<password>"
        When i authenticate against api.ilearnrw.eu
        Then i should receive a token
        And i get the user details
        When i fetch the profile i get valid json

        Examples:
            | username              | password |
            | admin                 | admin    |
            | joe_t                 | test     |
            | Giorgos_Expertidis    | test     |
