package com.matrixdev.dremergency.Controllers.Authentication;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.matrixdev.dremergency.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {


    private static final String LOGIN = "login";
    private static final String REGISTER = "register";
    private View rootView;
    private HomeActivity activity;
    private TextView txtView;
    private Button registerBtn;
    private Button signBtn;
    //    private MyImageView bckImage;
    private View authenticationPanel;
    private int height;
    private int duration = 1300;


    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = (HomeActivity) getActivity();
        rootView = inflater.inflate(R.layout.fragment_splash, container, false);
        initUI();
        setListeners();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        activity.onSplash();
        authenticationPanel.setAlpha(0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!activity.userDataCheck) {
                    up();
                    activity.sos.setVisibility(View.VISIBLE);
                }
            }
        }, duration);
    }

    private void setListeners() {
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                down(1);
                duration = 1300;
                activity.offSplash();
            }
        });
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                down(2);
                duration = 1300;
                activity.offSplash();
            }
        });
    }


    private void initUI() {
        registerBtn = (Button) rootView.findViewById(R.id.home_register_button);
        signBtn = (Button) rootView.findViewById(R.id.sign_btn);

        authenticationPanel = rootView.findViewById(R.id.authentication_panel);
    }

    public void up() {
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator rollUp = ObjectAnimator.ofFloat(authenticationPanel, "translationY", 250, 0);
        rollUp.setDuration(1000);
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(authenticationPanel, "alpha", 0f, 1f);
        fadeOut.setDuration(2000);
        fadeOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                authenticationPanel.setAlpha(1f);
            }
        });
        animatorSet.play(fadeOut).with(rollUp);
        animatorSet.start();

    }

    public void down(final int page) {
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator rollDown = ObjectAnimator.ofFloat(authenticationPanel, "translationY", 0, 250);
        rollDown.setDuration(1000);
        rollDown.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                switch (page) {
                    case 1:
                        activity.getFragmentSwitcher().moveToRegister();
                        break;
                    case 2:
                        activity.getFragmentSwitcher().moveToLogin();
                }
            }
        });
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(authenticationPanel, "alpha", 1f, 0f);
        fadeOut.setDuration(2000);
        fadeOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                authenticationPanel.setAlpha(0f);
            }
        });
        animatorSet.play(fadeOut).with(rollDown);
        animatorSet.start();
        activity.sos.setVisibility(View.GONE);
    }
}
