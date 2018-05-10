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

public class ActivityQuiz extends AppCompatActivity {

    ArrayList<String> questionsList = new ArrayList<>();
    ArrayList<String> optionList1 = new ArrayList<>();
    ArrayList<String> optionList2 = new ArrayList<>();
    ArrayList<String> optionList3 = new ArrayList<>();
    ArrayList<String> optionList4 = new ArrayList<>();
    ArrayList<String> answerList = new ArrayList<>();
    ArrayList<String>  objectIdList= new ArrayList<>();


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

    String Question = "question";
    String Option1 = "option1";
    String Option2 = "option2";
    String Option3 = "option3";
    String Option4 = "option4";
    String Answer = "answer";
    String selectedOption;
    ProgressDialog progressDialog;

    int currentValue =0;

    Context context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        this.setTitle("Quiz");
        context = this;

        Bundle bundle = getIntent().getExtras();
        final Integer passedValue = bundle.getInt("currentValue");
        final String subTopic = bundle.getString("subTopic");
        String className = bundle.getString("className");

        Log.d("className",""+className);

        final int listItemPosition=passedValue+ currentValue;
        Log.d("listItemPosition",""+listItemPosition);
        int QTrackSize = listItemPosition + 1;
        String QCSize = ((Integer)QTrackSize).toString();

        //Score
        final Integer score1 = bundle.getInt("Score");
        final Integer currentScore1 = bundle.getInt("currentScore");
        final int TotalScore = currentScore1 + score1;
        Log.d("Total Score", "Current Score="+TotalScore);

        Log.d("extra",""+subTopic);
        quiz_topicName = findViewById(R.id.quiz_Topic_TextView);
        Question_textView = findViewById(R.id.quiz_Question_TextView);
        quiz_option1 = findViewById(R.id.quiz_option1);
        quiz_option2 = findViewById(R.id.quiz_option2);
        quiz_option3 = findViewById(R.id.quiz_option3);
        quiz_option4 = findViewById(R.id.quiz_option4);
        quiz_CheckAnswer= findViewById(R.id.quiz_CheckAnswer);
        quiz_optionGroup = findViewById(R.id.quiz_optionGroup);

        quiz_QSize_TextView.setText(QCSize);

        quiz_topicName.setText(subTopic);

        progressDialog = ProgressDialogSpinner.showProgressDialog(this,"Loading Questions");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Quiz");
        query.whereEqualTo("subTopicName", subTopic);

        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> questions, ParseException e) {

                if (questions.size() > 0) {
                    Log.d("size", "" + questions.size());
                    String QuizSize = ((Integer) questions.size()).toString();
                    quiz_QCSize_TextView.setText(QuizSize);

                    if (questions.size() > listItemPosition) {
                        if (e == null) {
                            for (ParseObject question : questions) {
                                questionsList.add(question.getString(Question));
                                optionList1.add(question.getString(Option1));
                                optionList2.add(question.getString(Option2));
                                optionList3.add(question.getString(Option3));
                                optionList4.add(question.getString(Option4));
                                answerList.add(question.getString(Answer));
                                objectIdList.add(question.getString("objectId"));

                                Log.d("objects",""+objectIdList);

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
                                                final Dialog dialogCorrect = DialogPopupFragment.showDialog(ActivityQuiz.this);
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

                                                        progressDialog = ProgressDialogSpinner.showProgressDialog(ActivityQuiz.this, "Please Wait");
                                                        Intent intent = new Intent(getApplicationContext(), ActivityQuiz.class);
                                                        intent.putExtra("subTopic", subTopic);
                                                        currentValue++;
                                                        Log.d("value of passedValue", "" + currentValue);
                                                        int c3 = currentValue + listItemPosition;
                                                        Log.d("value of c3", "" + c3);
                                                        intent.putExtra("currentValue", c3);

                                                        //Score
                                                        int correctAnswer = 1;
                                                        intent.putExtra("Score", correctAnswer);
                                                        intent.putExtra("currentScore", TotalScore);

                                                        startActivity(intent);
                                                        progressDialog.dismiss();
                                                        finish();
                                                        dialogCorrect.dismiss();
                                                    }
                                                });

                                            } else {

                                                final Dialog dialogCorrect = DialogPopupFragment.showDialog(ActivityQuiz.this);
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
                                                        progressDialog = ProgressDialogSpinner.showProgressDialog(ActivityQuiz.this, "Please Wait");
                                                        Intent intent = new Intent(getApplicationContext(), ActivityQuiz.class);
                                                        intent.putExtra("subTopic", subTopic);
                                                        currentValue++;
                                                        Log.d("value of passedValue", "" + currentValue);
                                                        int c3 = currentValue + listItemPosition;
                                                        Log.d("value of c3", "" + c3);
                                                        intent.putExtra("currentValue", c3);
                                                        //Score
                                                        int wrongAnswer = 0;
                                                        intent.putExtra("Score", wrongAnswer);
                                                        intent.putExtra("currentScore", TotalScore);
                                                        startActivity(intent);
                                                        progressDialog.dismiss();
                                                        finish();
                                                        dialogCorrect.dismiss();
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

                        //QuizHelper activityQuizScore = new QuizHelper();
                        //String Mastery = "mastery";
                        //activityQuizScore.saveToRecentActivity(subTopic , TotalScore, Mastery);

                        String noContent = " Congratulation, You completed the Quiz. Score is " + TotalScore;
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

    @Override
    public void onBackPressed() {

        AlertDialogFragment.showAlertDialog(ActivityQuiz.this, "Cancel", "Yes", "Do you want to end the Quiz?",
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