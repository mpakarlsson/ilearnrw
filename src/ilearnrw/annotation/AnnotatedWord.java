package ilearnrw.annotation;

import ilearnrw.textclassification.Word;

public class AnnotatedWord extends Word {

    public AnnotatedWord(Word w) {
	super();
	super.word= w.getWord();
	super.wordUnmodified = w.getWordUnmodified();
	super.type = w.getType();
	super.syllables = w.getSyllables();
	super.cvForm = w.getCVForm();
	super.phonetics = w.getPhonetics();
	super.numSyllables = w.getNumberOfSyllables();
	super.languageCode = w.getLanguageCode();
	super.frequency = w.getFrequency();
	super.graphemesPhonemes = w.getGraphemesPhonemes();
    }
}
