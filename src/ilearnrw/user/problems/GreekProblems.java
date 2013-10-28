package ilearnrw.user.problems;

import ilearnrw.user.LanguageCode;
import ilearnrw.user.IlearnException;
import ilearnrw.user.problems.Category;
import ilearnrw.user.problems.ProblemDefinition;
import ilearnrw.user.problems.ProblemDefinitionIndex;


	public class GreekProblems {
		private ProblemDefinitionIndex probsMatrix;

		public GreekProblems() {
			//three problems
			probsMatrix = new ProblemDefinitionIndex(9, LanguageCode.GR);	
			initialize();
		}
		
		public void getProblem(int idx){
			
		}
		
		public ProblemDefinitionIndex getAllProblems(){
			return probsMatrix;
		}

		public void initialize(){

			int problemIndexPosition, problemLength;
			
			Category cat = new Category("Syllable Division");
			//B.1 Syllable Division (20 problems)
			problemIndexPosition = 0;
			problemLength = 20;
			ProblemDefinition prob = new ProblemDefinition("Syllable Division", cat);
			probsMatrix.setProblemDefinition(prob, problemIndexPosition);
			probsMatrix.constructProblemRow(problemIndexPosition, problemLength);
			try {
				int i=0;
				probsMatrix.setProblemDescription(new String[]{"-cv-cv-"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"-cv-v-"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"-v-cv-"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"-cv-vc-"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"-vc-cv-"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"-cvc-cv-"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"-cv-ccv-"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"-ccv-cv-"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"-ccvc-cv-"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"-cv-cccv-"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"-v-cccv-"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"/ιά/","/ειά/"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"/ιά/","/ία/"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"/αί/","/αΐ/", "/εί/","/εΐ/","/οί/","/οΐ"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"/αί/","/άι/","/οί/","/όι/"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"/άι/","/όι/"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"/αι/","/αϊ/","/οι/","/οϊ/","/ει/","/εϊ/"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"/ου/","/οϋ/","/ού/","/οΰ/"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"/αυ/","/αϋ/","/αύ/","/αΰ/"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"/εύω/"}, problemIndexPosition, i++);
			} catch (IlearnException e) {
				e.printStackTrace();
			}
			

			cat = new Category("Phonemes");
			//B.2 Phonemes: Consonants (12 problems)
			problemIndexPosition = 1;
			problemLength = 12;
			prob = new ProblemDefinition("Consonants", cat);
			probsMatrix.setProblemDefinition(prob, problemIndexPosition);
			probsMatrix.constructProblemRow(problemIndexPosition, problemLength);
			try {
				int i=0;
				probsMatrix.setProblemDescription(new String[]{"/t/-/d/","/p/-/b/"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"/k/-/p/","/k/-/t/"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"/m/-/n/"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"/θ/-/ð/","/f/-/v/", "/χ/-/γ/"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"/k/-/γ/","/k/-/χ/"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"/s/-/z/"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"/l/-/r/"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"/ð/-/v/","/f/-/θ/","/f/-/v/","/θ/-/ð/"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"/kt/-/pt/"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"/ks/-/ps/","/ks/-/sk/","/ps/-/sp/"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"/ðr/-/θr/","/fr/-/χr/"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"/χθ/-/fθ/"}, problemIndexPosition, i++);
			} catch (IlearnException e) {
				e.printStackTrace();
			}

			//B.3 Phonemes: Vowels (6 problems)
			problemIndexPosition = 2;
			problemLength = 6;
			prob = new ProblemDefinition("Vowels", cat);
			probsMatrix.setProblemDefinition(prob, problemIndexPosition);
			probsMatrix.constructProblemRow(problemIndexPosition, problemLength);
			try {
				int i=0;
				probsMatrix.setProblemDescription(new String[]{"εϋ: /eɪ/"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"οϋ: /oɪ/"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"αϊ: /aɪ/"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"αη: /aɪ/"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"οΰ: /oi/"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"αΐ: /ai/"}, problemIndexPosition, i++);
			} catch (IlearnException e) {
				e.printStackTrace();
			}

			cat = new Category("Suffixing");
			//B.4 Suffixing: Derivational (13 problems)
			problemIndexPosition = 3;
			problemLength = 13;
			prob = new ProblemDefinition("Derivational", cat);
			probsMatrix.setProblemDefinition(prob, problemIndexPosition);
			probsMatrix.constructProblemRow(problemIndexPosition, problemLength);
			try {
				int i=0;
				probsMatrix.setProblemDescription(new String[]{"-άκι","-άκης","-άκος","-ίτσα",
						"-κας","-οπούλα","-όπουλο","-ούδι","-ούλα","-ούλης","-ούλης","-ούλα","ούλικο",
						"-ούτσικος","-ούτσικη","-ούτσικο"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"-άκλα","-άρα","-αράς","-αρόνα",
						"-αρος","-ούκλα"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"-άς","-έας","-ιάς","-δόρος","-άρης",
						"-ιάρης","-ιέρης", "-ιέρα","-ίτης","-ιώτης","-ίστας","-ιστής","-ίστρια",
						"-της","-τής","-τρια","-τισσα","-τζής","-τζού","-τίας","-τορας"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"-είο","-ιά","-ία","-ικο","-δικο"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"-έας","-ερό","-ιέρα","-τήρας","-τήρι","-τήριο",
						"-τρα","-της"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"-άδα","-εια","-ίλα","-ιλίκι","-μάρα","-οσύνη",
						"-ούρα","-σιά","-ξιά","-ότητα","-ύτητα"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"-ί","-ιά"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"-άλα","-εία","-ειά","-εια","-ση","-ξη","-ψη",
						"-ία","-σιά","-ψιά","-ξιά","-ματιά","-σιμο","-ξιμο","-ψιμο","-αμα","-ημα","-ωμα","-σμα",
						"-γμα","-μός","-ητό","-ατό","-κτό","-χτό","-φτό","πτό"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"-ικός","-ική","-ικό","-σιμος","-σιμη","-σιμο",
						"-ιάρης","-ιάρα","-άρικο","-ερός","-ερή","-ερό","-τός","-τή","-τό","-άτος","-άτη","-άτο",
						"-ινος","-ινη","-ινο","-ιακός","-ιακή","-ιακό","-ανός","-ανή","-ανό","-ούρης","-ούρα",
						"-ούρικο"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"-ίστικος","-ίστικη","-ίστικο","-ήσιος","-ήσια","-ήσιο",
						"-λέος","-λέα","-λέο","-αίος","-αία","-αίο","-ωπός","-ωπή","-ωπό","-ένιος","-ένια","-ένιο",
						"-τέος","-τέα","-τέο","-ώδης","-ώδες"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"-ίζω","-άζω","-ιάζω","-αίνω","-ώνω","-ύνω",
						"-εύω","-άρω"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"-βολώ","-λογώ","-ποιώ"}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{"-ειδής","-ειδές","-μελής","-μελές",
						"-ετής","-ετές"}, problemIndexPosition, i++);
			} catch (IlearnException e) {
				e.printStackTrace();
			}

			//B.5 Suffixing: Inflectional/Grammatical (17 problems)
			problemIndexPosition = 4;
			problemLength = 17;
			prob = new ProblemDefinition("Inflectional/Grammatical", cat);
			probsMatrix.setProblemDefinition(prob, problemIndexPosition);
			probsMatrix.constructProblemRow(problemIndexPosition, problemLength);
			try {
				int i=0;
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
			} catch (IlearnException e) {
				e.printStackTrace();
			}

			cat = new Category("Prefixing");
			//B.6 Prefixing (6 problems)
			problemIndexPosition = 5;
			problemLength = 6;
			prob = new ProblemDefinition("Prefixing", cat);
			probsMatrix.setProblemDefinition(prob, problemIndexPosition);
			probsMatrix.constructProblemRow(problemIndexPosition, problemLength);
			try {
				int i=0;
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
			} catch (IlearnException e) {
				e.printStackTrace();
			}


			cat = new Category("Grapheme/Phoneme Correspondence");
			//B.7 Grapheme/Phoneme Correspondence
			
			//Regular:Consonant clusters (20 problems)
			problemIndexPosition = 6;
			problemLength = 20;
			prob = new ProblemDefinition("Regular:Consonant clusters", cat);
			probsMatrix.setProblemDefinition(prob, problemIndexPosition);
			probsMatrix.constructProblemRow(problemIndexPosition, problemLength);
			try {
				int i=0;
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
			} catch (IlearnException e) {
				e.printStackTrace();
			}
			
			//Irregular (7 problems)
			problemIndexPosition = 7;
			problemLength = 7;
			prob = new ProblemDefinition("Irregular", cat);
			probsMatrix.setProblemDefinition(prob, problemIndexPosition);
			probsMatrix.constructProblemRow(problemIndexPosition, problemLength);
			try {
				int i=0;
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
			} catch (IlearnException e) {
				e.printStackTrace();
			}

			cat = new Category("Grammar/Function Words");
			//B.8 Grammar/Function Words (10 problems)
			problemIndexPosition = 8;
			problemLength = 10;
			prob = new ProblemDefinition("Grammar/Function Words", cat);
			probsMatrix.setProblemDefinition(prob, problemIndexPosition);
			probsMatrix.constructProblemRow(problemIndexPosition, problemLength);
			try {
				int i=0;
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
				probsMatrix.setProblemDescription(new String[]{""}, problemIndexPosition, i++);
			} catch (IlearnException e) {
				e.printStackTrace();
			}	
		}
		

	}