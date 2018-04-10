package se.kvrgic.timegame;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import se.kvrgic.timegame.data.Answer;

public class Game1Activity extends Activity {

    public static final String TAG = "Game1Activity";

    public static List<Answer> answers = new LinkedList<>();
    public static Answer correctAnswer = null;
    public static boolean isGameRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);

        if(!isGameRunning) {
            doSetupGame();
        }
        doDrawGame();
    }

    public void doAcceptAnswer(View view) {
        Log.d(TAG, "doAcceptAnswer: ");
        isGameRunning = false;
    }



    private void doSetupGame() {
        answers.clear();
        correctAnswer = new Answer( Math.round(Math.random() * 12), 0);
        answers.add(correctAnswer);

        while (answers.size() < 3) {
            Answer alternativeAnswer = new Answer(Math.round(Math.random() * 12), 0);
            if ( !answers.contains(alternativeAnswer) ) {
                answers.add(alternativeAnswer);
            }
        }
        Collections.shuffle(answers);
        isGameRunning = true;
    }

    private void doDrawGame() {
        View hour_hand = findViewById(R.id.game1_hour);
        hour_hand.setRotation(correctAnswer.getHourAngle());

        LinearLayout answersLayout = findViewById(R.id.game_answers);
        for(int i=0; i<answers.size(); i++) {
            ConstraintLayout answerLayout = (ConstraintLayout) answersLayout.getChildAt(i);
            CheckBox checkbox = answerLayout.findViewById(R.id.checkBox);
            checkbox.setText(answers.get(i).toString());
        }
    }

}
