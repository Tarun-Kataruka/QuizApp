package com.example.quizapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quizapp.Models.QuestionModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.concurrent.TimeUnit;

public class ScoreActivity extends AppCompatActivity {

    private TextView scoreTV, timeTV, totalQTV, correctQTV, wrongQTV, unattemptedQTV;
    private Button leaderB;
    private long timeTaken;
    private Dialog progressDialog;
    private TextView dialogText;
    private int finalScore;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Result");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new Dialog(ScoreActivity.this);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogText = progressDialog.findViewById(R.id.dialog_test);
        dialogText.setText("Loading...");
        progressDialog.show();

        init();

        loadData();

        setBookMarks();

        saveResult();
    }

    private void init(){
        scoreTV=findViewById(R.id.score);
        timeTV=findViewById(R.id.time);
        totalQTV=findViewById(R.id.totalQ);
        correctQTV=findViewById(R.id.correctQ);
        wrongQTV=findViewById(R.id.wrong_ques);
        unattemptedQTV=findViewById(R.id.un_attempted);
        leaderB=findViewById(R.id.leaderB);
    }

    private void loadData()
    {
        int correctQ= 0, wrongQ =0, unattemptedQ =0;
        for(int i=0; i<DbQuery.g_quesList.size(); i++)
        {
            if(DbQuery.g_quesList.get(i).getSelectedAns()== -1)
            {
                unattemptedQ++;
            }
            else
            {
               if(DbQuery.g_quesList.get(i).getSelectedAns()== DbQuery.g_quesList.get(i).getCorrectAns())
               {
                   correctQ++;
               }
               else {
                   wrongQ++;
               }
            }
        }
        correctQTV.setText(String.valueOf(correctQ));
        wrongQTV.setText(String.valueOf(wrongQ));
        unattemptedQTV.setText(String.valueOf(unattemptedQ));

        totalQTV.setText(String.valueOf(DbQuery.g_quesList.size()));


         finalScore = correctQ;
        scoreTV.setText(String.valueOf(finalScore));

        timeTaken= getIntent().getLongExtra("TIME_TAKEN",0);
        String time = String.format("%02d : %02d min",
                TimeUnit.MILLISECONDS.toMinutes(timeTaken),
                TimeUnit.MILLISECONDS.toSeconds(timeTaken) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeTaken))
        );

        timeTV.setText(time);
    }

    private void saveResult()
    {
        DbQuery.saveResult(finalScore, new MyCompleteListener() {
            @Override
            public void onSuccess() {
                progressDialog.dismiss();
            }

            @Override
            public void onFailure() {
                Toast.makeText(ScoreActivity.this,"Somethiong went wrong!",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void setBookMarks()
    {
        for(int i=0; i<DbQuery.g_quesList.size(); i++)
        {
            QuestionModel question = DbQuery.g_quesList.get(i);
            if(question.isBookMarked())
            {
                if (! DbQuery.g_bmIdList.contains(question.getqID()))
                {
                    DbQuery.g_bmIdList.add(question.getqID());
                    DbQuery.myProfile.setBmCount(DbQuery.g_bmIdList.size());
                }
            }
            else {
                if(DbQuery.g_bmIdList.contains(question.getqID()))
                {
                    DbQuery.g_bmIdList.remove(question.getqID());
                    DbQuery.myProfile.setBmCount(DbQuery.g_bmIdList.size());
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            ScoreActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}