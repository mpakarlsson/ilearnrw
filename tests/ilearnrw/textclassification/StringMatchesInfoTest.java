package ilearnrw.textclassification;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import ilearnrw.prototype.application.JsonHandler;
import ilearnrw.resource.ResourceLoader.Type;
import ilearnrw.textclassification.english.EnglishWord;
import ilearnrw.user.problems.ProblemDefinitionIndex;
import ilearnrw.user.problems.ProblemType;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;



public class StringMatchesInfoTest {
	
	private static ProblemDefinitionIndex pdIndex;
	
	private static EnglishWord ew1, ew2, ew3, ew4, ew5, ew6, ew7;
	
	@BeforeClass
	public static void runOnceBeforeAnything(){
		JsonHandler handler = new JsonHandler(Type.DATA, "problem_definitions_en.json", true);
		pdIndex = (ProblemDefinitionIndex) handler.fromJson(ProblemDefinitionIndex.class);
		
		ew1 = new EnglishWord("absorbing", "əb.ˈzɔːb.ɪŋ", 
				new ArrayList<GraphemePhonemePair>(createGraphemePhonemeList(new String[] {"a-ə", "b-b", "s-z", "or-ɔː", "b-b", "i-ɪ", "ng-ŋ"})),
				3, 5001, WordType.Unknown);
		
		ew2 = new EnglishWord("angle", "ˈæŋ.ɡəl", 
				new ArrayList<GraphemePhonemePair>(createGraphemePhonemeList(new String[] {"a-æ", "n-ŋ", "g-ɡ", "le-əl"})),
				2, 5001, WordType.Unknown);
	
		ew3 = new EnglishWord("balloon", "bə.ˈluːn", 
				new ArrayList<GraphemePhonemePair>(createGraphemePhonemeList(new String[] {"b-b", "a-ə", "ll-l", "oo-uː", "n-n"})),
				2, 5001, WordType.Unknown);
		
		ew4 = new EnglishWord("bee's", "biːz", 
				new ArrayList<GraphemePhonemePair>(createGraphemePhonemeList(new String[] {"b-b", "ee-iː", "'-", "s-z"})),
				1, 5001, WordType.Unknown);

		ew5 = new EnglishWord("psychology", "saɪˈk.ɒ.lə.dʒi", 
				new ArrayList<GraphemePhonemePair>(createGraphemePhonemeList(new String[] {"ps-s", "y-aɪ", "ch-k", "o-ɒ", "l-l", "o-ə", "g-dʒ", "y-i"})),
				4, 5001, WordType.Unknown);
	
		ew6 = new EnglishWord("majority", "mə.ˈdʒɒ.rɪ.ti", 
				new ArrayList<GraphemePhonemePair>(createGraphemePhonemeList(new String[] {"m-m", "a-ə", "j-dʒ", "o-ɒ", "r-r", "i-ɪ", "t-t", "y-i"})),
				2, 5001, WordType.Unknown);

		ew7 = new EnglishWord("tablespoon", "ˈteɪ.bl.spuːn", 
				new ArrayList<GraphemePhonemePair>(createGraphemePhonemeList(new String[] {"t-t", "a.e-eɪ", "b-b", "l-l", "s-s", "p-p", "oo-uː", "n-n"})),
				3, 5001, WordType.Unknown);
	}
	
	@AfterClass
	public static void runOnceAfterEveryThing(){
		
	}
	
