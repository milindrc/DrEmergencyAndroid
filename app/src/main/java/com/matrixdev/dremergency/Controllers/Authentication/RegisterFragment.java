package com.matrixdev.dremergency.Controllers.Authentication;


import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.matrixdev.dremergency.Controllers.BaseActivity;
import com.matrixdev.dremergency.Controllers.Dashboard.DashboardActivity;
import com.matrixdev.dremergency.Controllers.Dashboard.DoctorDashboard;
import com.matrixdev.dremergency.Helpers.SharedPreferencesHelper;
import com.matrixdev.dremergency.Models.Authentication.LoginResponse;
import com.matrixdev.dremergency.R;
import com.matrixdev.dremergency.Utils.Constants;
import com.matrixdev.dremergency.Utils.Validator;
import com.matrixdev.dremergency.web.ApiManager;
import com.matrixdev.dremergency.web.ResponseHandler;
import com.matrixdev.dremergency.web.ServerResponseListener;
import com.schibstedspain.leku.LocationPickerActivity;

import java.lang.reflect.Field;
import java.util.HashMap;

import static com.schibstedspain.leku.LocationPickerActivityKt.ADDRESS;
import static com.schibstedspain.leku.LocationPickerActivityKt.LATITUDE;
import static com.schibstedspain.leku.LocationPickerActivityKt.LOCATION_ADDRESS;
import static com.schibstedspain.leku.LocationPickerActivityKt.LONGITUDE;
import static com.schibstedspain.leku.LocationPickerActivityKt.TRANSITION_BUNDLE;
import static com.schibstedspain.leku.LocationPickerActivityKt.ZIPCODE;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements ServerResponseListener {


    private static final String REGISER = "register";
    private View rootView;
    private int id;
    private TextView emailField;
    private EditText mobileField;
    private EditText passwordField;
    private EditText rePasswordField;
    private Button registerButton;
    private TextInputLayout emailFieldLayout;
    private TextInputLayout mobileFieldLayout;
    private TextInputLayout rePasswordFieldLayout;
    private TextInputLayout passwordFieldLayout;
    private HomeActivity activity;
    private ApiManager apiManager;
    private int bloodId;
    private LinearLayout bloodGroupLayout;
    private EditText nameFielld;
    private TextInputLayout nameFieldLayout;
    private EditText ageField;
    private EditText designationField;
    private EditText addressField;
    private EditText locationField;
    private RadioButton genderMaleButton;
    private RadioButton genderFemaleButton;
    private RadioButton genderOthersButton;
    private RadioButton roleUserButton;
    private RadioButton roleDoctorButton;
    private TextInputLayout ageFieldLayout;
    private TextInputLayout designationFieldLayout;
    private TextInputLayout addressFieldLayout;
    private TextInputLayout locationFieldLayout;
    private RadioGroup genderFieldLayout;
    private RadioGroup roleFieldLayout;
    private TextInputLayout qualificationsFieldLayout;
    private EditText qualificationsField;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (HomeActivity) getActivity();
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_register, container, false);
        init(rootView);
        setListener();
        return rootView;
    }

    private void init(View rootView) {
        nameFielld = (EditText) rootView.findViewById(R.id.name_text_view);
        emailField = (EditText) rootView.findViewById(R.id.register_email);
        mobileField = (EditText) rootView.findViewById(R.id.register_mobile);
        passwordField = (EditText) rootView.findViewById(R.id.register_password);
        rePasswordField = (EditText) rootView.findViewById(R.id.register_re_password);
        ageField = (EditText) rootView.findViewById(R.id.register_age);
        designationField = (EditText) rootView.findViewById(R.id.register_designation);
        addressField = (EditText) rootView.findViewById(R.id.register_address);
        qualificationsField = (EditText) rootView.findViewById(R.id.register_qualifications);
        locationField = (EditText) rootView.findViewById(R.id.register_location);
        genderMaleButton = (RadioButton) rootView.findViewById(R.id.register_gender_male);
        genderFemaleButton = (RadioButton) rootView.findViewById(R.id.register_gender_female);
        genderOthersButton = (RadioButton) rootView.findViewById(R.id.register_gender_others);
        roleUserButton = (RadioButton) rootView.findViewById(R.id.register_role_user);
        roleDoctorButton = (RadioButton) rootView.findViewById(R.id.register_role_doctor);

        nameFieldLayout = (TextInputLayout) rootView.findViewById(R.id.name_layout);
        emailFieldLayout = (TextInputLayout) rootView.findViewById(R.id.email_roll_no_layout);
        mobileFieldLayout = (TextInputLayout) rootView.findViewById(R.id.register_mobile_layout);
        passwordFieldLayout = (TextInputLayout) rootView.findViewById(R.id.register_password_layout);
        rePasswordFieldLayout = (TextInputLayout) rootView.findViewById(R.id.register_re_password_layout);
        ageFieldLayout = (TextInputLayout) rootView.findViewById(R.id.register_age_layout);
        designationFieldLayout = (TextInputLayout) rootView.findViewById(R.id.register_designation_layout);
        addressFieldLayout = (TextInputLayout) rootView.findViewById(R.id.register_address_layout);
        qualificationsFieldLayout = (TextInputLayout) rootView.findViewById(R.id.register_qualifications_layout);
        locationFieldLayout = (TextInputLayout) rootView.findViewById(R.id.register_location_layout);
        genderFieldLayout = (RadioGroup) rootView.findViewById(R.id.register_gender_layout);
        roleFieldLayout = (RadioGroup) rootView.findViewById(R.id.register_role_layout);

        registerButton = (Button) rootView.findViewById(R.id.register_button);
        bloodGroupLayout = (LinearLayout) rootView.findViewById(R.id.blood_group_layout);

        if (activity.getCreateUser() != null) {
            nameFielld.setText(activity.getCreateUser().getName());
            emailField.setText(activity.getCreateUser().getEmail());
        }

        try {
            TelephonyManager tMgr = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                String mPhoneNumber = tMgr.getLine1Number();
                mobileField.setText(mPhoneNumber);
            }
            String mPhoneNumber = tMgr.getLine1Number();
            mobileField.setText(mPhoneNumber);

        }catch (Exception e)
        {e.printStackTrace();}
    }

    private void setListener() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    registerUser();
                }
            }
        });

        roleDoctorButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked)
                    qualificationsFieldLayout.setVisibility(View.VISIBLE);
                else
                    qualificationsFieldLayout.setVisibility(View.GONE);

            }
        });

        passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                passwordFieldLayout.setErrorEnabled(false);
                passwordFieldLayout.setError(null);
            }
        });
        emailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                emailFieldLayout.setErrorEnabled(false);
                emailFieldLayout.setError(null);
            }
        });
        mobileField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mobileFieldLayout.setErrorEnabled(false);
                mobileFieldLayout.setError(null);
            }
        });
        nameFielld.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                nameFieldLayout.setErrorEnabled(false);
                nameFieldLayout.setError(null);
            }
        });
        ageField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ageFieldLayout.setErrorEnabled(false);
                ageFieldLayout.setError(null);
            }
        });

        locationField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent locationPickerIntent = new LocationPickerActivity.Builder()
                        .withLocation(41.4036299, 2.1743558)
                        .withGeolocApiKey("AIzaSyDdZpCHuJOxF1GM-6cONvyw98Gb3OCKUMI")
