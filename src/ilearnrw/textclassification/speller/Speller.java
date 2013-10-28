package ilearnrw.textclassification.speller;
/*
 * Speller.java
 *
 * Created on 4 june 2008, 11:13 pm
 *
 */

import java.util.ArrayList;

/**
 * @author Fouli Argyriou
 */
public interface Speller {
    public void setStringToSpell(String strToSpell);
    public void performSpelling();
    public java.util.ArrayList<String> getTokens();
    public String[] getTokensArray();
    public String getHyphenSyllable(ArrayList<String> tokens);
    public String toString();
    public String getToken(int index);
    public int getTokensNumber();
}