	@Test
	public void testEndsWithSuffix(){
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo1 = new ArrayList<ArrayList<StringMatchesInfo>>();
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo2 = new ArrayList<ArrayList<StringMatchesInfo>>();
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo3 = new ArrayList<ArrayList<StringMatchesInfo>>();
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo4 = new ArrayList<ArrayList<StringMatchesInfo>>();
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo5 = new ArrayList<ArrayList<StringMatchesInfo>>();
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo6 = new ArrayList<ArrayList<StringMatchesInfo>>();
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo7 = new ArrayList<ArrayList<StringMatchesInfo>>();
		
		for (int i = 0; i < pdIndex.getIndexLength(); i++) {
			for (int j = 0; j < pdIndex.getRowLength(i); j++) {
				String[] pd = pdIndex.getProblemDescription(i, j).getDescriptions();
				ProblemType pt = pdIndex.getProblemDescription(i, j).getProblemType();
				
				if(pt.equals(ProblemType.SUFFIX_ADD) || pt.equals(ProblemType.SUFFIX_DROP) ||
						pt.equals(ProblemType.SUFFIX_CHANGE) || pt.equals(ProblemType.SUFFIX_DOUBLE) ||
						pt.equals(ProblemType.SUFFIX_STRESS_PATTERN) || pt.equals(ProblemType.SUFFIX_PATTERN)){
					
					ArrayList<StringMatchesInfo> smiInfo = StringMatchesInfo.endsWithSuffix(new String[]{pd[0]}, ew1);

					if(pd[0].equals("ing"))
						assertNotNull(smiInfo);
					else
						assertNull(smiInfo);
					
					if(smiInfo != null) strMatchInfo1.add(smiInfo);
					
					smiInfo = StringMatchesInfo.endsWithSuffix(new String[]{pd[0]}, ew2);
					if(smiInfo != null) strMatchInfo2.add(smiInfo);
					
					smiInfo = StringMatchesInfo.endsWithSuffix(new String[]{pd[0]}, ew3);
					if(smiInfo != null) strMatchInfo3.add(smiInfo);
					
					smiInfo = StringMatchesInfo.endsWithSuffix(new String[]{pd[0]}, ew4);
					if(smiInfo != null) strMatchInfo4.add(smiInfo);
					
					smiInfo = StringMatchesInfo.endsWithSuffix(new String[]{pd[0]}, ew5);
					if(smiInfo != null) strMatchInfo5.add(smiInfo);
					
					smiInfo = StringMatchesInfo.endsWithSuffix(new String[]{pd[0]}, ew6);
					if(smiInfo != null) strMatchInfo6.add(smiInfo);
					
					smiInfo = StringMatchesInfo.endsWithSuffix(new String[]{pd[0]}, ew7);
					if(smiInfo != null) strMatchInfo7.add(smiInfo);
					
				}
			}
		}
		assertEquals(3, strMatchInfo1.size());
		assertEquals(1, strMatchInfo2.size());
		assertEquals(0, strMatchInfo3.size());
		assertEquals(1, strMatchInfo4.size());
		assertEquals(3, strMatchInfo5.size());
		assertEquals(5, strMatchInfo6.size());
		assertEquals(0, strMatchInfo7.size());

	}
	
	@Test
	public void testStartsWithPrefix(){
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo1 = new ArrayList<ArrayList<StringMatchesInfo>>();
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo2 = new ArrayList<ArrayList<StringMatchesInfo>>();
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo3 = new ArrayList<ArrayList<StringMatchesInfo>>();
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo4 = new ArrayList<ArrayList<StringMatchesInfo>>();
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo5 = new ArrayList<ArrayList<StringMatchesInfo>>();
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo6 = new ArrayList<ArrayList<StringMatchesInfo>>();
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo7 = new ArrayList<ArrayList<StringMatchesInfo>>();
		
		for (int i = 0; i < pdIndex.getIndexLength(); i++) {
			for (int j = 0; j < pdIndex.getRowLength(i); j++) {
				String[] pd = pdIndex.getProblemDescription(i, j).getDescriptions();
				ProblemType pt = pdIndex.getProblemDescription(i, j).getProblemType();
				
				if(pt.equals(ProblemType.PREFIX) || pt.equals(ProblemType.PREFIX_GROUP)){
					ArrayList<StringMatchesInfo> smiInfo = StringMatchesInfo.startsWithPrefix(pd, ew1);
					if(smiInfo!=null) strMatchInfo1.add(smiInfo);
					
					smiInfo = StringMatchesInfo.startsWithPrefix(pd, ew2);
					if(smiInfo!=null) strMatchInfo2.add(smiInfo);
					
					smiInfo = StringMatchesInfo.startsWithPrefix(pd, ew3);
					if(smiInfo!=null) strMatchInfo3.add(smiInfo);
				
					smiInfo = StringMatchesInfo.startsWithPrefix(pd, ew4);
					if(smiInfo!=null) strMatchInfo4.add(smiInfo);
					
					smiInfo = StringMatchesInfo.startsWithPrefix(pd, ew5);
					if(smiInfo!=null) strMatchInfo5.add(smiInfo);
					
					smiInfo = StringMatchesInfo.startsWithPrefix(pd, ew6);
					if(smiInfo!=null) strMatchInfo6.add(smiInfo);
					
					smiInfo = StringMatchesInfo.startsWithPrefix(pd, ew7);
					if(smiInfo!=null) strMatchInfo7.add(smiInfo);
				}
			}
		}
		
		assertEquals(0, strMatchInfo1.size());
		assertEquals(0, strMatchInfo2.size());
		assertEquals(0, strMatchInfo3.size());
		assertEquals(0, strMatchInfo4.size());
		assertEquals(0, strMatchInfo5.size());
		assertEquals(0, strMatchInfo6.size());
		assertEquals(0, strMatchInfo7.size());
		
	}
	
