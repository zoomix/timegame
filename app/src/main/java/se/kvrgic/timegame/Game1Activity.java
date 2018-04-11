package se.kvrgic.timegame;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import se.kvrgic.timegame.data.Answer;
import se.kvrgic.timegame.data.GameMode;

public class Game1Activity extends Activity {

    public static final String TAG = "Game1Activity";

    public static List<Answer> answers = new LinkedList<>();
    public static Answer correctAnswer = null;
    public static GameMode gameMode = GameMode.FINISHED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);

        if(gameMode.equals(GameMode.FINISHED)) {
            doSetupGame();
        }
        doDrawGame();
    }

    public void doAcceptAnswer(View view) {
        Log.d(TAG, "doAcceptAnswer: ");
        int checkedIndex = getCheckedIndex();
        if (checkedIndex == -1) {
            Toast.makeText(this, "Du valde inget alternativ", Toast.LENGTH_SHORT).show();
            return;
        }

        gameMode = GameMode.PAUSED;
        AlertDialog gameDone;

        if (answers.get(checkedIndex).equals(correctAnswer)) {
            gameDone = new AlertDialog.Builder(this)
                                      .setTitle("Grattis")
                                      .setMessage("Du hade rätt. Woow.")
                                      .create();
        } else {
            gameDone = new AlertDialog.Builder(this)
                                      .setTitle("Ajdå")
                                      .setMessage("Det här gick la sådär, eller?")
                                      .create();
        }

        gameDone.show();
        gameMode = GameMode.FINISHED;
    }



    private int getCheckedIndex() {
        LinearLayout answersLayout = findViewById(R.id.game_answers);
        for(int i=0; i<answers.size(); i++) {
            CheckBox checkBox = answersLayout.getChildAt(i).findViewById(R.id.checkBox);
            if (checkBox.isChecked()) {
                return i;
            }
        }
        Log.d(TAG, "getCheckedIndex: Hittade inget som var ibockat.");
        return -1;
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
        gameMode = GameMode.RUNNING;
    }

    private void doDrawGame() {
        View hour_hand = findViewById(R.id.game1_hour);
        hour_hand.setRotation(correctAnswer.getHourAngle());

        LinearLayout answersLayout = findViewById(R.id.game_answers);
        for(int i=0; i<answers.size(); i++) {
            ConstraintLayout answerLayout = (ConstraintLayout) answersLayout.getChildAt(i);
            CheckBox checkbox = answerLayout.findViewById(R.id.checkBox);
            checkbox.setText(answers.get(i).toString());
            checkbox.setOnCheckedChangeListener( (compoundButton, checked) -> {
                if (checked) {
                    uncheckAllApartFrom(compoundButton);
                }
            });
        }
    }

    private void uncheckAllApartFrom(CompoundButton compoundButton) {
        LinearLayout answersLayout = findViewById(R.id.game_answers);
        for(int i=0; i<answers.size(); i++) {
            CheckBox checkBox = answersLayout.getChildAt(i).findViewById(R.id.checkBox);
            if (!checkBox.equals(compoundButton)) {
                checkBox.setChecked(false);
            }
        }
    }
}
