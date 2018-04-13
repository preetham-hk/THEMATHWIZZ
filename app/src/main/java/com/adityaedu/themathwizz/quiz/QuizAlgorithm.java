package com.adityaedu.themathwizz.quiz;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adityaedu.themathwizz.R;
import com.adityaedu.themathwizz.activities.HomeActivity;
import com.adityaedu.themathwizz.fragments.AlertDialogFragment;
import com.adityaedu.themathwizz.fragments.DialogPopupFragment;
import com.adityaedu.themathwizz.fragments.ProgressDialogSpinner;
import com.adityaedu.themathwizz.helpers.AsyncFile;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by preetham on 2/28/2018.
 *
 */

public class QuizAlgorithm extends AppCompatActivity {

    ArrayList<String> questionsList = new ArrayList<>();
    ArrayList<String> optionList1 = new ArrayList<>();
    ArrayList<String> optionList2 = new ArrayList<>();
    ArrayList<String> optionList3 = new ArrayList<>();
    ArrayList<String> optionList4 = new ArrayList<>();
    ArrayList<String> answerList = new ArrayList<>();


    TextView Question_textView;
    RadioButton quiz_option1;
    RadioButton quiz_option2;
    RadioButton quiz_option3;
    RadioButton quiz_option4;
    AppCompatButton quiz_CheckAnswer;
    RadioGroup quiz_optionGroup;
    TextView quiz_topicName;
    TextView quiz_QCSize_TextView;
    TextView quiz_QSize_TextView;
    RelativeLayout relativeLayout;
    TextView quiz_difficulty;

    String Question = "question";
    String Option1 = "option1";
    String Option2 = "option2";
    String Option3 = "option3";
    String Option4 = "option4";
    String Answer = "answer";
    String selectedOption;
    ProgressDialog progressDialog;

    int currentValue =0;
    int listItemPosition;

    Context context;
    Dialog dialogCorrect;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        this.setTitle("Quiz");
        context = this;

        Bundle bundle = getIntent().getExtras();
        final Integer passedValueForL0 = bundle.getInt("currentValueForL0");
        final Integer passedValueForL1 = bundle.getInt("currentValueForL1");
        final Integer passedValueForL2 = bundle.getInt("currentValueForL2");
        final String subTopic = bundle.getString("subTopic");
        final String className = bundle.getString("className");
        final int level = bundle.getInt("Level");


        Log.d("className",""+className);

        final int easyListItemPosition=passedValueForL0+ currentValue;
        Log.d("listItemPosition_E",""+easyListItemPosition);
        final int mediumListItemPosition= passedValueForL1 + currentValue;
        Log.d("listItemPosition_M",""+mediumListItemPosition);
        final int hardListItemPosition = passedValueForL2 + currentValue;
        Log.d("listItemPosition_H",""+hardListItemPosition);

        //int QTrackSize = listItemPosition + 1;
        //String QCSize = ((Integer)QTrackSize).toString();

        quiz_difficulty = findViewById(R.id.quiz_difficulty);

        //Score
        final Integer score1 = bundle.getInt("Score");
        final Integer currentScore1 = bundle.getInt("currentScore");
        final int TotalScore = currentScore1 + score1;
        Log.d("Total Score", "Current Score="+TotalScore);


        //Easy Questions
        final Integer EasyQuestion = bundle.getInt("EasyQ");
        Integer CurrentEasyQuestion = bundle.getInt("TotalEasyQuestion");
        final int TotalEasyQuestion = EasyQuestion + CurrentEasyQuestion;
        Log.d("TotalEasyQuestion",""+TotalEasyQuestion);

        //Score for Easy Questions
        final Integer EasyQScore = bundle.getInt("EasyQS");
        Integer CurrentEasyScore = bundle.getInt("TotalEasyQScore");
        final int TotalEasyScore = EasyQScore + CurrentEasyScore;
        Log.d("TotalEasyQuestionScore",""+TotalEasyScore);

