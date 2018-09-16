package com.matrixdev.dremergency.Helpers;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

/**
 * Created by Milind on 7/10/2018.
 */

public class SocialLoginHelper {

    private static OnRecieveFbUser onRecieveFbUser;

    public static void fbSignIn(AccessToken accessToken, final Activity activity) {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("----", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                Log.d("----", user.getDisplayName() != null ? user.getDisplayName() : "No Name");
                                Log.d("----", user.getEmail() != null ? user.getEmail() : "No Email");
                                Log.d("----", user.getPhoneNumber() != null ? user.getPhoneNumber() : "No Mobile");
                                onRecieveFbUser.onRecieve(user);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("----", "signInWithCredential:failure", task.getException());
                            Toast.makeText(activity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public static void init(OnRecieveFbUser onRecieveFbUser, CallbackManager mCallbackManager, final Activity activity) {
        SocialLoginHelper.onRecieveFbUser = onRecieveFbUser;
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("----", "facebook:onSuccess:" + loginResult);
//                handleFacebookAccessToken(loginResult.getAccessToken());
                fbSignIn(loginResult.getAccessToken(), activity);
            }

            @Override
            public void onCancel() {
                Log.d("----", "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("----", "facebook:onError", error);
                // ...
            }
        });
    }

    public static AccessToken fbCurrentUser() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken;
    }

    public static void loadCredsAndSignIn(Activity activity) {
        LoginManager.getInstance().logInWithReadPermissions(
                activity,
                Arrays.asList( "email", "public_profile")
        );

    }

    public static void googleSignIn(Activity activity, int TAG) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);

//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(activity);
//        updateUI(account);

        mGoogleSignInClient.signOut();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        signInIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        activity.startActivityForResult(signInIntent, TAG);
    }

    public static GoogleSignInAccount getGoogleAccount(Intent data) throws ApiException {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        GoogleSignInAccount account = task.getResult(ApiException.class);
        return account;
    }

    public interface OnRecieveFbUser {
        void onRecieve(FirebaseUser user);
    }

}
