package ilearnrw.games;

import ilearnrw.textclassification.Classifier;
import ilearnrw.textclassification.Text;
import ilearnrw.user.LanguageCode;
import ilearnrw.user.User;

public class ClassificationTests {

	public static void main(String args[]){
		User user = new User(1);
		user.getProfile().getProblemsMatrix().loadTestGreekProblems();
		//System.out.println(user.getProfile().getSeveritiesToProblemsMatrix().toString());s
		Text t = new Text("Επαναλαμβάνω ότι αυτά πρέπει να τα ξεχάσει, δηλαδή να μην ποντάρει πάνω τους. " +
				"Μακάρι π.χ για τον Ολυμπιακό ο Μήτρογλου να βάλει πέντε γκολ: όμως πριν ξεκινήσει το ματς " +
				"δεν μπορεί η ομάδα να ποντάρει ότι θα καθαρίσει πάλι ο ένας (και σούπερ φορμαρισμένος) σκόρερ " +
				"της - πρέπει κάτι να κάνουν και οι άλλοι.", LanguageCode.GR);
		//System.out.println(t.getSentences()[0].getWord(0));
		Classifier cls = new Classifier(user.getProfile().getProblemsMatrix(), t);
		cls.test();
	}
}