	@Test
	public void testEqualsPhoneme(){
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo1 = new ArrayList<ArrayList<StringMatchesInfo>>();
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo2 = new ArrayList<ArrayList<StringMatchesInfo>>();
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo3 = new ArrayList<ArrayList<StringMatchesInfo>>();
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo4 = new ArrayList<ArrayList<StringMatchesInfo>>();
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo5 = new ArrayList<ArrayList<StringMatchesInfo>>();
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo6 = new ArrayList<ArrayList<StringMatchesInfo>>();
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo7 = new ArrayList<ArrayList<StringMatchesInfo>>();
		
		for (int i = 0; i < pdIndex.getIndexLength(); i++) {
			for (int j = 0; j < pdIndex.getRowLength(i); j++) {
				String[] pd = pdIndex.getProblemDescription(i, j).getDescriptions();
				ProblemType pt = pdIndex.getProblemDescription(i, j).getProblemType();
			
				if(pt.equals(ProblemType.LETTER_EQUALS_PHONEME) || pt.equals(ProblemType.DIGRAPH_EQUALS_PHONEME) ||
						pt.equals(ProblemType.TRIGRAPH_EQUALS_PHONEME)){
					ArrayList<StringMatchesInfo> smiInfo = StringMatchesInfo.equalsPhoneme(pd, ew1, pt.toString());
					if(smiInfo!=null) strMatchInfo1.add(smiInfo);
					
					smiInfo = StringMatchesInfo.equalsPhoneme(pd, ew2, pt.toString());
					if(smiInfo!=null) strMatchInfo2.add(smiInfo);
					
					smiInfo = StringMatchesInfo.equalsPhoneme(pd, ew3, pt.toString());
					if(smiInfo!=null) strMatchInfo3.add(smiInfo);
				
					smiInfo = StringMatchesInfo.equalsPhoneme(pd, ew4, pt.toString());
					if(smiInfo!=null) strMatchInfo4.add(smiInfo);
					
					smiInfo = StringMatchesInfo.equalsPhoneme(pd, ew5, pt.toString());
					if(smiInfo!=null) strMatchInfo5.add(smiInfo);
					
					smiInfo = StringMatchesInfo.equalsPhoneme(pd, ew6, pt.toString());
					if(smiInfo!=null) strMatchInfo6.add(smiInfo);
					
					smiInfo = StringMatchesInfo.equalsPhoneme(pd, ew7, pt.toString());
					if(smiInfo!=null) strMatchInfo7.add(smiInfo);
					
					
				}	
			}
		}
		assertEquals(5, strMatchInfo1.size());
		assertEquals(1, strMatchInfo2.size());
		assertEquals(4, strMatchInfo3.size());
		assertEquals(3, strMatchInfo4.size());
		assertEquals(5, strMatchInfo5.size());
		assertEquals(7, strMatchInfo6.size());
		assertEquals(7, strMatchInfo7.size());
	}
	
	@Test
	public void testSyllableCount(){
		
		assertNotNull(StringMatchesInfo.syllableCount(new String[]{"Syllable count >= 3"}, ew1));
		assertNull(StringMatchesInfo.syllableCount(new String[]{"Syllable count >= 3"}, ew2));
		assertNull(StringMatchesInfo.syllableCount(new String[]{"Syllable count >= 3"}, ew3));
		assertNull(StringMatchesInfo.syllableCount(new String[]{"Syllable count >= 3"}, ew4));
		assertNotNull(StringMatchesInfo.syllableCount(new String[]{"Syllable count >= 3"}, ew5));
		assertNotNull(StringMatchesInfo.syllableCount(new String[]{"Syllable count >= 3"}, ew6));
		
		// This is correct, but the syllable dividing is not working as it should
		// Todo: add syllable information to the dictinary
		//assertNotNull(StringMatchesInfo.syllableCount(new String[]{"Syllable count >= 3"}, ew7));
	}
	