        Log.d("extra",""+subTopic);
        quiz_topicName = findViewById(R.id.quiz_Topic_TextView);
        Question_textView = findViewById(R.id.quiz_Question_TextView);
        quiz_option1 = findViewById(R.id.quiz_option1);
        quiz_option2 = findViewById(R.id.quiz_option2);
        quiz_option3 = findViewById(R.id.quiz_option3);
        quiz_option4 = findViewById(R.id.quiz_option4);
        quiz_CheckAnswer= findViewById(R.id.quiz_CheckAnswer);
        quiz_optionGroup = findViewById(R.id.quiz_optionGroup);
        quiz_QCSize_TextView = findViewById(R.id.quiz_QCSize_TextView);
        quiz_QSize_TextView =findViewById(R.id.quiz_QSize_TextView);

        relativeLayout = findViewById(R.id.QTrack_layout);
        //quiz_QSize_TextView.setText(QCSize);
        quiz_topicName.setText(subTopic);

        String easy = "Easy";
        String medium = "Medium";
        String hard = "Hard";

        //itemPosition for level
        if (level == 0){
            listItemPosition = easyListItemPosition;
            quiz_difficulty.setText(easy);
        }

        else if (level == 1){
            listItemPosition = mediumListItemPosition;
            quiz_difficulty.setText(medium);
        }
        else if(level == 2){
            listItemPosition = hardListItemPosition;
            quiz_difficulty.setText(hard);
        }

        Log.d("listItemPosition_A",""+listItemPosition);

        progressDialog = ProgressDialogSpinner.showProgressDialog(this,"Loading Questions");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("TestingQuiz");
        query.whereEqualTo("subTopicName", subTopic);
        query.whereEqualTo("Level",level);

        //query.fromLocalDataStore();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> questions, ParseException e) {

