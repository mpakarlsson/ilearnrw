package ilearnrw.user.problems;

import ilearnrw.prototype.application.JsonHandler;
import ilearnrw.user.problems.Category;
import ilearnrw.user.problems.ProblemDefinition;
import ilearnrw.user.problems.ProblemDefinitionIndex;
import ilearnrw.utils.LanguageCode;


	public class GreekProblems extends Problems{

		public GreekProblems() {
			JsonHandler handler = new JsonHandler("data/problem_definitions_greece.json", true);
			problemDefinitionIndex = (ProblemDefinitionIndex) handler.fromJson(ProblemDefinitionIndex.class);
			System.err.println("problemDefinitionIndex");
			//three problems
			//problemDefinitionIndex = new ProblemDefinitionIndex(8, LanguageCode.GR);	
			//initialize();

		}
		
		public void initialize(){
			
			int problemIndexPosition, problemLength;

			Category cat = new Category("Syllable Division");
			//B.1 Syllable Division (20 problems)
			problemIndexPosition = 0;
			problemLength = 20;
			ProblemDefinition prob = new ProblemDefinition("Syllable Division", cat);
			problemDefinitionIndex.setProblemDefinition(prob, problemIndexPosition);
			problemDefinitionIndex.constructProblemRow(problemIndexPosition, problemLength);
			{
				int i=0;
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS_PATTERN, 
						new String[]{"-cv-cv-"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS_PATTERN, 
						new String[]{"-cv-v-"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS_PATTERN, 
						new String[]{"-v-cv-"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS_PATTERN, 
						new String[]{"-cv-vc-"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS_PATTERN_OR_ENDS_WITH_EXTRA_CONSONANT, 
						new String[]{"-vc-cv-"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS_PATTERN_OR_ENDS_WITH_EXTRA_CONSONANT, 
						new String[]{"-cvc-cv-"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS_PATTERN_OR_ENDS_WITH_EXTRA_CONSONANT, 
						new String[]{"-cv-ccv-"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS_PATTERN_OR_ENDS_WITH_EXTRA_CONSONANT, 
						new String[]{"-ccv-cv-"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS_PATTERN_OR_ENDS_WITH_EXTRA_CONSONANT, 
						new String[]{"-ccvc-cv-"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS_PATTERN_OR_ENDS_WITH_EXTRA_CONSONANT, 
						new String[]{"-cv-cccv-"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS_PATTERN_OR_ENDS_WITH_EXTRA_CONSONANT, 
						new String[]{"-v-cccv-"}, problemIndexPosition, i++);
				
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS, 
						new String[]{"ιά","ειά"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS, 
						new String[]{"ία"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS, 
						new String[]{"αΐ", "εΐ","οΐ"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS_LETTERS_ON_CONSEQUTIVE_SYLLABLES, 
						new String[]{"άι","όι"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS_LETTERS_ON_SAME_SYLLABLES, 
						new String[]{"άι","όι"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS, 
						new String[]{"αϊ","οϊ","εϊ"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS, 
						new String[]{"οϋ","οΰ"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS, 
						new String[]{"αυ","αϋ","αύ","αΰ"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS, 
						new String[]{"εύω", "εύεις", "εύει", "εύουμε", "εύετε", "εύουν", 
						"ευα", "ευες", "ευαν", "ευαμε", "ευατε", "ευαν"}, problemIndexPosition, i++);
			}
			

			cat = new Category("Phonemes");
			//B.2 Phonemes: Consonants (12 problems)
			problemIndexPosition = 1;
			problemLength = 12;
			prob = new ProblemDefinition("Consonants", cat);
			problemDefinitionIndex.setProblemDefinition(prob, problemIndexPosition);
			problemDefinitionIndex.constructProblemRow(problemIndexPosition, problemLength);
			{
				int i=0;
				problemDefinitionIndex.setProblemDescription(ProblemType.SOUND_SIMILARITY, 
						new String[]{"t-d","p-b"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.SOUND_SIMILARITY, 
						new String[]{"k-p","k-t"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.SOUND_SIMILARITY, 
						new String[]{"m-n"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.SOUND_SIMILARITY, 
						new String[]{"θ-ð","f-v", "χ-γ"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.SOUND_SIMILARITY, 
						new String[]{"k-γ","k-χ"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.SOUND_SIMILARITY, 
						new String[]{"s-z"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.SOUND_SIMILARITY, 
						new String[]{"l-r"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.SOUND_SIMILARITY, 
						new String[]{"ð-v","f-θ","f-v","θ-ð"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.SOUND_SIMILARITY, 
						new String[]{"kt-pt"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.SOUND_SIMILARITY, 
						new String[]{"ks-ps","ks-sk","ps-sp"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.SOUND_SIMILARITY, 
						new String[]{"ðr-θr","fr-χr"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.SOUND_SIMILARITY, 
						new String[]{"χθ-fθ"}, problemIndexPosition, i++);
			}

			//B.3 Phonemes: Vowels (6 problems)
			problemIndexPosition = 2;
			problemLength = 6;
			prob = new ProblemDefinition("Vowels", cat);
			problemDefinitionIndex.setProblemDefinition(prob, problemIndexPosition);
			problemDefinitionIndex.constructProblemRow(problemIndexPosition, problemLength);
			{
				int i=0;
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS, 
						//new String[]{"εϋ: /eɪ/"}, problemIndexPosition, i++);
						new String[]{"εϋ"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS, 
						//new String[]{"οϋ: /oɪ/"}, problemIndexPosition, i++);
						new String[]{"οϋ"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS, 
						//new String[]{"αϊ: /aɪ/"}, problemIndexPosition, i++);
						new String[]{"αϊ"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS, 
						//new String[]{"αη: /aɪ/"}, problemIndexPosition, i++);
						new String[]{"αη"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS, 
						//new String[]{"οΰ: /oi/"}, problemIndexPosition, i++);
						new String[]{"οΰ"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS, 
						//new String[]{"αΐ: /ai/"}, problemIndexPosition, i++);
						new String[]{"αΐ"}, problemIndexPosition, i++);
			}

			cat = new Category("Suffixing");
			//B.4 Suffixing: Derivational (13 problems)
			problemIndexPosition = 3;
			problemLength = 13;
			prob = new ProblemDefinition("Derivational", cat);
			problemDefinitionIndex.setProblemDefinition(prob, problemIndexPosition);
			problemDefinitionIndex.constructProblemRow(problemIndexPosition, problemLength);
			{
				int i=0;
                problemDefinitionIndex.setProblemDescription(ProblemType.IS_BIG_NOUN_OR_ADJ_AND_ENDS_WITH,
                                new String[]{"άκι","άκης","άκος","ίτσα",
                                "κας","οπούλα","όπουλο","ούδι","ούλα","ούλης","ούλης","ούλα","ούλικο",
                                "ούτσικος","ούτσικη","ούτσικο"}, problemIndexPosition, i++);
                problemDefinitionIndex.setProblemDescription(ProblemType.IS_BIG_NOUN_OR_ADJ_AND_ENDS_WITH,
                                new String[]{"άκλα","άρα","αράς","αρόνα",
                                "αρος","ούκλα"}, problemIndexPosition, i++);
                problemDefinitionIndex.setProblemDescription(ProblemType.IS_BIG_NOUN_AND_ENDS_WITH,
                                new String[]{"έας","ιάς","δόρος","άρης",//"άς",
                                "ιάρης","ιέρης", "ιέρα","ίτης","ιώτης","ίστας","ιστής","ίστρια",
                                "της","τής","τρια","τισσα","τζής","τζού","τίας","τορας"}, problemIndexPosition, i++);
                problemDefinitionIndex.setProblemDescription(ProblemType.IS_BIG_NOUN_AND_ENDS_WITH,
                                new String[]{"είο","ιά","ία","ικο","δικο"}, problemIndexPosition, i++);
                problemDefinitionIndex.setProblemDescription(ProblemType.IS_BIG_NOUN_AND_ENDS_WITH,
                                new String[]{"έας","ερό","ιέρα","τήρας","τήρι","τήριο",
                                "τρα","της"}, problemIndexPosition, i++);
                problemDefinitionIndex.setProblemDescription(ProblemType.IS_BIG_NOUN_AND_ENDS_WITH,
                                new String[]{"άδα","εια","ίλα","ιλίκι","μάρα","οσύνη",
                                "ούρα","σιά","ξιά","ότητα","ύτητα"}, problemIndexPosition, i++);
                problemDefinitionIndex.setProblemDescription(ProblemType.IS_BIG_NOUN_AND_ENDS_WITH,
                                new String[]{"ί","ιά"}, problemIndexPosition, i++);
                problemDefinitionIndex.setProblemDescription(ProblemType.IS_BIG_NOUN_AND_ENDS_WITH,
                                new String[]{"άλα","εία","ειά","εια","ση","ξη","ψη",
                                "ία","σιά","ψιά","ξιά","ματιά","σιμο","ξιμο","ψιμο","αμα","ημα","ωμα","σμα",
                                "γμα","μός","ητό","ατό","κτό","χτό","φτό","πτό"}, problemIndexPosition, i++);
                problemDefinitionIndex.setProblemDescription(ProblemType.IS_BIG_ADJ_AND_ENDS_WITH,
                                new String[]{"ικός","ική","ικό","σιμος","σιμη","σιμο",
                                "ιάρης","ιάρα","άρικο","ερός","ερή","ερό","τός","τή","τό","άτος","άτη","άτο",
                                "ινος","ινη","ινο","ιακός","ιακή","ιακό","ανός","ανή","ανό","ούρης","ούρα",
                                "ούρικο"}, problemIndexPosition, i++);
                problemDefinitionIndex.setProblemDescription(ProblemType.IS_BIG_ADJ_AND_ENDS_WITH,
                                new String[]{"ίστικος","ίστικη","ίστικο","ήσιος","ήσια","ήσιο",
                                "λέος","λέα","λέο","αίος","αία","αίο","ωπός","ωπή","ωπό","ένιος","ένια","ένιο",
                                "τέος","τέα","τέο","ώδης","ώδες"}, problemIndexPosition, i++);
                problemDefinitionIndex.setProblemDescription(ProblemType.IS_BIG_VERB_AND_ENDS_WITH,
                                new String[]{"ίζω","άζω","ιάζω","αίνω","ώνω","ύνω","εύω","άρω"}, problemIndexPosition, i++);
                problemDefinitionIndex.setProblemDescription(ProblemType.IS_BIG_VERB_AND_ENDS_WITH,
                                new String[]{"βολώ","λογώ","ποιώ"}, problemIndexPosition, i++);
                problemDefinitionIndex.setProblemDescription(ProblemType.IS_BIG_ADJ_AND_ENDS_WITH,
                                new String[]{"ειδής","ειδές","μελής","μελές",
                                "ετής","ετές"}, problemIndexPosition, i++);
			}

			//B.5 Suffixing: Inflectional/Grammatical (17 problems)
			problemIndexPosition = 4;
			problemLength = 19;
			prob = new ProblemDefinition("Inflectional/Grammatical", cat);
			problemDefinitionIndex.setProblemDefinition(prob, problemIndexPosition);
			problemDefinitionIndex.constructProblemRow(problemIndexPosition, problemLength);
			{
				int i=0;
				problemDefinitionIndex.setProblemDescription(ProblemType.IS_NOUN_AND_ENDS_WITH, 
						new String[]{"ος", "ο", "ας", "α", "ης", "η", "α", "η", "ο"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.IS_NOUN_AND_ENDS_WITH, 
						new String[]{"οι", "ους", "ες", "εις", "α"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.IS_NOUN_AND_ENDS_WITH, 
						new String[]{"ου", "ων", "ες", "ων", "εις", "εων", "ες", "ών", "ων", "ου", "ων"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.IS_NOUN_AND_ENDS_WITH, 
						new String[]{"ες", "ε", "άς", "ά", "ούς", "ού", "ού", "ι", "ον", "ος", "ας", "α", "υ"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.IS_NOUN_AND_ENDS_WITH, 
						new String[]{"έδες", "άδες", "ούδες", "οντα", "ά", "η", "ατα"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.IS_NOUN_AND_ENDS_WITH, 
						new String[]{"έ", "έδων", "ά", "άδων", "ού", "ούδων", "ούς", "", "ούδων", "ιού", "ιών", "οντος", "όντων", "ατος", "άτων"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.IS_ADJ_AND_ENDS_WITH, 
						new String[]{"ος", "η", "ο", "ος", "α", "ο", "ός", "ιά", "ό", "ης", "α", "ικο", "ικάς", "ικού", "άδικο"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.IS_ADJ_AND_ENDS_WITH, 
						new String[]{"ύς", "ιά", "ύ", "ής", "ιά", "ί", "ής", "ές"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.EQUALS, 
						new String[]{"πολύς", "πολλή", "πολύ"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.IS_ADJ_AND_ENDS_WITH, 
						new String[]{"οι", "ες", "α", "ηδες", "ες", "ικα", "άδες", "ούδες", "άδικα"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.IS_ADJ_AND_ENDS_WITH, 
						new String[]{"ιοί", "ιές", "ιά", "", "είς", "ή"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.EQUALS, 
						new String[]{"πολλοί", "πολλές", "πολλά"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.IS_ADJ_AND_ENDS_WITH, 
						new String[]{"ου", "ης", "ου", "ου", "ας", "ου", "ού", "ιάς", "ού", "η", "ας", "ικου", "ά", "ούς", "άδικου"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.IS_ADJ_AND_ENDS_WITH, 
						new String[]{"ιού", "ιάς", "ιού", "ιών", "ούς", "ών"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.IS_VERB_AND_ENDS_WITH, 
						new String[]{"ω", "εις", "ει", "ουμε", "ετε", "ουν", "ώ", "άς", "ά", "άει", "άμε", "ούμε", "άτε", "ούν", "ούνε", "άν", "άνε", "ούν", "ώ", "είς", "εί", "ούμε", "είτε", "ούν", "ούνε"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.IS_VERB_AND_ENDS_WITH, 
						new String[]{"α", "ες", "ε", "αμε", "ατε", "αν", "άνε", "αγα", "αγες", "αγε", "άγαμε", "άγατε", "αγαν", "άγανε", "ούσα", "ούσες", "ούσε", "ούσαμε", "ούσατε", "ούσαν", "ούσανε"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.IS_VERB_AND_ENDS_WITH, 
						new String[]{"ομαι", "εσαι", "εται", "όμαστε", "όσαστε", "εστε", "ονται", "ιέμαι", "ιέσαι", "ιέται", "ιόμαστε", "ιέστε", "ιούνται", "ιόνται", "ιούμαι", "είσαι", "", "είται", "ούμαστε", "είστε", "ούνται"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.IS_VERB_AND_ENDS_WITH, 
						new String[]{"όμουν", "όμουνα", "όσουν", "όμουνα", "όταν", "ότανε", "όμασταν", "όμαστε", "όσασταν", "όσαστε", "ονταν", "όντανε", "όντουσαν", "ούμουν", "ούμουνα", "ούσουν", "ούσουνα", "ούνταν", "ούμασταν", "ούμαστε", "ούσασταν", "ούσαστε", 
						"ούνταν", "ούντανε", "ηκα", "ηκες", "ηκε", "ήκαμε", "ήκατε", "αν"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.IS_PARTICIPLE_AND_ENDS_WITH, 
						new String[]{"ών", "ούσα", "όν", "είς", "είσα", "έν"}, problemIndexPosition, i++);
			}

			cat = new Category("Prefixing");
			//B.6 Prefixing (6 problems)
			problemIndexPosition = 5;
			problemLength = 6;
			prob = new ProblemDefinition("Prefixing", cat);
			problemDefinitionIndex.setProblemDefinition(prob, problemIndexPosition);
			problemDefinitionIndex.constructProblemRow(problemIndexPosition, problemLength);
			{
				int i=0;
				problemDefinitionIndex.setProblemDescription(ProblemType.IS_ADJ_AND_STARTS_WITH, 
						new String[]{"α", "αντι", "δυσ"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.IS_NOUN_OR_ADJ_AND_STARTS_WITH, 
						new String[]{"υπερ", "υπο", "κατα"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.IS_VERB_AND_STARTS_WITH, 
						new String[]{"υπερ", "υπο"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.STARTS_WITH, //starts with kai einai epiu8eto
						new String[]{"δι", "τρι", "πρωτο", "αυτο", "πολυ", "μικρο", "ψιλο", "ημι"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.IS_VERB_AND_STARTS_WITH, 
						new String[]{"ανα", "αντι", "απο", "δια", "εισ", "εκ", "εξ", "εν", "επι", "κατα", 
						"μετα", "παρα", "περι", "προ", "προσ", "συν"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.IS_VERB_AND_STARTS_WITH, 
						new String[]{"ψιλο", "μισο", "κουτσο", "ψευτο"}, problemIndexPosition, i++);
			}


			cat = new Category("Grapheme/Phoneme Correspondence");
			//B.7 Grapheme/Phoneme Correspondence
			
			//Regular:Consonant clusters (20 problems)
			problemIndexPosition = 6;
			problemLength = 27;
			prob = new ProblemDefinition("Regular/Irregular:Consonant clusters", cat);
			problemDefinitionIndex.setProblemDefinition(prob, problemIndexPosition);
			problemDefinitionIndex.constructProblemRow(problemIndexPosition, problemLength);
			{
				int i=0;
				problemDefinitionIndex.setProblemDescription(ProblemType.TWO_SYL_WORD_INITIAL_PHONEME, 
						new String[]{"sp", "st", "sk"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.TWO_SYL_WORD_INITIAL_PHONEME, 
						new String[]{"pr","tr","kr"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.TWO_SYL_WORD_INITIAL_PHONEME, 
						new String[]{"gr", "dr", "br"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.TWO_SYL_WORD_INITIAL_PHONEME, 
						new String[]{"fr", "ðr", "χr", "vr", "γr", "θr"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.TWO_SYL_WORD_INITIAL_PHONEME, 
						new String[]{"spr", "skr", "str", "sfr"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.THREE_SYL_WORD_INITIAL_PHONEME, 
						new String[]{"sp", "st", "sk"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.THREE_SYL_WORD_INITIAL_PHONEME, 
						new String[]{"pr", "tr", "kr"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.THREE_SYL_WORD_INITIAL_PHONEME, 
						new String[]{"gr", "dr", "br"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.THREE_SYL_WORD_INITIAL_PHONEME, 
						new String[]{"fr", "ðr", "χr", "vr", "γr", "θr"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.THREE_SYL_WORD_INITIAL_PHONEME, 
						new String[]{"spr", "skr", "str", "sfr"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.TWO_SYL_WORD_INTERNAL_PHONEME, 
						new String[]{"sp", "st", "sk"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.TWO_SYL_WORD_INTERNAL_PHONEME, 
						new String[]{"pr", "tr", "kr"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.TWO_SYL_WORD_INTERNAL_PHONEME, 
						new String[]{"gr", "dr", "br"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.TWO_SYL_WORD_INTERNAL_PHONEME, 
						new String[]{"fr", "ðr", "χr", "vr", "γr", "θr"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.TWO_SYL_WORD_INTERNAL_PHONEME, 
						new String[]{"spr", "skr", "str", "sfr"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.THREE_SYL_WORD_INTERNAL_PHONEME, 
						new String[]{"sp", "st", "sk"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.THREE_SYL_WORD_INTERNAL_PHONEME, 
						new String[]{"pr", "tr", "kr"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.THREE_SYL_WORD_INTERNAL_PHONEME, 
						new String[]{"gr", "dr", "br"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.THREE_SYL_WORD_INTERNAL_PHONEME, 
						new String[]{"fr", "ðr", "χr", "vr", "γr", "θr"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.THREE_SYL_WORD_INTERNAL_PHONEME, 
						new String[]{"spr", "skr", "str", "sfr"}, problemIndexPosition, i++);
			//}
			
			//Irregular (7 problems)
			//problemIndexPosition = 7;
			//problemLength = 7;
			//prob = new ProblemDefinition("Irregular", cat);
			//problemDefinitionIndex.setProblemDefinition(prob, problemIndexPosition);
			//problemDefinitionIndex.constructProblemRow(problemIndexPosition, problemLength);
			//{
			//	int i=0;
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS, 
						new String[]{"ευ", "εύ"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS, 
						new String[]{"αυ", "αύ"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS, 
						new String[]{"εϋ"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS, 
						new String[]{"οϋ"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS, 
						new String[]{"αϊ"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS, 
						new String[]{"οΰ"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.CONTAINS, 
						new String[]{"αΐ"}, problemIndexPosition, i++);
			}

			cat = new Category("Grammar/Function Words");
			//B.8 Grammar/Function Words (10 problems)
			problemIndexPosition = 7;
			problemLength = 10;
			prob = new ProblemDefinition("Grammar/Function Words", cat);
			problemDefinitionIndex.setProblemDefinition(prob, problemIndexPosition);
			problemDefinitionIndex.constructProblemRow(problemIndexPosition, problemLength);
			{
				int i=0;
				problemDefinitionIndex.setProblemDescription(ProblemType.EQUALS, 
						new String[]{"των"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.EQUALS, 
						new String[]{"ένας", "μία", "ένα"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.EQUALS, 
						new String[]{"ενός", "μιας"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.EQUALS, 
						new String[]{"ο", "η", "το"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.EQUALS, 
						new String[]{"του", "της"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.EQUALS, 
						new String[]{"οι", "τα"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.EQUALS, 
						new String[]{"σε"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.EQUALS, 
						new String[]{"με"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.EQUALS, 
						new String[]{"για"}, problemIndexPosition, i++);
				problemDefinitionIndex.setProblemDescription(ProblemType.EQUALS, 
						new String[]{"από"}, problemIndexPosition, i++);
			}		
	}
}
	
	