	@Test
	public void testPatternEqualsPronunciation(){
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo1 = new ArrayList<ArrayList<StringMatchesInfo>>();
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo2 = new ArrayList<ArrayList<StringMatchesInfo>>();
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo3 = new ArrayList<ArrayList<StringMatchesInfo>>();
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo4 = new ArrayList<ArrayList<StringMatchesInfo>>();
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo5 = new ArrayList<ArrayList<StringMatchesInfo>>();
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo6 = new ArrayList<ArrayList<StringMatchesInfo>>();
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo7 = new ArrayList<ArrayList<StringMatchesInfo>>();
		
		EnglishWord tempWord = new EnglishWord("framework", "ˈfreɪm.wɜːk",
				new ArrayList<GraphemePhonemePair>(createGraphemePhonemeList(new String[]{"f-f", "r-r", "a.e-eɪ", "m-m", "w-w", "or-ɜː", "k-k"})),
				3, 5001, WordType.Unknown);
		
		
		ArrayList<ArrayList<StringMatchesInfo>> strMatchInfo8 = new ArrayList<ArrayList<StringMatchesInfo>>();
		
		for (int i = 0; i < pdIndex.getIndexLength(); i++) {
			for (int j = 0; j < pdIndex.getRowLength(i); j++) {
				String[] pd = pdIndex.getProblemDescription(i, j).getDescriptions();
				ProblemType pt = pdIndex.getProblemDescription(i, j).getProblemType();

				if(pt.equals(ProblemType.PATTERN_EQUALS_PRONUNCIATION_CONTAINS) || pt.equals(ProblemType.PATTERN_EQUALS_PRONUNCIATION_BEGINS) ||
						pt.equals(ProblemType.PATTERN_EQUALS_PRONUNCIATION_ENDS)){
					
					String type = pt.toString().toLowerCase().substring(pt.toString().lastIndexOf("_")+1);
					
					ArrayList<StringMatchesInfo> smiInfo = StringMatchesInfo.patternEqualsPronunciation(pd, ew1, type);
					if(smiInfo!=null) strMatchInfo1.add(smiInfo);
					
					smiInfo = StringMatchesInfo.patternEqualsPronunciation(pd, ew2, type);
					if(smiInfo!=null) strMatchInfo2.add(smiInfo);
					
					smiInfo = StringMatchesInfo.patternEqualsPronunciation(pd, ew3, type);
					if(smiInfo!=null) strMatchInfo3.add(smiInfo);
					
					smiInfo = StringMatchesInfo.patternEqualsPronunciation(pd, ew4, type);
					if(smiInfo!=null) strMatchInfo4.add(smiInfo);
					
					smiInfo = StringMatchesInfo.patternEqualsPronunciation(pd, ew5, type);
					if(smiInfo!=null) strMatchInfo5.add(smiInfo);
					
					smiInfo = StringMatchesInfo.patternEqualsPronunciation(pd, ew6, type);
					if(smiInfo!=null) strMatchInfo6.add(smiInfo);
					
					smiInfo = StringMatchesInfo.patternEqualsPronunciation(pd, ew7, type);
					if(smiInfo!=null) strMatchInfo7.add(smiInfo);	
					
					smiInfo = StringMatchesInfo.patternEqualsPronunciation(pd, tempWord, type);
					if(smiInfo!=null) strMatchInfo8.add(smiInfo);
				}
			}
		}
		assertEquals(1, strMatchInfo1.size());
		assertEquals(0, strMatchInfo2.size());
		assertEquals(1, strMatchInfo3.size());
		assertEquals(0, strMatchInfo4.size());
		assertEquals(1, strMatchInfo5.size());
		assertEquals(0, strMatchInfo6.size());
		assertEquals(1, strMatchInfo7.size());
		assertEquals(2, strMatchInfo8.size());
	}

	private static ArrayList<GraphemePhonemePair> createGraphemePhonemeList(String[] arr){
		ArrayList<String> al = new ArrayList<String>(Arrays.asList(arr));
		ArrayList<GraphemePhonemePair> graphemePhonemeList = new ArrayList<GraphemePhonemePair>();
		for (int i = 0; i < al.size(); i++) {
			String[] values = al.get(i).split("-", -1);	
			graphemePhonemeList.add(new GraphemePhonemePair(values[0], values[1]));
		}
		
		return graphemePhonemeList;
	}
	
}
