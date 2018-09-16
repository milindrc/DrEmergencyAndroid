package com.matrixdev.dremergency.Controllers.Dashboard;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.matrixdev.dremergency.Application;
import com.matrixdev.dremergency.Controllers.BaseActivity;
import com.matrixdev.dremergency.Controllers.Dashboard.Doctor.DoctorDashboardAdapter;
import com.matrixdev.dremergency.Helpers.SharedPreferencesHelper;
import com.matrixdev.dremergency.Models.Authentication.LoginData;
import com.matrixdev.dremergency.R;
import com.matrixdev.dremergency.Utils.Constants;
import com.matrixdev.dremergency.web.ApiManager;
import com.matrixdev.dremergency.web.ServerResponseListener;

import java.util.HashMap;


public class DoctorDashboard extends BaseActivity implements ServerResponseListener {
    private static final String DEVICE = "device";
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private DrawerLayout drawer;
    private ListView navDrawer;
    private ImageView navDrawerButton;
    private NavigationDrawerAdapter adapter;
    private ViewPager doctorPage;
    private DoctorDashboardAdapter dashboardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_dashboard);
        initUI();
        setListeners();
        updateToken(SharedPreferencesHelper.getDeviceData());
    }

    private void setListeners() {
        navDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
    }

    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText(getLoginData().getName());


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navDrawer = (ListView) findViewById(R.id.left_drawer);
        adapter = new NavigationDrawerAdapter(this);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.navigation_drawer_item,new String[]{"Rate Us","Logout"});
        navDrawer.setAdapter(adapter);
        navDrawerButton = (ImageView) findViewById(R.id.nav_drawer_button);

        doctorPage = (ViewPager) findViewById(R.id.doctor_panel_pager);
        dashboardAdapter = new DoctorDashboardAdapter(getSupportFragmentManager());
        doctorPage.setAdapter(dashboardAdapter);
    }

    private void updateToken(String deviceData) {
        if(SharedPreferencesHelper.getLoginData()==null || (getLoginData().getDevice()!=null && getLoginData().getDevice().equals(deviceData)))
            return;

        LoginData user = SharedPreferencesHelper.getLoginData();

        ApiManager apiManager = new ApiManager(Application.getContext(),this);

        HashMap<String,String> params = new HashMap<>();
        params.put(Constants.PARAM_USER_ID, String.valueOf(user.getId()));
        params.put(Constants.PARAM_DEVICE,deviceData);

        apiManager.getStringPostResponse(DEVICE ,Constants.URL_UPDATE_DEVICE,params);
    }

    @Override
    public void positiveResponse(String TAG, String response) {

    }

    @Override
    public void positiveResponse(String TAG, Object responseObj) {

    }

    @Override
    public void negativeResponse(String TAG, String errorResponse) {

    }
}
