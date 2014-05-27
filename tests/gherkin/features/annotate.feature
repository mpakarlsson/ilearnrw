Feature: Annotate text

    Background:
        Given the username "Giorgos_Expertidis"
        And the password "test"
        And i authenticate against api.ilearnrw.eu
        Then i should receive a token
        And i get the user details
        And i get the languagecode GR

    Scenario: Annotate a sentence
        When i annotate the text "Hello, this is a text to annotate."
        Then i get valid xhtml
        Then wordSet "<key>" exists

        Examples:
            | key               |
            | idCorrespondance  |

    Scenario: Annotate longer text
        When i annotate the text in <data/short_en.txt>
        Then i get valid xhtml
        Then wordSet "<key>" exists

        Examples:
            | key               |
            | idCorrespondance  |

    Scenario: Annotate really long text
        When i annotate the text in <data/long_en.txt>
        Then i get valid xhtml
        Then wordSet "<key>" exists

        Examples:
            | key               |
            | idCorrespondance  |
