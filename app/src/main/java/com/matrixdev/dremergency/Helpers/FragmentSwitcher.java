package com.matrixdev.dremergency.Helpers;

import android.app.FragmentTransaction;

import com.matrixdev.dremergency.Controllers.Authentication.LoginFragment;
import com.matrixdev.dremergency.Controllers.Authentication.RegisterFragment;
import com.matrixdev.dremergency.Controllers.Authentication.SplashFragment;
import com.matrixdev.dremergency.Controllers.BaseActivity;
import com.matrixdev.dremergency.R;


/**
 * Created by Milind on 24-Oct-17.
 */

public class FragmentSwitcher {
    private static final String SPLASH = "splash";
    private static final String LOGIN = "login";
    private static final String REGISTER = "register";
    BaseActivity activity;
    android.app.FragmentManager fragmentManager;

    public FragmentSwitcher(BaseActivity activity) {
        this.activity = activity;
        fragmentManager = activity.getFragmentManager();
    }

    public void moveToSplash() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.home_frame, new SplashFragment(), SPLASH);
//        ft.addToBackStack(SPLASH);
        ft.commit();
    }

    public void moveToLogin() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.animator.enter_from_right, R.animator.exit_to_left, R.animator.enter_from_left, R.animator.exit_to_right);
        ft.replace(R.id.home_frame, new LoginFragment(), LOGIN);
        ft.addToBackStack(LOGIN);
        ft.commit();
    }

    public void moveToRegister() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.setCustomAnimations(R.animator.enter_from_right, R.animator.exit_to_left, R.animator.enter_from_left, R.animator.exit_to_right);
        ft.replace(R.id.home_frame, new RegisterFragment(), REGISTER);
        ft.addToBackStack(REGISTER);
        ft.commit();
    }


}
