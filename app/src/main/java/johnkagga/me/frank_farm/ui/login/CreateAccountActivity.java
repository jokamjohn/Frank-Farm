package johnkagga.me.frank_farm.ui.login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import johnkagga.me.frank_farm.BaseActivity;
import johnkagga.me.frank_farm.R;
import johnkagga.me.frank_farm.model.FirebaseUser;
import johnkagga.me.frank_farm.ui.MainActivity;
import johnkagga.me.frank_farm.utils.Constants;
import johnkagga.me.frank_farm.utils.Helper;

/**
 * Represents Sign up screen and functionality of the app
 */
public class CreateAccountActivity extends BaseActivity {
    private static final String LOG_TAG = CreateAccountActivity.class.getSimpleName();
    private ProgressDialog mAuthProgressDialog;
    protected Firebase mFirebaseReference;
    private EditText mEditTextUsernameCreate, mEditTextEmailCreate, mEditTextPasswordCreate;
    private String mUserEmail;
    private String mUserPassword;
    private String mUserUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        /**
         * Link layout elements from XML and setup the progress dialog
         */
        initializeScreen();
    }

    /**
     * Override onCreateOptionsMenu to inflate nothing
     *
     * @param menu The menu with which nothing will happen
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    /**
     * Link layout elements from XML and setup the progress dialog
     */
    public void initializeScreen() {
        mEditTextUsernameCreate = (EditText) findViewById(R.id.edit_text_username_create);
        mEditTextEmailCreate = (EditText) findViewById(R.id.edit_text_email_create);
        mEditTextPasswordCreate = (EditText) findViewById(R.id.edit_text_password_create);
        Button createButton = (Button) findViewById(R.id.btn_create_account_final);
        TextView signIn = (TextView) findViewById(R.id.tv_sign_in);
        LinearLayout linearLayoutCreateAccountActivity = (LinearLayout) findViewById(R.id.linear_layout_create_account_activity);
        initializeBackground(linearLayoutCreateAccountActivity);

        /* Setup the progress dialog that is displayed later when authenticating with Firebase */
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle(getResources().getString(R.string.progress_dialog_loading));
        mAuthProgressDialog.setMessage(getResources().getString(R.string.progress_dialog_creating_user_with_firebase));
        mAuthProgressDialog.setCancelable(false);

        createButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                createFirebaseUser();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignIn();
            }
        });
    }

    /**
     * Open LoginActivity when user taps on "Sign in" textView
     */
    public void onSignIn() {
        Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    /**
     * Create new account using Firebase email/password provider
     */
    public void createFirebaseUser() {

        mFirebaseReference = new Firebase(Constants.FIREBASE_URL);
        mUserEmail = mEditTextEmailCreate.getText().toString().toLowerCase();
        mUserPassword = mEditTextPasswordCreate.getText().toString();
        mUserUsername = mEditTextUsernameCreate.getText().toString();

        if (!isEmailValid(mUserEmail) || !isPasswordValid(mUserPassword) || !isUserNameValid(mUserUsername))
            return;

        mAuthProgressDialog.show();

        mFirebaseReference.createUser(mUserEmail, mUserPassword, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                mAuthProgressDialog.dismiss();
                Log.i(LOG_TAG, getString(R.string.log_message_auth_successful));
                String uid = (String) result.get("uid");
                createUserInFirebaseHelper(uid);

                mFirebaseReference.authWithPassword(mUserEmail, mUserPassword, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        mAuthProgressDialog.dismiss();

                        if (authData != null) {
                            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor spe = sp.edit();
                            /**
                             * If user has logged in with Password provider
                             */
                            if (authData.getProvider().equals(Constants.PASSWORD_PROVIDER)) {
                                setAuthenticatedUserPasswordProvider(authData);
                            }

                /* Save provider name and encodedEmail for later use and start MainActivity */
                            spe.putString(Constants.KEY_PROVIDER, authData.getProvider()).apply();
                            spe.putString(Constants.KEY_ENCODED_EMAIL, mEncodedEmail).apply();

                /* Go to main activity */
                            Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }

                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        mAuthProgressDialog.dismiss();

                        /**
                         * Use utility method to check the network connection state
                         * Show "No network connection" if there is no connection
                         * Show Firebase specific error message otherwise
                         */
                        switch (firebaseError.getCode()) {
                            case FirebaseError.INVALID_EMAIL:
                            case FirebaseError.USER_DOES_NOT_EXIST:
                                mEditTextEmailCreate.setError(getString(R.string.error_message_email_issue));
                                break;
                            case FirebaseError.INVALID_PASSWORD:
                                mEditTextPasswordCreate.setError(firebaseError.getMessage());
                                break;
                            case FirebaseError.NETWORK_ERROR:
                                showErrorToast(getString(R.string.error_message_failed_sign_in_no_network));
                                break;
                            default:
                                showErrorToast(firebaseError.toString());
                        }
                    }
                });
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                /* Error occurred, log the error and dismiss the progress dialog */
                Log.d(LOG_TAG, getString(R.string.log_error_occurred) +
                        firebaseError);
                mAuthProgressDialog.dismiss();
                //Display appropriate error
                if (firebaseError.getCode() == FirebaseError.EMAIL_TAKEN) {
                    mEditTextEmailCreate.setError(getResources().getString(R.string.error_email_taken));
                } else {
                    showErrorToast(firebaseError.getMessage());
                }
            }
        });

    }

    /**
     * Helper method that makes sure a user is created if the user
     * logs in with Firebase's email/password provider.
     * @param authData AuthData object returned from onAuthenticated
     */
    private void setAuthenticatedUserPasswordProvider(AuthData authData) {
        final String unprocessedEmail = authData.getProviderData().get(Constants.FIREBASE_PROPERTY_EMAIL).toString().toLowerCase();
        /**
         * Encode user email replacing "." with ","
         * to be able to use it as a Firebase db key
         */
        mEncodedEmail = Helper.encodeEmail(unprocessedEmail);
    }
    /**
     * Creates a new user in Firebase from the Java POJO, if they do not exist
     */
    private void createUserInFirebaseHelper(final String uid) {

        final Firebase userLocation = new Firebase(Constants.FIREBASE_URL_USERS).child(uid);
        userLocation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot == null) {
                    HashMap<String, Object> timestampJoined = new HashMap<String, Object>();
                    timestampJoined.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
                    FirebaseUser user = new FirebaseUser(mUserUsername, mUserEmail, timestampJoined);
                    userLocation.setValue(user);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d(LOG_TAG, getString(R.string.log_error_occurred) + firebaseError.getMessage());
            }
        });

    }

    private boolean isEmailValid(String email) {
        boolean isGoodEmail = (email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches());

        if (!isGoodEmail) {
            mEditTextEmailCreate.setError(String.format(getResources().getString(R.string.error_invalid_email_not_valid), email));
            return false;
        }

        return isGoodEmail;
    }

    private boolean isUserNameValid(String userName) {
        if (userName.equals("")) {
            mEditTextUsernameCreate.setError(getResources().getString(R.string.error_cannot_be_empty));
            return false;
        }
        return true;
    }

    private boolean isPasswordValid(String password) {
        if (password.length() < 6) {
            mEditTextPasswordCreate.setError(getResources().getString(R.string.error_invalid_password_not_valid));
            return false;
        }
        return true;
    }

    /**
     * Show error toast to users
     */
    private void showErrorToast(String message) {
        Toast.makeText(CreateAccountActivity.this, message, Toast.LENGTH_LONG).show();
    }
}
