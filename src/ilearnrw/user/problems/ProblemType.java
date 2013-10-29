package ilearnrw.user.problems;

/**
 * 
 * @author chris
 *
 * This enumerator represents the type of the problem that needs to be known by text-methods 
 * in order to be able to know what kind of search is needed inside the text for the specific error
 * eg. the Problem of suffixing for -ing must be encoded as an "ending error" 
 * so that the text-methods will only look at the end of each word for this error.
 *
 */
public enum ProblemType {
	CONTAINS, IS_NOUN_OR_ADJ_AND_ENDS_WITH, IS_NOUN_AND_ENDS_WITH, IS_ADJ_AND_ENDS_WITH, IS_VERB_AND_ENDS_WITH, 
	IS_PARTICIPLE_AND_ENDS_WITH, IS_NOUN_OR_ADJ_AND_STARTS_WITH, IS_ADJ_AND_STARTS_WITH, IS_VERB_AND_STARTS_WITH,
	TWO_SYL_WORD_INITIAL_PHONEME, TWO_SYL_WORD_INTERNAL_PHONEME, THREE_SYL_WORD_INITIAL_PHONEME, THREE_SYL_WORD_INTERNAL_PHONEME, 
	STARTS_WITH, ENDING, CONTAINS_PATTERN, CONTAINS_PHONEME, CONTAINS_SUFFIX, EQUALS, X;
}
