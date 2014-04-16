Feature: Classify greek text
    
    Background:
        Given the username "Giorgos_Expertidis"
        And the password "test"
        And i authenticate against api.ilearnrw.eu
        And i get the user details
        And i get the languagecode GR

    Scenario: Classify a sentence
        When i classify the text "Hello, my name is Admin."
        Then classification result "<key>" is <number>

        Examples:
            | key                   | number |
            | numberOfDistinctWords | 5      |
            | numberOfSentences     | 1      |
            | numberOfSyllables     | 5      |


