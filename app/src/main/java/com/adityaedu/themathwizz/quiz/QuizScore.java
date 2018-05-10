package com.adityaedu.themathwizz.quiz;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.adityaedu.themathwizz.R;

public class QuizScore extends AppCompatActivity {

    Context context;

    TextView Score_total_TextView,
            EasyQ_TextView,
            EasyQS_TextView,
            MediumQ_TextView,
            MediumQS_TextView,
            HardQ_TextView,
            HardQS_TextView;

    String mastery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizscore);
        this.setTitle("Quiz");
        context = this;

        Bundle bundle = getIntent().getExtras();
        Integer TotalScore = bundle.getInt("TotalScore");
        String subTopic = bundle.getString("subTopic");
        Integer TotalEasyQuestion = bundle.getInt("TotalEasyQuestion");
        Integer TotalEasyScore = bundle.getInt("TotalEasyScore");
        Integer TotalMediumQuestion = bundle.getInt("TotalMediumQuestion");
        Integer TotalMediumScore = bundle.getInt("TotalMediumScore");
        Integer TotalHardQuestion = bundle.getInt("TotalHardQuestion");
        Integer TotalHardScore = bundle.getInt("TotalHardScore");

        Score_total_TextView = findViewById(R.id.Score_total_TextView);
        EasyQ_TextView = findViewById(R.id.EasyQ_TextView);
        EasyQS_TextView = findViewById(R.id.EasyQS_TextView);
        MediumQ_TextView = findViewById(R.id.MediumQ_TextView);
        MediumQS_TextView = findViewById(R.id.MediumQS_TextView);
        HardQ_TextView= findViewById(R.id.HardQ_TextView);
        HardQS_TextView = findViewById(R.id.HardQS_TextView);

        float CompleteQuestions = TotalEasyQuestion + TotalMediumQuestion + TotalHardQuestion;
        float CompleteScore = TotalEasyScore + TotalMediumScore + TotalHardScore ;

        float percentage =  (CompleteScore / CompleteQuestions) * 100;


        if (percentage == 100.0){
            mastery = "Expert";
        }
        else if (percentage <100.0 && percentage >= 50.0)
        {
            mastery = "Intermediate";
        }
        else if (percentage < 50.0){
            mastery = "Novice";
        }

        Log.d("Q",""+CompleteQuestions);
        Log.d("A",""+CompleteScore);
        Log.d("percentage",""+percentage);


        QuizHelper activityQuizScore = new QuizHelper();
        activityQuizScore.saveToRecentActivity(subTopic , TotalScore , mastery);

        String noContent = " Congratulation, You completed the Quiz. Score is " + TotalScore;
        String EQ = "Total Easy Questions Attempted ="+TotalEasyQuestion ;
        String ES = "Easy Level Score = "+TotalEasyScore;
        String MQ = "Total Medium Questions Attempted = "+TotalMediumQuestion;
        String MS = "Medium Level Score = "+TotalMediumScore;
        String HQ = "Total Hard Questions Attempted = "+TotalHardQuestion;
        String HS = "Hard Level Score = "+TotalHardScore;

        Score_total_TextView.setText(noContent);
        EasyQ_TextView.setText(EQ);
        EasyQS_TextView.setText(ES);
        MediumQ_TextView.setText(MQ);
        MediumQS_TextView.setText(MS);
        HardQ_TextView.setText(HQ);
        HardQS_TextView.setText(HS);

        Log.d("EndTotalScore",""+TotalScore);
        Log.d("EndTotalEasyQ",""+ TotalEasyQuestion);
        Log.d("EndTotalEasyQS",""+TotalEasyScore);
        Log.d("EndTotalMediumQ",""+TotalMediumQuestion);
        Log.d("EndTotalMediumQS",""+TotalMediumScore);
        Log.d("EndTotalHardQ",""+TotalHardQuestion);
        Log.d("EndTotalHardQS",""+TotalHardScore);

    }
}
