package ilearnrw.languagetools.greek;
/*
 * Speller.java
 *
 * Created on 4 june 2008, 11:13 pm
 *
 */
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
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
    public String toString();
    public String getToken(int index);
    public int getTokensNumber();
}