//                        .withSearchZone("es_ES")
//                        .withGoogleTimeZoneEnabled()
                        .shouldReturnOkOnBackPressed()
                        .withStreetHidden()
                        .withCityHidden()
                        .withZipCodeHidden()
                        .withSatelliteViewHidden()
                        .withGooglePlacesEnabled()
//                        .withGoogleTimeZoneEnabled()
                        .withVoiceSearchHidden()
                        .build(activity);

                startActivityForResult(locationPickerIntent, activity.MAP_BUTTON_REQUEST_CODE);
//                activity.startActivity(new Intent(activity, MapsActivity.class));
            }
        });
    }

    private void registerUser() {

        activity.startLoader();
        apiManager = new ApiManager(activity, this);

        HashMap<String, String> params = new HashMap<>();
        params.put(Constants.PARAM_NAME, nameFielld.getText().toString());
        params.put(Constants.PARAM_EMAIL, emailField.getText().toString());
        params.put(Constants.PARAM_MOBILE, mobileField.getText().toString());
        params.put(Constants.PARAM_PASSWORD, passwordField.getText().toString());
        params.put(Constants.PARAM_AGE, ageField.getText().toString());
        params.put(Constants.PARAM_DESIGNATION, designationField.getText().toString());
        params.put(Constants.PARAM_QUALIFICATIONS, designationField.getText().toString());
        params.put(Constants.PARAM_GENDER, genderMaleButton.isChecked()?"Male":"Female");
        params.put(Constants.PARAM_ROLE_ID, roleUserButton.isChecked()?"1":"2");
        params.put(Constants.PARAM_LOCATION, locationField.getText().toString());
        params.put(Constants.PARAM_ADDRESS, addressField.getText().toString());

        apiManager.doJsonParsing(true);
        apiManager.setClassTypeForJson(LoginResponse.class);

        apiManager.getStringPostResponse(REGISER, Constants.URL_REGISTER, params);

    }

    private boolean validate() {
        boolean status = true;

        String name = nameFielld.getText().toString();
        String emailTxt = emailField.getText().toString();
        String mobileTxt = mobileField.getText().toString();
        String passwordTxt = passwordField.getText().toString();
        String rePasswordTxt = rePasswordField.getText().toString();
        String ageTxt = ageField.getText().toString();

        String mobileError = Validator.isValidPhoneNumber(mobileTxt);

        if (emailTxt.length() > 40 || emailTxt.length() < 9 || emailTxt.isEmpty()) {
            setError(emailFieldLayout, "Invalid Email.");
            emailField.getBackground().setColorFilter(getResources().getColor(R.color.transcluscent_red), PorterDuff.Mode.SRC_ATOP);
            status = false;
        }

        if (name.isEmpty()) {
            nameFieldLayout.setErrorEnabled(true);
            nameFieldLayout.setError("Name must not be empty");
            nameFielld.getBackground().setColorFilter(getResources().getColor(R.color.transcluscent_red), PorterDuff.Mode.SRC_ATOP);
            status = false;
        }
        if (ageTxt.isEmpty()) {
            ageFieldLayout.setErrorEnabled(true);
            ageFieldLayout.setError("Name must not be empty");
            ageField.getBackground().setColorFilter(getResources().getColor(R.color.transcluscent_red), PorterDuff.Mode.SRC_ATOP);
            status = false;
        }

        if (!mobileError.isEmpty()) {
            mobileFieldLayout.setErrorEnabled(true);
            mobileFieldLayout.setError(mobileError);
            mobileField.getBackground().setColorFilter(getResources().getColor(R.color.transcluscent_red), PorterDuff.Mode.SRC_ATOP);
            status = false;
        }

        if (!passwordTxt.equals(rePasswordTxt) || passwordTxt.isEmpty()) {
            passwordFieldLayout.setErrorEnabled(true);
            passwordFieldLayout.setError(passwordTxt.isEmpty() ? "Password must not be empty" : "Password Mismatch");
            passwordField.getBackground().setColorFilter(getResources().getColor(R.color.transcluscent_red), PorterDuff.Mode.SRC_ATOP);
            status = false;
        } else if (passwordTxt.length() < 4) {
            passwordFieldLayout.setErrorEnabled(true);
            passwordFieldLayout.setError("Password should be minimum 4 letters");
            passwordField.getBackground().setColorFilter(getResources().getColor(R.color.transcluscent_red), PorterDuff.Mode.SRC_ATOP);
            status = false;
        }


        return status;
    }

    public void setError(TextInputLayout inputLayout, String message) {
        inputLayout.setErrorTextAppearance(R.style.TextInputLayoutError);
        inputLayout.setError(message);
    }

    public void setMessage(TextInputLayout inputLayout, String message) {
        inputLayout.setErrorTextAppearance(R.style.TextInputLayout);
        inputLayout.setError(message);

    }

    public void setErrorTextColor(TextInputLayout textInputLayout, int color) {
        try {
            Field fErrorView = TextInputLayout.class.getDeclaredField("mErrorView");
            fErrorView.setAccessible(true);
            TextView mErrorView = (TextView) fErrorView.get(textInputLayout);
            Field fCurTextColor = TextView.class.getDeclaredField("mCurTextColor");
            fCurTextColor.setAccessible(true);
            fCurTextColor.set(mErrorView, color);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void positiveResponse(String TAG, String response) {

    }

    @Override
    public void positiveResponse(String TAG, final Object responseObj) {
        activity.stopLoader();
        ResponseHandler.handleResponse(responseObj, new ResponseHandler.ResponseCode() {
            @Override
            public void preformCode() {

                LoginResponse response = (LoginResponse) responseObj;
                BaseActivity.loginData = response.getData();

                SharedPreferencesHelper.saveLoginData(response.getData());

                Intent intent = new Intent(activity, response.getData().getRoleId()==1?DashboardActivity.class: DoctorDashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);

            }
        });
    }


    private int dpToPx(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }


    @Override
    public void negativeResponse(String TAG, String errorResponse) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            Log.d("RESULT****", "OK");
            if (requestCode == activity.MAP_BUTTON_REQUEST_CODE) {
                double latitude = data.getDoubleExtra(LATITUDE, 0.0);
                Log.d("LATITUDE****", String.valueOf(latitude));
                double longitude = data.getDoubleExtra(LONGITUDE, 0.0);
                Log.d("LONGITUDE****", String.valueOf(longitude));
                String address = data.getStringExtra(LOCATION_ADDRESS);
                Log.d("ADDRESS****", address);
                String postalcode = data.getStringExtra(ZIPCODE);
                Log.d("POSTALCODE****", postalcode);
                Bundle bundle = data.getBundleExtra(TRANSITION_BUNDLE);
                String fullAddress = data.getParcelableExtra(ADDRESS).toString();
                if (fullAddress != null) {
                    Log.d("FULL ADDRESS****", fullAddress.toString());
                }
                locationField.setText(String.format("%s,%s", latitude, longitude));
            }
        }

    }
}
