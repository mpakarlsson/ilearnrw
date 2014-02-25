package com.ilearnrw.loginapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.ilearnrw.loginapp.network.Authentication;
import com.ilearnrw.loginapp.network.LoginResult;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	
	//Shared preferences
	private SharedPreferences sharedPreferences;
	SharedPreferences.Editor sharedPreferencesEditor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		
		// Set up the shared preferences objects
		sharedPreferences = getSharedPreferences(ACCOUNT_SERVICE, MODE_PRIVATE);
		sharedPreferencesEditor = sharedPreferences.edit();

		// Set up the login form.
		mEmailView = (EditText) findViewById(R.id.username);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserLoginTask();
			mAuthTask.execute((Void) null);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
	
	public class UnauthorizedException extends Exception
	{
		private static final long serialVersionUID = 1L;
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, LoginResult> {
		private static final String TAG_LOGIN = "login";

		@Override
		protected LoginResult doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.
			
			String baseURL = "http://api.ilearnrw.eu/ilearnrw";
			String authenticationUrl = "/user/auth?username=" + mEmail + "&pass=" + mPassword;
			
			Log.d(TAG_LOGIN,"connecting to server");
			
			try {
				String response = null;
				URL url = new URL(baseURL + authenticationUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				//encoding of api:api
				String basicAuth = "Basic YXBpOmFwaQ==";
				Log.d(TAG_LOGIN,"connecting");
				connection.setRequestProperty ("Authorization", basicAuth);
				connection.connect();
				int responseCode = connection.getResponseCode();
				Log.d(TAG_LOGIN,"Response code: " + responseCode);
				if (responseCode == 403)
					throw new UnauthorizedException();
				response = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8")).readLine();
				
				Log.d(TAG_LOGIN,"deserializing");
				Gson gson = new Gson();
				Authentication authentication = gson.fromJson(response, Authentication.class);
				Log.d(TAG_LOGIN,"successful");
				LoginResult result = new LoginResult();
				result.setSuccessful(true);
				result.setAuthentication(authentication);
				return result;
			}
			catch (Exception e) {
				Log.d(TAG_LOGIN,"error, returning unsuccessful login result");
				LoginResult result = new LoginResult();
				result.setException(e);
				return result;
			}
		}

		@Override
		protected void onPostExecute(final LoginResult loginResult) {
			mAuthTask = null;
			showProgress(false);

			if (loginResult.isSuccessful()) {
				Log.d(TAG_LOGIN,"finished");
				Log.d(TAG_LOGIN,loginResult.getAuthentication().getAuth());
				sharedPreferencesEditor.putString("auth", loginResult.getAuthentication().getAuth());
				sharedPreferencesEditor.putString("refresh", loginResult.getAuthentication().getRefresh());
				sharedPreferencesEditor.commit();
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			} else {
				Log.d(TAG_LOGIN,"oops");
				try {
					throw loginResult.getException();
				} catch (IOException e) {
					Log.d(TAG_LOGIN,"I/O");
					Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
					alertDialogBuilder
						.setTitle("Error!")
						.setMessage("Could not connect to server.")
						.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					        public void onClick(DialogInterface dialog, int which) {
					            //dismiss the dialog  
					          }
					      });
					alertDialogBuilder.create().show();
				} catch (UnauthorizedException e) {
					Log.d(TAG_LOGIN,"Unauthorized");
					mPasswordView
							.setError(getString(R.string.error_incorrect_password));
					mPasswordView.requestFocus();
				} catch (Exception e) {
					Log.d(TAG_LOGIN,"Exception");
					Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
					alertDialogBuilder
						.setTitle("Error!")
						.setMessage("Unknown error.")
						.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					        public void onClick(DialogInterface dialog, int which) {
					            //dismiss the dialog  
					          }
					      });
					alertDialogBuilder.create().show();
				}
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}
