package eu.ilearnrw.client;

import java.util.HashMap;
import java.util.Locale;

import eu.ilearnrw.R;
import eu.ilearnrw.client.HighlightNotifier;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class HighlighterActivity extends Activity implements OnInitListener {
	private static final int MY_DATA_CHECK_CODE = 0;
	private TextView tv1;
	private TextView tv2;
	private TextToSpeech tts;
	private static final String TEXT1 = "Children have to read this text very carefully.";
	private static final String TEXT2 = "This is a second really hard to read text";

	TextHighlighter highlighter1 = new TextHighlighter(TEXT1, new HighlightNotifier() {

		public void onHighlight(String word, SpannableString text) {
			tv1.setText(text);
			HashMap<String, String> params = new HashMap<String, String>();
			params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, word);

			tts.setOnUtteranceCompletedListener(new OnUtteranceCompletedListener() {

				public void onUtteranceCompleted(String utteranceId) {
					runOnUiThread(new Runnable() {

						public void run() {
							if (highlighter1.hasNext()) {
								highlighter1.highlightNext();
							} else {
								highlighter1.reset();
							}
						}
					});

				}
			});

			tts.speak(word, TextToSpeech.QUEUE_ADD, params);

		}
	});

	TextHighlighter highlighter2 = new TextHighlighter(TEXT2, new HighlightNotifier() {

		public void onHighlight(String word, SpannableString text) {
			tv2.setText(text);

		}
	});

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == MY_DATA_CHECK_CODE) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				tts = new TextToSpeech(this, this);

			} else {
				Intent installTTSIntent = new Intent();
				installTTSIntent
						.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installTTSIntent);
			}
		}
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tv1 = (TextView) this.findViewById(R.id.textview1);
		tv1.setText(TEXT1);
		tv2 = (TextView) this.findViewById(R.id.textview2);
		tv2.setText(TEXT2);

		Button startButton = (Button) this.findViewById(R.id.startButton);
		startButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				highlighter1.reset();
				highlighter1.highlightNext();

			}
		});

		Button stopButton = (Button) this.findViewById(R.id.stopButton);
		stopButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				highlighter1.stop();

			}
		});

		Intent checkTTSIntent = new Intent();
		checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);

		highlighter2.setFrequency(200);
		highlighter2.setAutoLoop(true);
		highlighter2.setHighlightSpan(new Object[] {
				new StyleSpan(Typeface.BOLD_ITALIC),
				new BackgroundColorSpan(Color.DKGRAY),
				new ForegroundColorSpan(Color.WHITE) });

		SeekBar seekbar1 = (SeekBar) this.findViewById(R.id.seekbar1);
		seekbar1.setMax(3000);
		seekbar1.setProgress(highlighter1.getFrequency());
		seekbar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				highlighter1.setFrequency(progress);
			}
		});

		SeekBar seekbar2 = (SeekBar) this.findViewById(R.id.seekbar2);
		seekbar2.setMax(3000);
		seekbar2.setProgress(highlighter2.getFrequency());
		seekbar2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				highlighter2.setFrequency(progress);
			}
		});

		highlighter2.start();

	}

	public void onInit(int initStatus) {
		if (initStatus == TextToSpeech.SUCCESS) {
			tts.setLanguage(Locale.US);

		} else if (initStatus == TextToSpeech.ERROR) {
			Toast.makeText(this, "Sorry! Text To Speech failed...",
					Toast.LENGTH_LONG).show();
		}
	}

}