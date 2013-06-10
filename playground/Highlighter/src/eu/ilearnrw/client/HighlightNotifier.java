package eu.ilearnrw.client;

import android.text.SpannableString;

public interface HighlightNotifier {

	public void onHighlight(String word, SpannableString text);
}
