package com.example.celineneohpeiwen.pb_3;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.celineneohpeiwen.pb_3.Common.Common;
import com.example.celineneohpeiwen.pb_3.DbHelper.DbHelper;
import com.example.celineneohpeiwen.pb_3.Model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;

public class Playing extends AppCompatActivity implements View.OnClickListener {

    final static long INTERVAL = 1000; //1 second
    final static long TIMEOUT = 5500; // 5.5 second
    int progressValue = 0;
    MediaPlayer trueMP;
    MediaPlayer falseMP;

    CountDownTimer mCountDown; //for progressbar
    List<Question> questionPlay = new ArrayList<>(); //total Question
    DbHelper db;

    //different
    int totalQuestion = 10;
    int mScore = 0;
    int mQuestionNumber = 0;
    int index = 0;
    String level = "";

    //Control //different
    TextView mCountLabel;
    TextView mScoreLabel;
    ProgressBar progressBar;
    ImageView mPicLabel;
    TextView mQuestionLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        //Get data from MainActivity
        Bundle extra = getIntent().getExtras();
        if (extra != null)
            level = extra.getString("LEVEL");

        db = new DbHelper(this);

        mCountLabel = (TextView) findViewById(R.id.countLabel);
        mScoreLabel = (TextView) findViewById(R.id.scoreLabel);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mPicLabel = (ImageView) findViewById(R.id.picLabel);
        mQuestionLabel = (TextView) findViewById(R.id.questionLabel);


        Button mbtnFalse = (Button) findViewById(R.id.btnFalse);
        Button mbtnTrue = (Button) findViewById(R.id.btnTrue);

        mbtnFalse.setOnClickListener(this);
        mbtnTrue.setOnClickListener(this);

        falseMP = MediaPlayer.create(this,R.raw.false_sound);
        trueMP = MediaPlayer.create(this,R.raw.true_sound);



    }


    @Override
    protected void onResume() {
        super.onResume();

        questionPlay = db.getAllQuestion();
        totalQuestion = questionPlay.size();


        mCountDown = new CountDownTimer(TIMEOUT, INTERVAL) {

            @Override
            public void onTick(long millisUntilFinished) {
                //timer
                progressBar.setProgress(progressValue);
                progressValue++;
            }

            @Override
            public void onFinish() {
                mCountDown.cancel();
                Toast.makeText(Playing.this,"Timeout!!!\n Answer: " +(questionPlay.get(index).getCorrectPB()),Toast.LENGTH_SHORT).show();
                showQuestion(++index);
            }
        };
        showQuestion(index);

    }


    private void showQuestion(int index) {
        if (index < totalQuestion) {
            mQuestionNumber++;
            mCountLabel.setText("Q:" + (mQuestionNumber) + "/10");
            progressBar.setProgress(0);
            progressValue = 0;

            int ImageId = this.getResources().getIdentifier(questionPlay.get(index).getImages().toLowerCase(), "drawable", getPackageName());
            mPicLabel.setBackgroundResource(ImageId);
            mQuestionLabel.setText(questionPlay.get(index).getQuestions());

            mCountDown.start();
        } else {
            Intent intent = new Intent(this, Done.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("SCORE", mScore);
            dataSend.putInt("TOTAL", totalQuestion);

            intent.putExtras(dataSend);
            startActivity(intent);
            finish();

        }
    }


    private void updateScore(int mScore) {
        mScoreLabel.setText("Score:" + mScore);
    }

    @Override
    public void onClick(View v) {
        mCountDown.cancel();
        if (index<totalQuestion){
            Button clickedButton = (Button)v;

            if (clickedButton.getText().equals(questionPlay.get(index).getAnswers())) {
                trueMP.start();
                mScore++;
                Toast.makeText(Playing.this,"CORRECT!!!\n Answer: " +(questionPlay.get(index).getCorrectPB()),Toast.LENGTH_SHORT).show();
            }
            else {
                falseMP.start();
                Toast.makeText(Playing.this,"WRONG!!!\n Answer: " +(questionPlay.get(index).getCorrectPB()),Toast.LENGTH_SHORT).show();
            }
            updateScore(mScore);
        }
        showQuestion(++index);
    }

}
