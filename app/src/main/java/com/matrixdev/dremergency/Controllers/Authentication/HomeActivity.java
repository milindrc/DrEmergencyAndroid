package com.matrixdev.dremergency.Controllers.Authentication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.matrixdev.dremergency.Controllers.BaseActivity;
import com.matrixdev.dremergency.Controllers.Dashboard.DashboardActivity;
import com.matrixdev.dremergency.Controllers.Dashboard.DoctorDashboard;
import com.matrixdev.dremergency.Helpers.FragmentSwitcher;
import com.matrixdev.dremergency.Helpers.SharedPreferencesHelper;
import com.matrixdev.dremergency.Helpers.SocialLoginHelper;
import com.matrixdev.dremergency.Helpers.SosHelper;
import com.matrixdev.dremergency.Models.Authentication.LoginData;
import com.matrixdev.dremergency.Models.Authentication.LoginResponse;
import com.matrixdev.dremergency.Models.Config.ConfigResponse;
import com.matrixdev.dremergency.R;
import com.matrixdev.dremergency.Utils.Constants;
import com.matrixdev.dremergency.Utils.ToastHelper;
import com.matrixdev.dremergency.web.ApiManager;
import com.matrixdev.dremergency.web.ResponseHandler;
import com.matrixdev.dremergency.web.ServerResponseListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class HomeActivity extends BaseActivity implements ServerResponseListener {

    private static final String CONFIG = "config";
    public static final int RC_SIGN_IN = 1;
    private static final String CHECK = "check";
    private static final String SOS = "sos";
    private FrameLayout frame;
    private RelativeLayout rootView;
    private ImageView bckImage;
    private TextView txtView;
    private ObjectAnimator fadeOut;

    public LoginData createUser;
    public boolean userDataCheck = false;
    public boolean splashCheck = false;
    public boolean configCheck = false;
    private Intent dashboardIntent;
    public CallbackManager mCallbackManager;
    public View sos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initUI();
        setListeners();
//        BlurKit.init(this);
//        hashGenerate();
    }

    private void setListeners() {
        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SosHelper.sendSOS(HomeActivity.this, getLoginData());
            }
        });
    }

    private void hashGenerate() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.sdkhightech.gocardpro",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("----KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    private void initUI() {
        configCheck = true;
        if (SharedPreferencesHelper.getLoginData() != null) {
            loginData = SharedPreferencesHelper.getLoginData();
            dashboardIntent = new Intent(this, loginData.getRoleId() == 1 ? DashboardActivity.class : DoctorDashboard.class);
            dashboardIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            String target = getIntent().getStringExtra(Constants.INTENT_HOME_TARGET);

//            if (target != null && !target.equals("") && target.trim().length() != 0)
//                dashboardIntent.putExtra(Constants.INTENT_DASHBOARD_TARGET, target);
            userDataCheck = true;
            configCheck = true;
        }

        mCallbackManager = CallbackManager.Factory.create();
        txtView = (TextView) findViewById(R.id.textV);
        rootView = (RelativeLayout) findViewById(R.id.root_layout);
        frame = (FrameLayout) findViewById(R.id.home_frame);
        sos = findViewById(R.id.sos);
        fragmentSwitcher = new FragmentSwitcher(this);
//        fetchConfig();

        fragmentSwitcher.moveToSplash();
        bckImage = (ImageView) rootView.findViewById(R.id.background_image);
    }

    private void fetchConfig() {
        ApiManager apiManager = new ApiManager(this, this);

        apiManager.doJsonParsing(true);
        apiManager.setClassTypeForJson(ConfigResponse.class);

        HashMap<String, String> param = new HashMap<>();
        param.put(Constants.PARAM_USER_ID, String.valueOf(getLoginData() == null ? "0" : getLoginData().getId()));

        apiManager.getStringPostResponse(CONFIG, Constants.URL_CONFIG, param);
    }

    public void offSplash() {
        Animation a = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.zoom);
        a.setFillAfter(true);
        bckImage.startAnimation(a);

        if (fadeOut.isRunning())
            fadeOut.cancel();

        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(txtView, "alpha", 1f, 0f);
        fadeOut.setDuration(1000);
        fadeOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                txtView.setAlpha(0f);
            }
        });
        fadeOut.start();
    }


    public void onSplash() {
        Animation a = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.zoom_out);
        a.setFillAfter(true);
        bckImage.startAnimation(a);

        fadeOut = ObjectAnimator.ofFloat(txtView, "alpha", 0f, 1f);
        fadeOut.setDuration(3000);
        fadeOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                splashCheck = true;
                if (userDataCheck && configCheck)
                    startActivity(dashboardIntent);
            }
        });
        fadeOut.start();

    }

    public void checkUser() {
        ApiManager apiManager = new ApiManager(this, this);

        apiManager.doJsonParsing(true);
        apiManager.setClassTypeForJson(LoginResponse.class);

        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.PARAM_EMAIL, getCreateUser().getEmail());
        apiManager.getStringPostResponse(CHECK, Constants.URL_GET_USER, params);

        startLoader();
    }


    @Override
    public void positiveResponse(String TAG, String response) {

    }

    @Override
    public void positiveResponse(String TAG, final Object responseObj) {
        stopLoader();
        if (TAG.equals(CONFIG)) {

            ConfigResponse response = (ConfigResponse) responseObj;
            if (response.isSuccess()) {
                configCheck = true;

                SharedPreferencesHelper.saveConfig(response.getData());
                BaseActivity.configData = response.getData();

                if (userDataCheck && splashCheck)
                    startActivity(dashboardIntent);
            }
        }
        if(TAG.equals(SOS))
        {
            ResponseHandler.handleResponse(responseObj, new ResponseHandler.ResponseCode() {
                @Override
                public void preformCode() {
                    ToastHelper.toast("Success. We are trying to connect you with nearby docter");
                }
            });
        }
        if (TAG.equals(CHECK)) {
            LoginResponse response = (LoginResponse) responseObj;
            if (response.isSuccess()) {
                loginData = response.getData();

                SharedPreferencesHelper.saveLoginData(response.getData());

                Intent intent = new Intent(this, DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                if (getCreateUser() != null) {
                    getFragmentSwitcher().moveToRegister();
                }
            }
        }

    }


    @Override
    public void negativeResponse(String TAG, String errorResponse) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);       //fb
        if (requestCode == RC_SIGN_IN) {
            try {
                GoogleSignInAccount user = SocialLoginHelper.getGoogleAccount(data);
                if (user != null && user.getDisplayName() != null && user.getEmail() != null) {
                    LoginData loginData = new LoginData();
                    loginData.setName(user.getDisplayName());
                    loginData.setEmail(user.getEmail());
                    setCreateUser(loginData);
                    checkUser();
                }

            } catch (ApiException e) {
                e.printStackTrace();
            }

        }



    }


    public LoginData getCreateUser() {
        return createUser;
    }

    public void setCreateUser(LoginData createUser) {
        this.createUser = createUser;
    }
}
