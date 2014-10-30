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
	// Greek types
	CONTAINS, IS_NOUN_OR_ADJ_AND_ENDS_WITH, IS_NOUN_AND_ENDS_WITH, IS_ADJ_AND_ENDS_WITH, 
	IS_VERB_AND_ENDS_WITH, IS_PARTICIPLE_AND_ENDS_WITH, IS_NOUN_OR_ADJ_AND_STARTS_WITH, 
	IS_ADJ_AND_STARTS_WITH, IS_VERB_AND_STARTS_WITH, TWO_SYL_WORD_INITIAL_PHONEME, 
	TWO_SYL_WORD_INTERNAL_PHONEME, THREE_SYL_WORD_INITIAL_PHONEME, 
	SOUND_SIMILARITY, VISUAL_SIMILARITY, CONTAINS_BUT_EXCLUDE_PREVIOUS_LETTERS,
	THREE_SYL_WORD_INTERNAL_PHONEME, STARTS_WITH, CONTAINS_PATTERN, 
	CONTAINS_PATTERN_OR_ENDS_WITH_EXTRA_CONSONANT, CONTAINS_PHONEME, EQUALS, 
	CONTAINS_LETTERS_ON_CONSEQUTIVE_SYLLABLES, CONTAINS_LETTERS_ON_SAME_SYLLABLES,
	IS_BIG_NOUN_OR_ADJ_AND_ENDS_WITH, IS_BIG_NOUN_AND_ENDS_WITH, IS_BIG_ADJ_AND_ENDS_WITH,
	IS_BIG_VERB_AND_ENDS_WITH,
	// English types
	SUFFIX_ADD, SUFFIX_DROP, SUFFIX_CHANGE, SUFFIX_DOUBLE, SUFFIX_STRESS_PATTERN,
	PREFIX, PREFIX_GROUP,
	LETTER_EQUALS_PHONEME, DIGRAPH_EQUALS_PHONEME, TRIGRAPH_EQUALS_PHONEME,
	PATTERN_EQUALS_PRONUNCIATION_CONTAINS, PATTERN_EQUALS_PRONUNCIATION_CONTAINS_MIXED, 
	PATTERN_EQUALS_PRONUNCIATION_ENDS, PATTERN_EQUALS_PRONUNCIATION_BEGINS,
	EQUALS_PRONUNCIATION_CONTAINS, EQUALS_PRONUNCIATION_BEGINS, EQUALS_PRONUNCIATION_ENDS,
	SYLLABLE_PATTERN, SUFFIX_PATTERN, SYLLABLE_COUNT, LETTER_PAIR,
	
	// All languages
	X;
}
