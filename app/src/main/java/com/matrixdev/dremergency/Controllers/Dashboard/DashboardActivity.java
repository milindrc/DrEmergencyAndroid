package com.matrixdev.dremergency.Controllers.Dashboard;

import android.content.DialogInterface;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.matrixdev.dremergency.Application;
import com.matrixdev.dremergency.Controllers.Authentication.HomeActivity;
import com.matrixdev.dremergency.Controllers.BaseActivity;
import com.matrixdev.dremergency.Helpers.DialogHelper;
import com.matrixdev.dremergency.Helpers.SharedPreferencesHelper;
import com.matrixdev.dremergency.Helpers.SosHelper;
import com.matrixdev.dremergency.Models.Authentication.LoginData;
import com.matrixdev.dremergency.Models.Problem.ProblemData;
import com.matrixdev.dremergency.Models.Problem.ProblemResponse;
import com.matrixdev.dremergency.R;
import com.matrixdev.dremergency.Utils.Constants;
import com.matrixdev.dremergency.web.ApiManager;
import com.matrixdev.dremergency.web.ResponseHandler;
import com.matrixdev.dremergency.web.ServerResponseListener;

import java.util.ArrayList;
import java.util.HashMap;


public class DashboardActivity extends BaseActivity implements ServerResponseListener{

    private static final String DEVICE = "device";
    private static final String PROBLEMS = "problems";
    private Toolbar toolbar;
    private ListView navDrawer;
    private ImageView navDrawerButton;
    private DrawerLayout drawer;
    private TextView toolbarTitle;
    private NavigationDrawerAdapter adapter;
    private View sos;
    private RecyclerView dashboardTiles;
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<ProblemData> problems;
    private ProblemAdapter problemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard2);

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
        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SosHelper.sendSOS(DashboardActivity.this,getLoginData());
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchProblems();
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
        sos = findViewById(R.id.sos);

        dashboardTiles = (RecyclerView) findViewById(R.id.card_tiles);
        dashboardTiles.setLayoutManager(new LinearLayoutManager(this));
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.card_refresh);

        checkBlacklist();
        fetchProblems();
    }

    private void checkBlacklist() {
        if(getLoginData().getBlacklist()==1){
            DialogHelper.with(this).showFixed("Blacklisted", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            },"You are now blacklisted , and can't use this app");
        }
    }

    private void fetchProblems() {
        ApiManager apiManager = new ApiManager(this,this);
        HashMap<String,String> param = new HashMap<>();
        param.put(Constants.PARAM_USER_ID, String.valueOf(getLoginData().getId()));

        apiManager.doJsonParsing(true);
        apiManager.setClassTypeForJson(ProblemResponse.class);

        apiManager.getStringPostResponse(PROBLEMS,Constants.URL_PROBLEMS,param);
        startLoader();
    }

    private void updateToken(String deviceData) {
        if(SharedPreferencesHelper.getLoginData()==null || (getLoginData().getDevice()!=null && getLoginData().getDevice().equals(deviceData)))
            return;

        LoginData user = SharedPreferencesHelper.getLoginData();

        ApiManager apiManager = new ApiManager(Application.getContext(),this);

        HashMap<String,String> params = new HashMap<>();
        params.put(Constants.PARAM_USER_ID, String.valueOf(user.getId()));
        params.put(Constants.PARAM_DEVICE,deviceData);

        apiManager.getStringPostResponse(DEVICE,Constants.URL_UPDATE_DEVICE,params);
    }

    @Override
    public void positiveResponse(String TAG, String response) {

    }

    @Override
    public void positiveResponse(String TAG, final Object responseObj) {
        stopLoader();
        refreshLayout.setRefreshing(false);
        if(TAG.equals(PROBLEMS))
        {
            ResponseHandler.handleResponse(responseObj, new ResponseHandler.ResponseCode() {
                @Override
                public void preformCode() {
                    ProblemResponse response = (ProblemResponse) responseObj;
                    problems = response.getData();
                    showProblems();
                }
            });
        }

    }

    private void showProblems() {
        problemAdapter = new ProblemAdapter(this,problems);
        dashboardTiles.setLayoutManager(new LinearLayoutManager(this));
        dashboardTiles.setAdapter(problemAdapter);
    }

    @Override
    public void negativeResponse(String TAG, String errorResponse) {

    }
}
