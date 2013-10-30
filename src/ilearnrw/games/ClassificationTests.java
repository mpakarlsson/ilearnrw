package ilearnrw.games;

import ilearnrw.textclassification.Classifier;
import ilearnrw.textclassification.Sentence;
import ilearnrw.textclassification.Word;
import ilearnrw.textclassification.Text;
import ilearnrw.user.LanguageCode;
import ilearnrw.user.User;
import ilearnrw.user.problems.GreekProblems;

public class ClassificationTests {

	public static void main(String args[]){
		User user = new User(1);
		GreekProblems greek = new GreekProblems();
		user.getProfile().getSeveritiesToProblemsMatrix().loadTestGreekProblems();
		//System.out.println(user.getProfile().getSeveritiesToProblemsMatrix().toString());s
		Text t = new Text("Σεϋχέλλες τραγούδι αντιλαϊκός", LanguageCode.GR);
		//System.out.println(t.getSentences()[0].getWord(0));
		Classifier cls = new Classifier(user, t);
		cls.test();
	}
}
