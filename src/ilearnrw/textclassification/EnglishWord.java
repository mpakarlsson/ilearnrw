package ilearnrw.textclassification;


public class EnglishWord extends Word {

	
	//We put inside only lower case words
	public EnglishWord(String word) {
		super.word = word.toLowerCase();
		//super.checkType();
		this.syllabism();
		this.createCVForm();
	}

	
	protected void syllabism(){
		//somehow...
	}
	
	protected char upperCharToCV(char x){
		switch (x){
			case 'b':
			case 'c':
			case 'd':
			case 'f':
			case 'g':
			//etc...
				return 'c';
			default :
				return 'v';
		}
	}
	
	protected void createCVForm(){
		//somehow whith the help of lowererCharToCV
		cvForm = "";
	}
}
