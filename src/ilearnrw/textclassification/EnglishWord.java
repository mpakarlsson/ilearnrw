package ilearnrw.textclassification;

import java.util.Vector;

import ilearnrw.prototype.application.Program;


public class EnglishWord extends Word {

	
	//We put inside only lower case words
	public EnglishWord(String word) {
		super.word = word.toLowerCase();
		checkType();
		syllabism();
		createCVForm();
		
		if(Program.getDictionary().containsKey(word)){
			frequency = Double.parseDouble(Program.getDictionary().get(word).get(2));
		} else 
			frequency = 5001;
	}

	
	protected void syllabism(){
		if(Program.getDictionary().containsKey(word)){
			Vector<String> data = Program.getDictionary().get(word);
			numSyllables = Integer.parseInt(data.get(1));
		} else {
			numSyllables = countVowels();
		}
	}
	
	protected void checkType(){
		//if (isVerb(word))...
		//...
		type = WordType.Unknown;
	}
	
	protected char upperCharToCV(char x){
		
		// The letter 'Y' can be both a vowel (pity) and a consonant(yes)
		// It is being treated as a vowel at the moment, but this needs to be addressed later
		
		switch (x){
			case 'A':
			case 'E':
			case 'I':
			case 'O':
			case 'U':
				return 'v';
			default :
				return 'c';
		}
	}
	
	protected void createCVForm(){
		cvForm = "";
		String temp = word.toUpperCase();
		for(int i=0; i<temp.length(); i++){
			char c = temp.charAt(i);
			cvForm += c;
		}
	}
	
	private int countVowels(){
		String temp = word.toUpperCase();
		char prevC = 0;
		int numVowels = 0, size = temp.length();
		for(char c : temp.toCharArray()){
			if(--size==0 && c == 'E'){
				if(numVowels == 0)
					numVowels++;
				break;
			}
			
			char currC = upperCharToCV(c);
			if(currC == 'v' && prevC != 'v'){
				numVowels++;
			}
			prevC = currC;
		}
		
		return numVowels;
	}
}
