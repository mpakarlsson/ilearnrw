Feature: Annotate text

    Background:
        Given the username "joe_t"
        And the password "test"
        And i authenticate against api.ilearnrw.eu
        Then i should receive a token
        And i get the user details
        And i get the languagecode EN

    Scenario: Annotate a sentence
        When i annotate the text "Hello, this is a text to annotate."
        Then i get valid xhtml
        Then wordSet "<key>" exists

        Examples:
            | key               |
            | idCorrespondance  |

    Scenario: Annotate a sentence with the word Greater
        When i annotate the text "Some are greater that others! Greater."
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
