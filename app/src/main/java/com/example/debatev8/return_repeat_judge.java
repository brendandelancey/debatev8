//After Submit Button is pressed User Input is saved
//as arg_a1 as a string -> ready to be submitted to database
package com.example.debatev8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Game;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

//Database: Saves User/ Finds User individual ID
//1 a) Initial -> Stores User Class Object In database
//1 b) Non-initial -> Gets Users Object
//
public class return_repeat_judge extends AppCompatActivity {

    Button judgeAgainButton;
    Button quitButton;

    game gameBeingJudged;

    DatabaseReference databaseRoot = FirebaseDatabase.getInstance().getReference();//***
    DatabaseReference databaseUsers = databaseRoot.child("UsersList");//***
    DatabaseReference databaseTopics = databaseRoot.child("Topics");
    DatabaseReference databaseCurrentGames = databaseRoot.child("CurrentGames");

    int iterations =0;

    int totalScore1=-100000000;
    int wins1=-100000000;
    int A_avg1=-100000000;
    int R_avg1=-100000000;
    int numGamesPlayed1=-100000000;
    int totalScore2=-100000000;
    int wins2=-100000000;
    int A_avg2=-100000000;
    int R_avg2=-100000000;
    int numGamesPlayed2=-100000000;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.judge_repeat_return);



        gameBeingJudged = (game) getIntent().getSerializableExtra("gameBeingJudged");
        Log.i("++++++++++++++++++++", "Return_Repeat_Judge1");
        Log.i("++++++++++++++++++++", gameBeingJudged.getPlayer1());

        //Reading 5 of 10 VAR from Player1
       databaseUsers.child(gameBeingJudged.getPlayer1()).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               Log.i("++++++++++++++++++++", "Return_Repeat_Judge1.5");
               for (DataSnapshot gameInfo : dataSnapshot.getChildren()) {
                   Log.i("++++++++++++++++++++", "Return_Repeat_Judge1.6");
                   Object ngp = dataSnapshot.child("numGamesPlayed").getValue();
                   Object w = dataSnapshot.child("wins").getValue();
                   Object ts = dataSnapshot.child("totalScore").getValue();
                   Object aa = dataSnapshot.child("a_avg").getValue();
                   Object ra = dataSnapshot.child("r_rvg").getValue();

                   if (ngp!=null){
                       Log.i("++++++++++++++++++++", "Return_Repeat_Judge1.6.1");
                   }
                   if(w!=null){
                       Log.i("++++++++++++++++++++", "Return_Repeat_Judge1.6.2");
                   }
                   if (aa!=null){
                       Log.i("++++++++++++++++++++", "Return_Repeat_Judge1.6.3");
                   }
                   if (ra!=null){
                       Log.i("++++++++++++++++++++", "Return_Repeat_Judge1.6.4");
                   }
                   if (ts!=null){
                       Log.i("++++++++++++++++++++", "Return_Repeat_Judge1.6.5");
                   }

                   Log.i("++++++++++++++++++++", "Return_Repeat_Judge1.7");
                   Log.i("++++++++++++++++++++", gameInfo.toString());
                   Log.i("++++++++++++++++++++", dataSnapshot.toString());
                   Log.i("++++++++++++++++++++", dataSnapshot.child("wins").toString());
                   //wins1=dataSnapshot.child("wins").getValue();
                   if (ngp!=null&&w!=null&&ts!=null&&aa!=null&&ra!=null)
                   {
                       Log.i("++++++++++++++++++++", "Return_Repeat_Judge1.8");
                       totalScore1 = 0;
                       wins1 = 0;
                       A_avg1 = 0;
                       R_avg1 = 0;
                       numGamesPlayed1 = 0;

                       totalScore1 = (int) (long) ngp;
                       wins1 = (int) (long) w;
                       A_avg1 = (int) (long) ts;
                       R_avg1 = (int) (long) aa;
                       numGamesPlayed1 = (int) (long) ra;
                       Log.i("++++++++++++++++++++", "Return_Repeat_Judge2");


                       //************Player 2
                       databaseUsers.child(gameBeingJudged.getPlayer2()).addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                               for (DataSnapshot gameInfo : dataSnapshot.getChildren()) {
                                   Object ngp = dataSnapshot.child("numGamesPlayed").getValue();
                                   Object w = dataSnapshot.child("wins").getValue();
                                   Object ts = dataSnapshot.child("totalScore").getValue();
                                   Object aa = dataSnapshot.child("a_avg").getValue();
                                   Object ra = dataSnapshot.child("r_rvg").getValue();




                                   if (ngp!=null&&w!=null&&ts!=null&&aa!=null&&ra!=null)
                                   {
                                       totalScore2 = 0;
                                       wins2 = 0;
                                       A_avg2 = 0;
                                       R_avg2 = 0;
                                       numGamesPlayed2 = 0;
                                       totalScore2 = (int) (long)ngp;
                                       wins2 = (int) (long) w;
                                       A_avg2 = (int) (long) ts;
                                       R_avg2 = (int) (long) aa;
                                       numGamesPlayed2 = (int) (long) ra;
                                       Log.i("++++++++++++++++++++", "Return_Repeat_Judge3");

                                       //*************************Calculations for Score
                                       final Timer myTimerReQuery = new Timer();
                                       TimerTask untilReQuery= new TimerTask()
                                       {

                                           @Override
                                           public void run()
                                           {
                                               Log.i("++++++++++++++++++++", "Return_Repeat_Judge4");

                                                if (iterations>0)
                                                {
                                                    myTimerReQuery.cancel();
                                                    myTimerReQuery.purge();
                                                }
                                               //Possibly a conditional until Values are not zero;
                                               if (iterations==0&&totalScore1!=-100000000&&wins1!=-100000000&&A_avg1!=-100000000&&R_avg1!=-100000000&&numGamesPlayed1!=-100000000&&totalScore2!=-100000000&&wins2!=-100000000&&A_avg2!=-100000000&&R_avg2!=-100000000&&numGamesPlayed2!=-100000000) {
                                                   iterations++;
                                                   String a = "" + iterations;
                                                   Log.i("===================", a);
                                                   Log.i("++++++++++++++++++++", "Return_Repeat_Judge5");
                                                   //***Scoring
                                                   int aTotal = gameBeingJudged.getStages().getStage1().getJudgeScoreA() + gameBeingJudged.getStages().getStage2().getJudgeScoreR() + gameBeingJudged.getStages().getStage3().getJudgeScoreA();
                                                   int bTotal = gameBeingJudged.getStages().getStage1().getJudgeScoreR() + gameBeingJudged.getStages().getStage2().getJudgeScoreA() + gameBeingJudged.getStages().getStage3().getJudgeScoreR();


                                                   int a_A_avg = (gameBeingJudged.getStages().getStage1().getJudgeScoreA() + gameBeingJudged.getStages().getStage3().getJudgeScoreA()) / 2;
                                                   int b_A_avg = gameBeingJudged.getStages().getStage2().getJudgeScoreA();
                                                   int a_R_avg = gameBeingJudged.getStages().getStage2().getJudgeScoreR();
                                                   int b_R_avg = (gameBeingJudged.getStages().getStage1().getJudgeScoreR() + gameBeingJudged.getStages().getStage3().getJudgeScoreR()) / 2;
                                                   int a_r_w = 0;
                                                   int b_r_w = 0;
                                                   //**Round 1
                                                   if (gameBeingJudged.getStages().getStage1().getJudgeScoreA() > gameBeingJudged.getStages().getStage1().getJudgeScoreR()) {
                                                       a_r_w++;
                                                   }
                                                   if (gameBeingJudged.getStages().getStage1().getJudgeScoreA() < gameBeingJudged.getStages().getStage1().getJudgeScoreR()) {
                                                       b_r_w++;
                                                   }
                                                   //*
                                                   //**Round 2
                                                   if (gameBeingJudged.getStages().getStage2().getJudgeScoreR() > gameBeingJudged.getStages().getStage2().getJudgeScoreA()) {
                                                       a_r_w++;
                                                   }
                                                   if (gameBeingJudged.getStages().getStage2().getJudgeScoreR() < gameBeingJudged.getStages().getStage2().getJudgeScoreA()) {
                                                       b_r_w++;
                                                   }
                                                   //*
                                                   //**Round 3
                                                   if (gameBeingJudged.getStages().getStage3().getJudgeScoreA() > gameBeingJudged.getStages().getStage3().getJudgeScoreR()) {
                                                       a_r_w++;
                                                   }
                                                   if (gameBeingJudged.getStages().getStage3().getJudgeScoreA() < gameBeingJudged.getStages().getStage3().getJudgeScoreR()) {
                                                       b_r_w++;
                                                   }


                                                   numGamesPlayed1++;
                                                   numGamesPlayed2++;
                                                   A_avg1 = ((A_avg1 * (numGamesPlayed1 - 1)) + a_A_avg) / numGamesPlayed1;
                                                   String b = "" + A_avg1;
                                                   Log.i("A_avg1", b);
                                                   R_avg1 = ((R_avg1 * (numGamesPlayed1 - 1)) + a_R_avg) / numGamesPlayed1;
                                                   String c = "" + R_avg1;
                                                   Log.i("R_avg1", c);
                                                   A_avg2 = ((A_avg2 * (numGamesPlayed2 - 1)) + b_A_avg) / numGamesPlayed2;
                                                   String d = "" + A_avg2;
                                                   Log.i("A_avg2", d);
                                                   R_avg2 = ((R_avg2 * (numGamesPlayed2 - 1)) + b_R_avg) / numGamesPlayed2;
                                                   String e = "" + R_avg2;
                                                   Log.i("R_avg2", e);
                                                   //*
                                                   //**Winner Determined
                                                   int subTotalScore1 = 0;
                                                   int subTotalScore2 = 0;
                                                   if (a_r_w != b_r_w) {
                                                       if (a_r_w > b_r_w) {
                                                           //Add Win to player1
                                                           wins1++;
                                                           subTotalScore1 = 25;
                                                           subTotalScore2 = -25;
                                                           //Bonous1
                                                           subTotalScore1 = (aTotal / 3) + subTotalScore1;
                                                           subTotalScore2 = (bTotal / 3) + subTotalScore2;
                                                           //Bonous2
                                                           if (totalScore2 - 100 > totalScore1) {
                                                               subTotalScore1 = subTotalScore1 + 20;
                                                               subTotalScore2 = subTotalScore2 - 10;
                                                           }
                                                           totalScore1 = totalScore1 + subTotalScore1;
                                                           totalScore2 = totalScore2 + subTotalScore2;
                                                           Log.i("++++++++++++++++++++", "Player1 Win Outright");
                                                           //Player1 Update
                                                           databaseUsers.child(gameBeingJudged.getPlayer1()).child("wins").setValue(wins1);
                                                           databaseUsers.child(gameBeingJudged.getPlayer1()).child("a_avg").setValue(A_avg1);
                                                           databaseUsers.child(gameBeingJudged.getPlayer1()).child("r_rvg").setValue(R_avg1);
                                                           databaseUsers.child(gameBeingJudged.getPlayer1()).child("numGamesPlayed").setValue(numGamesPlayed1);
                                                           databaseUsers.child(gameBeingJudged.getPlayer1()).child("totalScore").setValue(totalScore1);
                                                           //Player2 Update
                                                           databaseUsers.child(gameBeingJudged.getPlayer2()).child("wins").setValue(wins2);
                                                           databaseUsers.child(gameBeingJudged.getPlayer2()).child("a_avg").setValue(A_avg2);
                                                           databaseUsers.child(gameBeingJudged.getPlayer2()).child("r_rvg").setValue(R_avg2);
                                                           databaseUsers.child(gameBeingJudged.getPlayer2()).child("numGamesPlayed").setValue(numGamesPlayed2);
                                                           databaseUsers.child(gameBeingJudged.getPlayer2()).child("totalScore").setValue(totalScore2);
                                                       } else {
                                                           //Add Win to player2
                                                           wins2++;
                                                           subTotalScore2 = 25;
                                                           subTotalScore1 = -25;
                                                           //Bonous1
                                                           subTotalScore1 = (aTotal / 3) + subTotalScore1;
                                                           subTotalScore2 = (bTotal / 3) + subTotalScore2;
                                                           //Bonous2

                                                           if (totalScore1 - 100 > totalScore2) {
                                                               subTotalScore1 = subTotalScore1 - 10;
                                                               subTotalScore2 = subTotalScore2 + 20;
                                                           }
                                                           totalScore1 = totalScore1 + subTotalScore1;
                                                           totalScore2 = totalScore2 + subTotalScore2;

                                                           Log.i("++++++++++++++++++++", "Player2 Win Outright");
                                                           //Player1 Update
                                                           databaseUsers.child(gameBeingJudged.getPlayer1()).child("wins").setValue(wins1);
                                                           databaseUsers.child(gameBeingJudged.getPlayer1()).child("a_avg").setValue(A_avg1);
                                                           databaseUsers.child(gameBeingJudged.getPlayer1()).child("r_rvg").setValue(R_avg1);
                                                           databaseUsers.child(gameBeingJudged.getPlayer1()).child("numGamesPlayed").setValue(numGamesPlayed1);
                                                           databaseUsers.child(gameBeingJudged.getPlayer1()).child("totalScore").setValue(totalScore1);
                                                           //Player2 Update
                                                           databaseUsers.child(gameBeingJudged.getPlayer2()).child("wins").setValue(wins2);
                                                           databaseUsers.child(gameBeingJudged.getPlayer2()).child("a_avg").setValue(A_avg2);
                                                           databaseUsers.child(gameBeingJudged.getPlayer2()).child("r_rvg").setValue(R_avg2);
                                                           databaseUsers.child(gameBeingJudged.getPlayer2()).child("numGamesPlayed").setValue(numGamesPlayed2);
                                                           databaseUsers.child(gameBeingJudged.getPlayer2()).child("totalScore").setValue(totalScore2);
                                                       }
                                                   } else {
                                                       if (aTotal != bTotal) {
                                                           if (aTotal > bTotal) {
                                                               //Add Win to player1 (Lesser Points)
                                                               wins1++;
                                                               subTotalScore1 = 15;
                                                               subTotalScore2 = -15;
                                                               //Bonous1
                                                               subTotalScore1 = (aTotal / 3) + subTotalScore1;
                                                               subTotalScore2 = (bTotal / 3) + subTotalScore2;
                                                               //Bonous2
                                                               if (totalScore2 - 100 > totalScore1) {
                                                                   subTotalScore1 = subTotalScore1 + 20;
                                                                   subTotalScore2 = subTotalScore2 - 10;
                                                               }
                                                               totalScore1 = totalScore1 + subTotalScore1;
                                                               totalScore2 = totalScore2 + subTotalScore2;
                                                               Log.i("++++++++++++++++++++", "Player1 Win Partial");

                                                               //Player1 Update
                                                               databaseUsers.child(gameBeingJudged.getPlayer1()).child("wins").setValue(wins1);
                                                               databaseUsers.child(gameBeingJudged.getPlayer1()).child("a_avg").setValue(A_avg1);
                                                               databaseUsers.child(gameBeingJudged.getPlayer1()).child("r_rvg").setValue(R_avg1);
                                                               databaseUsers.child(gameBeingJudged.getPlayer1()).child("numGamesPlayed").setValue(numGamesPlayed1);
                                                               databaseUsers.child(gameBeingJudged.getPlayer1()).child("totalScore").setValue(totalScore1);
                                                               //Player2 Update
                                                               databaseUsers.child(gameBeingJudged.getPlayer2()).child("wins").setValue(wins2);
                                                               databaseUsers.child(gameBeingJudged.getPlayer2()).child("a_avg").setValue(A_avg2);
                                                               databaseUsers.child(gameBeingJudged.getPlayer2()).child("r_rvg").setValue(R_avg2);
                                                               databaseUsers.child(gameBeingJudged.getPlayer2()).child("numGamesPlayed").setValue(numGamesPlayed2);
                                                               databaseUsers.child(gameBeingJudged.getPlayer2()).child("totalScore").setValue(totalScore2);
                                                           } else {
                                                               //Add Win to player2 (Lesser Points)
                                                               wins2++;
                                                               subTotalScore2 = 15;
                                                               subTotalScore1 = -15;
                                                               //Bonous1
                                                               subTotalScore1 = (aTotal / 3) + subTotalScore1;
                                                               subTotalScore2 = (bTotal / 3) + subTotalScore2;
                                                               //Bonous2

                                                               if (totalScore1 - 100 > totalScore2) {
                                                                   subTotalScore1 = subTotalScore1 - 10;
                                                                   subTotalScore2 = subTotalScore2 + 20;
                                                               }
                                                               totalScore1 = totalScore1 + subTotalScore1;
                                                               totalScore2 = totalScore2 + subTotalScore2;
                                                               Log.i("++++++++++++++++++++", "Player2 Win Partial");
                                                               //Player1 Update
                                                               databaseUsers.child(gameBeingJudged.getPlayer1()).child("wins").setValue(wins1);
                                                               databaseUsers.child(gameBeingJudged.getPlayer1()).child("a_avg").setValue(A_avg1);
                                                               databaseUsers.child(gameBeingJudged.getPlayer1()).child("r_rvg").setValue(R_avg1);
                                                               databaseUsers.child(gameBeingJudged.getPlayer1()).child("numGamesPlayed").setValue(numGamesPlayed1);
                                                               databaseUsers.child(gameBeingJudged.getPlayer1()).child("totalScore").setValue(totalScore1);
                                                               //Player2 Update
                                                               databaseUsers.child(gameBeingJudged.getPlayer2()).child("wins").setValue(wins2);
                                                               databaseUsers.child(gameBeingJudged.getPlayer2()).child("a_avg").setValue(A_avg2);
                                                               databaseUsers.child(gameBeingJudged.getPlayer2()).child("r_rvg").setValue(R_avg2);
                                                               databaseUsers.child(gameBeingJudged.getPlayer2()).child("numGamesPlayed").setValue(numGamesPlayed2);
                                                               databaseUsers.child(gameBeingJudged.getPlayer2()).child("totalScore").setValue(totalScore2);


                                                           }
                                                       } else {
                                                           //Add a Tie Game
                                                       }
                                                   }
                                                   //*
                                                   Log.i("++++++++++++++++++++", "Return_Repeat_Judge6");

                                               }
                                               else
                                               {
                                                   //Will probably make the buttons invisible until posted
                                               }
                                           }
                                       };//Every Second
                                       myTimerReQuery.schedule(untilReQuery,0,3000);

                                   }

                               }
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError databaseError) {

                           }
                       });



                   }





               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });



        //**
        //***Deleting Game

        //**



        judgeAgainButton = (Button) findViewById(R.id.judgeAgain);
        judgeAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openJudgechoice();


            }

        });
        quitButton = (Button) findViewById(R.id.quit);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitChoice();

            }
        });

    }
    //May change this to not allow choice of debate and use algorithm instead
    public void openJudgechoice(){
        Intent intent = new Intent(this, searching_completed_matches.class);


        startActivity(intent);
    }
    //opens judge wait screen
    public void quitChoice(){
        Intent intent = new Intent(this, choice_home.class);
        startActivity(intent);
    }
    //This leads to the leaderboards_debate page

}
