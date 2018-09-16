package com.matrixdev.dremergency.Controllers.Authentication;


import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.matrixdev.dremergency.Controllers.Dashboard.DashboardActivity;
import com.matrixdev.dremergency.Controllers.Dashboard.DoctorDashboard;
import com.matrixdev.dremergency.Helpers.DialogHelper;
import com.matrixdev.dremergency.Helpers.SharedPreferencesHelper;
import com.matrixdev.dremergency.Helpers.SocialLoginHelper;
import com.matrixdev.dremergency.Models.Authentication.LoginData;
import com.matrixdev.dremergency.Models.Authentication.LoginResponse;
import com.matrixdev.dremergency.Models.CommonResponse;
import com.matrixdev.dremergency.R;
import com.matrixdev.dremergency.Utils.Constants;
import com.matrixdev.dremergency.Utils.ToastHelper;
import com.matrixdev.dremergency.web.ApiManager;
import com.matrixdev.dremergency.web.ResponseHandler;
import com.matrixdev.dremergency.web.ServerResponseListener;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements ServerResponseListener {


    private static final String LOGIN = "login_request";
    private static final String RESET_PASSWORD = "reset";
    private View rootView;
    private HomeActivity activity;
    private Button registerButton;
    private EditText usernameField;
    private EditText passwordField;
    private Button loginButton;
    private View needHelpBtn;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton googleLogin;
    private FirebaseAuth mAuth;
    private View fbLoginButton;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        activity = (HomeActivity) getActivity();
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        init(rootView);
        setListener();
        return rootView;
    }

    private void init(View rootView) {
        registerButton = (Button) rootView.findViewById(R.id.register_button);
        usernameField = (EditText) rootView.findViewById(R.id.login_username);
        passwordField = (EditText) rootView.findViewById(R.id.login_password);
        loginButton = (Button) rootView.findViewById(R.id.login_button);
        needHelpBtn = rootView.findViewById(R.id.need_help);
        googleLogin = (SignInButton) rootView.findViewById(R.id.google_sign_in);
        googleLogin.setSize(SignInButton.SIZE_ICON_ONLY);
        fbLoginButton = rootView.findViewById(R.id.facebook_sign_in);

        LoginManager.getInstance().logOut();
    }


    private void setListener() {
        SocialLoginHelper.init(new SocialLoginHelper.OnRecieveFbUser() {
            @Override
            public void onRecieve(FirebaseUser user) {
                if (user != null && user.getDisplayName() != null && user.getEmail() != null) {
                    LoginData loginData = new LoginData();
                    loginData.setName(user.getDisplayName());
                    loginData.setEmail(user.getEmail());
                    activity.setCreateUser(loginData);
                    activity.checkUser();
                }
            }
        }, activity.mCallbackManager, activity);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.getFragmentSwitcher().moveToRegister();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();

            }
        });

        needHelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogHelper.InputResponse inputResponse = new DialogHelper.InputResponse();
                DialogHelper.with(activity).showInputDialog("Enter Email",inputResponse, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        forgotPassword(inputResponse);
                    }
                });
            }
        });
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SocialLoginHelper.googleSignIn(activity,activity.RC_SIGN_IN);
            }
        });
        fbLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SocialLoginHelper.fbCurrentUser() != null) {
                    SocialLoginHelper.fbSignIn(SocialLoginHelper.fbCurrentUser(), activity);
                } else {
                    SocialLoginHelper.loadCredsAndSignIn(activity);
                }
            }
        });
    }

    private void forgotPassword(DialogHelper.InputResponse inputResponse) {
        ApiManager apiManager = new ApiManager(activity, this);

        apiManager.doJsonParsing(true);
        apiManager.setClassTypeForJson(CommonResponse.class);

        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.PARAM_EMAIL, inputResponse.getData());
        apiManager.getStringPostResponse(RESET_PASSWORD, Constants.URL_RESET_PASS, params);

        activity.startLoader();
    }

    private void attemptLogin() {
        ApiManager apiManager = new ApiManager(activity, this);

        apiManager.doJsonParsing(true);
        apiManager.setClassTypeForJson(LoginResponse.class);

        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.PARAM_USERNAME, usernameField.getText().toString());
        params.put(Constants.PARAM_PASSWORD, passwordField.getText().toString());
        apiManager.getStringPostResponse(LOGIN, Constants.URL_LOGIN, params);

        activity.startLoader();
    }


    @Override
    public void positiveResponse(String TAG, String response) {

    }

    @Override
    public void positiveResponse(String TAG, final Object responseObj) {
        activity.stopLoader();
        if (TAG.equals(LOGIN)) {
            ResponseHandler.handleResponse(responseObj, new ResponseHandler.ResponseCode() {
                @Override
                public void preformCode() {
                    LoginResponse response = (LoginResponse) responseObj;
                    activity.loginData = response.getData();

                    SharedPreferencesHelper.saveLoginData(response.getData());

                    Intent intent = new Intent(activity, activity.loginData.getRoleId() == 1 ? DashboardActivity.class : DoctorDashboard.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                }
            });
        }
        if(TAG.equals(RESET_PASSWORD))
        {
            ResponseHandler.handleResponse(responseObj, new ResponseHandler.ResponseCode() {
                @Override
                public void preformCode() {
                    ToastHelper.toast("Recovery link sent to registered Email Id.");
                }
            });
        }
    }

    @Override
    public void negativeResponse(String TAG, String errorResponse) {
//        activity.stopLoader();
//        ToastHelper.toast(getString(R.string.negativeResponseMessage));
    }
}
