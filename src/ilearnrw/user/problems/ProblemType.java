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
	ENDING, PATTERN, CONTAINS_PHONEME, CONTAINS_SUFFIX, X;
}