                if (questions.size() > 0) {
                    Log.d("size", "" + questions.size());
                    if (questions.size() > listItemPosition) {
                        if (e == null) {
                            for (ParseObject question : questions) {
                                questionsList.add(question.getString(Question));
                                optionList1.add(question.getString(Option1));
                                optionList2.add(question.getString(Option2));
                                optionList3.add(question.getString(Option3));
                                optionList4.add(question.getString(Option4));
                                answerList.add(question.getString(Answer));
                                try {
                                    Question_textView.setText(questionsList.get(listItemPosition));
                                    quiz_option1.setText(optionList1.get(listItemPosition));
                                    quiz_option2.setText(optionList2.get(listItemPosition));

                                    String option3 = optionList3.get(listItemPosition);
                                    String option4 = optionList4.get(listItemPosition);

                                    if (option3 != null && !option3.isEmpty()){
                                        quiz_option3.setText(optionList3.get(listItemPosition));
                                    }
                                    else {
                                        quiz_option3.setVisibility(View.GONE);
                                    }

                                    if (option4 != null && !option4.isEmpty()){
                                        quiz_option4.setText(optionList4.get(listItemPosition));
                                    }
                                    else {
                                        quiz_option4.setVisibility(View.GONE);
                                    }

                                    AsyncFile asyncFile = new AsyncFile();
                                    Bitmap bitmap = asyncFile.doInBackground("Quiz","subTopicName", subTopic,"question",questionsList.get(listItemPosition),"QuizImageFile");

                                    ImageView imageView = findViewById(R.id.quiz_questionImage);
                                    if (bitmap != null){
                                        imageView.setImageBitmap(bitmap);
                                    }
                                    else {
                                        imageView.setVisibility(View.GONE);
                                    }

                                    progressDialog.dismiss();
                                } catch (Exception e3) {
                                    e3.getMessage();
                                }

                                quiz_CheckAnswer.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        quiz_optionGroup = findViewById(R.id.quiz_optionGroup);
                                        Integer selected = quiz_optionGroup.getCheckedRadioButtonId();
                                        if (selected < 0) {
                                            Toast.makeText(getApplicationContext(), "Please Select Any one Option", Toast.LENGTH_SHORT).show();
                                        } else {
                                            String Answer = answerList.get(listItemPosition);
                                            Log.d("Answer", "" + Answer);
                                            selectedOption = ((RadioButton) findViewById(quiz_optionGroup.getCheckedRadioButtonId())).getText().toString();
                                            Log.d("selected", "" + selectedOption);
                                            //
                                            if (selectedOption.equals(Answer)) {
                                                //Dialog
                                                final Dialog dialogCorrect = DialogPopupFragment.showDialog(QuizAlgorithm.this);
                                                dialogCorrect.setContentView(R.layout.dialog_quiz_answer);
                                                TextView correctText = dialogCorrect.findViewById(R.id.correctText);
                                                String message = "Correct";
                                                correctText.setText(message);
                                                correctText.setTextColor(getResources().getColor(R.color.lightGreen));
                                                Button buttonNext = dialogCorrect.findViewById(R.id.dialogNext);
                                                String buttonText = "Next Question";
                                                buttonNext.setText(buttonText);
                                                //buttonNext.setBackgroundColor(getResources().getColor(R.color.green));
                                                buttonNext.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        Log.d("Level",""+level);
                                                        if(level == 0){
                                                            startNextQuestion(subTopic,className,level,1,easyListItemPosition,"currentValueForL0",
                                                                    "currentValueForL1", passedValueForL1,"currentValueForL2",passedValueForL2,
                                                                    1,TotalScore,
                                                                    1,TotalEasyQuestion,
                                                                    1,TotalEasyScore);
                                                            dialogCorrect.dismiss();
                                                        }
                                                        else  if (level == 1){
                                                            startNextQuestion(subTopic,className,level,1,mediumListItemPosition,"currentValueForL1",
                                                                    "currentValueForL0", passedValueForL0,"currentValueForL2",passedValueForL2,
                                                                    1,TotalScore,
                                                                    0,TotalEasyQuestion,
                                                                    0,TotalEasyScore);
                                                            dialogCorrect.dismiss();
                                                        }
                                                        else  if (level == 2){

                                                            startNextQuestion(subTopic,className,level,0,hardListItemPosition,"currentValueForL2",
                                                                    "currentValueForL0", passedValueForL0,"currentValueForL1",passedValueForL1,
                                                                    1,TotalScore,
                                                                    0,TotalEasyQuestion,
                                                                    0,TotalEasyScore);
                                                            dialogCorrect.dismiss();

                                                        }
                                                    }
                                                });

                                            } else {

                                                dialogCorrect = DialogPopupFragment.showDialog(QuizAlgorithm.this);
                                                dialogCorrect.setContentView(R.layout.dialog_quiz_answer);
                                                TextView correctText = dialogCorrect.findViewById(R.id.correctText);
                                                String message = "Wrong";
                                                correctText.setText(message);
                                                correctText.setTextColor(getResources().getColor(R.color.red));
                                                TextView answerText = dialogCorrect.findViewById(R.id.dialog_Answer_textView);
                                                String dialogAnswer = "Answer is: " + Answer;
                                                answerText.setText(dialogAnswer);
                                                Button buttonNext = dialogCorrect.findViewById(R.id.dialogNext);
                                                String buttonText = "Next Question";
                                                buttonNext.setText(buttonText);
                                                buttonNext.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        progressDialog = ProgressDialogSpinner.showProgressDialog(QuizAlgorithm.this, "Please Wait");
                                                        if(level == 0){
                                                            startNextQuestion(subTopic,className,level,0,easyListItemPosition, "currentValueForL0",
                                                                    "currentValueForL1",passedValueForL1,"currentValueForL2",passedValueForL2,
                                                                    0,TotalScore,
                                                                    1,TotalEasyQuestion,
                                                                    0,TotalEasyScore);
                                                            dialogCorrect.dismiss();

                                                        }
                                                        else  if (level == 1){
                                                            startNextQuestion(subTopic,className,level,-1,mediumListItemPosition, "currentValueForL1",
                                                                    "currentValueForL0", passedValueForL0, "currentValueForL2",passedValueForL2,
                                                                    0,TotalScore,
                                                                    0,TotalEasyQuestion,
                                                                    0,TotalEasyScore);
                                                            dialogCorrect.dismiss();

                                                        }
                                                        else  if (level == 2){

                                                            startNextQuestion(subTopic,className,level,-1,hardListItemPosition, "currentValueForL2",
                                                                    "currentValueForL0", passedValueForL0, "currentValueForL1",passedValueForL1,
                                                                    0,TotalScore,
                                                                    0,TotalEasyQuestion,
                                                                    0,TotalEasyScore);
                                                            dialogCorrect.dismiss();

                                                        }

                                                    }
                                                });

                                            }
                                        }
                                    }
                                });

                            }

                        } else {
                            e.printStackTrace();
                        }
                    } else {

                        Log.d("size", "" + questions.size());
                        Log.d("Sub topic",""+subTopic);
                        Log.d("Score",""+TotalScore);
                        progressDialog.dismiss();

                        QuizHelper activityQuizScore = new QuizHelper();
                        activityQuizScore.saveToRecentActivity(subTopic , TotalScore);

                        String noContent = " Congratulation, You completed the Quiz. Score is " + TotalScore;
                        Log.d("EndTotalScore",""+TotalScore);
                        Log.d("EndTotalEasyQ",""+ TotalEasyQuestion);
                        Log.d("EndTotalEasyQS",""+TotalEasyScore);
                        Question_textView.setText(noContent);
                        quiz_optionGroup.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.GONE);
                        progressDialog.dismiss();

                    }

                }
                else {
                    Log.d("size",""+questions.size());
                    String noContent = "Currently content is not available for this topic";
                    Question_textView.setText(noContent);
                    quiz_optionGroup.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
            }


        });

    }

    private void startNextQuestion(String subTopic, String className, int level, int levelValue, int levelItemList, String currentValueForLC,
                                   String currentValueForLP,int passedValueFor1 , String currentValueForLN, int passedValueFor2,
                                   int score, int TotalScore,
                                   int EasyQ, int TotalEasyQ,
                                   int EasyQS, int TotalEasyQS
                                    ){
        progressDialog = ProgressDialogSpinner.showProgressDialog(QuizAlgorithm.this, "Please Wait");
        Intent intent = new Intent(getApplicationContext(), QuizAlgorithm.class);
        intent.putExtra("subTopic", subTopic);
        intent.putExtra("className",className);
        int NextLevel = level + levelValue;
        intent.putExtra("Level",NextLevel);
        currentValue++;
        int c3 = currentValue + levelItemList;
        Log.d("levelItem",""+levelItemList);
        Log.d("value of c3", "" + c3);
        intent.putExtra(currentValueForLC, c3);
        Log.d("currentValueForLC", ""+c3);
        intent.putExtra(currentValueForLP,passedValueFor1);
        Log.d("currentValueForLC", ""+passedValueFor1);
        intent.putExtra(currentValueForLN,passedValueFor2);
        Log.d("currentValueForLC", ""+passedValueFor2);

        //Score
        intent.putExtra("Score", score);
        intent.putExtra("currentScore", TotalScore);

        //Score for Level0
        intent.putExtra("EasyQ",EasyQ);
        intent.putExtra("TotalEasyQuestion",TotalEasyQ);
        intent.putExtra("EasyQS",EasyQS);
        intent.putExtra("TotalEasyQScore",TotalEasyQS);

        startActivity(intent);
        progressDialog.dismiss();
        finish();
    }

    @Override
    public void onBackPressed() {

        AlertDialogFragment.showAlertDialog(QuizAlgorithm.this, "Cancel", "Yes", "Do you want to end the Quiz?",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                        finish();
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                }
        );
    }
}