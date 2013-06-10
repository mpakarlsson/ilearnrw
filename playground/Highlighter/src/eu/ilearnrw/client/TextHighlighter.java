package eu.ilearnrw.client;

import android.graphics.Color;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;

public class TextHighlighter {

	private String initialText;
	private String[] words;
	private int currentWord;
	private int lastPosition = -1;
	private boolean autoLoop = false;
	private Handler handler = new Handler();
	private int frequency = 500;
	private HighlightNotifier notifier;
	private SpannableString ss;
	private Object[] highlightSpan = new Object[] { new BackgroundColorSpan(
			Color.GREEN) };

	private Runnable run = new Runnable() {

		public void run() {
			if (!TextHighlighter.this.hasNext()) {
				TextHighlighter.this.reset();
				if (!autoLoop) {
					removeHighlighting();
					notifier.onHighlight("", getString());
					return;
				}
			}
			TextHighlighter.this.highlightNext();

			handler.postDelayed(this, getFrequency()
					+ getWordDelay(getCurrentWord()));
		}

		private int getWordDelay(String currentWord) {
			return 100 * currentWord.length();
		}

	};

	private String getCurrentWord() {
		if (hasNext()) {
			return words[currentWord];
		}

		return "";
	}

	public TextHighlighter(String text, HighlightNotifier notifier) {
		initialText = text;
		words = text.split(" ");
		currentWord = -1;
		this.notifier = notifier;
		ss = new SpannableString(initialText);
	}

	public void start() {
		reset();
		handler.post(run);
	}

	public void stop() {
		handler.removeCallbacks(run);
	}

	public SpannableString highlightNext() {
		currentWord++;
		removeHighlighting();
		if (currentWord < words.length) {
			int newPosition = initialText.indexOf(getCurrentWord(),
					lastPosition);
			lastPosition = newPosition + getCurrentWord().length();
			setHighlighting(newPosition, lastPosition);
		}
		notifier.onHighlight(getCurrentWord(), getString());

		return ss;
	}

	private void setHighlighting(int start, int end) {
		for (Object span : highlightSpan) {
			ss.setSpan(span, start, end, 0);
		}
	}

	private void removeHighlighting() {
		for (Object span : highlightSpan) {
			ss.removeSpan(span);
		}

	}

	public boolean hasNext() {
		return (currentWord < words.length);

	}

	public void reset() {
		currentWord = -1;
		lastPosition = -1;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public SpannableString getString() {
		return ss;
	}

	public Object getHighlightSpan() {
		return highlightSpan;
	}

	public void setHighlightSpan(Object[] highlightSpan) {
		this.highlightSpan = highlightSpan;
	}

	public boolean isAutoLoop() {
		return autoLoop;
	}

	public void setAutoLoop(boolean autoLoop) {
		this.autoLoop = autoLoop;
	}

}
