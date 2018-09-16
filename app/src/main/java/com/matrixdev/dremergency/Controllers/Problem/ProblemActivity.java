package com.matrixdev.dremergency.Controllers.Problem;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.matrixdev.dremergency.Controllers.BaseActivity;
import com.matrixdev.dremergency.Models.Problem.ProblemData;
import com.matrixdev.dremergency.R;
import com.matrixdev.dremergency.Utils.Constants;

public class ProblemActivity extends BaseActivity {

    private ProblemData problemData;
    private ImageView problemImage;
    private TextView problemName;
    private TextView problemDescription;
    private ViewPager problemPager;
    private ProblemPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);

        problemData =(ProblemData) getIntent().getParcelableExtra(Constants.INTENT_PROBLEM);
        initUI();

    }

    private void initUI() {
        problemImage = (ImageView) findViewById(R.id.problem_image);
        problemName = (TextView) findViewById(R.id.problem_name);
        problemDescription = (TextView) findViewById(R.id.problem_description);

        if(problemData!=null)
        {
            problemName.setText(problemData.getName());
            problemDescription.setText(problemData.getDescription());
            if (problemData.getProfile() != null && problemData.getProfile().length() != 0) {
                if (problemData.getProfile().contains(","))
                    Glide.with(this).load(problemData.getProfile().split(",")[0]).into(problemImage);
                else
                    Glide.with(this).load(problemData.getProfile()).into(problemImage);
            } else
                problemImage.setImageDrawable(null);
        }

        problemPager = (ViewPager) findViewById(R.id.problem_pager);
        pagerAdapter = new ProblemPagerAdapter(getSupportFragmentManager(),problemData);
        problemPager.setAdapter(pagerAdapter);
    }

    public ProblemData getProblemData() {
        return problemData;
    }

    public void setProblemData(ProblemData problemData) {
        this.problemData = problemData;
    }
}
