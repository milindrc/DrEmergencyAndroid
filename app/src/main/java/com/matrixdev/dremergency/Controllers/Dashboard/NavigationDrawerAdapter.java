package com.matrixdev.dremergency.Controllers.Dashboard;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.matrixdev.dremergency.Application;
import com.matrixdev.dremergency.Controllers.Authentication.HomeActivity;
import com.matrixdev.dremergency.Controllers.BaseActivity;
import com.matrixdev.dremergency.Helpers.DialogHelper;
import com.matrixdev.dremergency.Helpers.SharedPreferencesHelper;
import com.matrixdev.dremergency.Models.Authentication.LoginResponse;
import com.matrixdev.dremergency.R;
import com.matrixdev.dremergency.Utils.Constants;
import com.matrixdev.dremergency.Utils.ToastHelper;
import com.matrixdev.dremergency.web.ApiManager;
import com.matrixdev.dremergency.web.ServerResponseListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


/**
 * Created on 22-Feb-17.
 */
public class NavigationDrawerAdapter extends BaseAdapter implements ServerResponseListener {

    public BaseActivity activity;
    private static final String DEVICE = "device";


    enum Options {PROFILE, LOGOUT, CONTACT_US, ABOUT, EXIT}

    ArrayList<String> data = new ArrayList<String>(Arrays.asList("Logout", "Contact Us", "About", "Exit"));

    public NavigationDrawerAdapter(BaseActivity activity) {
        this.activity = activity;
    }

    public NavigationDrawerAdapter(DashboardActivity activity, ArrayList<String> data) {
        this.activity = activity;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = null;

        if (position == 0) {
            rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_profile, parent, false);
            ImageView profile = (ImageView) rootView.findViewById(R.id.profile_image);

            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent profilePage = new Intent(activity, ProfileActivity.class);
//                    profilePage.putExtra(Constants.INTENT_USER_DATA, activity.getLoginData());
//                    ActivityOptions options = ActivityOptions.makeCustomAnimation(view.getContext(),R.anim.slide_up,R.anim.slide_down);
//                    activity.startActivity(profilePage,options.toBundle());
                }
            });

            try {
                if (BaseActivity.getLoginData().getProfile() != null && BaseActivity.getLoginData().getProfile().length() != 0)
                    Glide.with(parent.getContext()).load(BaseActivity.getLoginData().getProfile()).into(profile);
            } catch (Exception e) {
                Log.d("----", e.getMessage());
            }
            ((TextView) rootView.findViewById(R.id.profile_name)).setText(BaseActivity.getLoginData().getName());
        } else {
            rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_drawer_item, parent, false);
            ((TextView) rootView.findViewById(R.id.nav_item)).setText(data.get(position - 1));
            setupListeners(rootView, position, parent.getContext());
        }


        return rootView;
    }

    private void setupListeners(View rootView, final int position, final Context context) {
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Options tile = Options.values()[position];

                switch (tile) {
//                    case 1:
//                        Intent intent = new Intent(context, Activity.class);
//                        context.startActivity(intent);
//                        break;
                    case LOGOUT:
                        clearDeviceToken();
                        break;
                    case CONTACT_US:
//                        Intent intent1 = new Intent(context, ContactUs.class);
//                        context.startActivity(intent1);
                        break;
                    case ABOUT:
//                        Intent intent2 = new Intent(context, AboutUsActivity.class);
//                        context.startActivity(intent2);
                        break;


                    case EXIT:
                        DialogHelper.with(activity).exit("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                (activity).finish();
                            }
                        }, " Do you want to exit ?");
                }
            }
        });
    }

    private void clearDeviceToken() {
        ApiManager apiManager = new ApiManager(Application.getContext(),this);

        apiManager.doJsonParsing(true);
        apiManager.setClassTypeForJson(LoginResponse.class);

        HashMap<String,String> params = new HashMap<>();
        params.put(Constants.PARAM_USER_ID, String.valueOf(BaseActivity.getLoginData().getId()));
        params.put(Constants.PARAM_DEVICE,"NULL");

        apiManager.getStringPostResponse(DEVICE,Constants.URL_UPDATE_DEVICE,params);

    }


    @Override
    public void positiveResponse(String TAG, String response) {

    }

    @Override
    public void positiveResponse(String TAG, Object responseObj) {
        if(TAG.equals(DEVICE))
        {
            LoginResponse response = (LoginResponse) responseObj;
            SharedPreferencesHelper.saveLoginData(null);
            Intent intent = new Intent(activity.getBaseContext(), HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.getBaseContext().startActivity(intent);
            ToastHelper.toast("Logged out!");

        }

    }

    @Override
    public void negativeResponse(String TAG, String errorResponse) {

    }



}
