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

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import se.kvrgic.timegame.data.Answer;
import se.kvrgic.timegame.data.GameMode;
import se.kvrgic.timegame.data.GameState;

public class Game1Activity extends Activity {

    public static final String TAG = "Game1Activity";

    public static GameMode gameMode = GameMode.FINISHED;
    public static GameState gameState;

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
            Toast.makeText(this, "Du  valde inget alternativ", Toast.LENGTH_SHORT).show();
            return;
        }

        gameMode = GameMode.PAUSED;

        boolean isWon = gameState.answers.get(checkedIndex).equals(gameState.correctAnswer);
        gameState.rounds.add(isWon ? GameState.GameResult.WON : GameState.GameResult.LOST);

        AlertDialog gameDone;
        gameDone = new AlertDialog.Builder(this)
                                  .setView(getGameScore())
                                  .create();
        gameDone.show();
        gameMode = GameMode.FINISHED;
    }



    private int getCheckedIndex() {
        LinearLayout answersLayout = findViewById(R.id.game_answers);
        for(int i=0; i<gameState.answers.size(); i++) {
            CheckBox checkBox = answersLayout.getChildAt(i).findViewById(R.id.checkBox);
            if (checkBox.isChecked()) {
                return i;
            }
        }
        Log.d(TAG, "getCheckedIndex: Hittade inget som var ibockat.");
        return -1;
    }

    private void doSetupGame() {
        gameState = new GameState();
        gameState.answers.clear();
        gameState.correctAnswer = new Answer( Math.round(Math.random() * 12), 0);
        gameState.answers.add(gameState.correctAnswer);
        while (gameState.answers.size() < 3) {
            Answer alternativeAnswer = new Answer(Math.round(Math.random() * 12), 0);
            if ( !gameState.answers.contains(alternativeAnswer) ) {
                gameState.answers.add(alternativeAnswer);
            }
        }
        Collections.shuffle(gameState.answers);
        gameMode = GameMode.RUNNING;
    }

    private void doDrawGame() {
        View hour_hand = findViewById(R.id.game1_hour);
        hour_hand.setRotation(gameState.correctAnswer.getHourAngle());

        LinearLayout answersLayout = findViewById(R.id.game_answers);
        for(int i=0; i<gameState.answers.size(); i++) {
            ConstraintLayout answerLayout = (ConstraintLayout) answersLayout.getChildAt(i);
            CheckBox checkbox = answerLayout.findViewById(R.id.checkBox);
            checkbox.setText(gameState.answers.get(i).toString());
            checkbox.setOnCheckedChangeListener( (compoundButton, checked) -> {
                if (checked) {
                    uncheckAllApartFrom(compoundButton);
                }
            });
        }
    }

    private void uncheckAllApartFrom(CompoundButton compoundButton) {
        LinearLayout answersLayout = findViewById(R.id.game_answers);
        for(int i=0; i<gameState.answers.size(); i++) {
            CheckBox checkBox = answersLayout.getChildAt(i).findViewById(R.id.checkBox);
            if (!checkBox.equals(compoundButton)) {
                checkBox.setChecked(false);
            }
        }
    }



    private View getGameScore() {
        View scoreLayout = View.inflate(this, R.layout.game1_score, null);

        Arrays.asList(R.id.duckOk, R.id.duckFail, R.id.catFail, R.id.catOk, R.id.bananaOk, R.id.bananaFail, R.id.cowOk, R.id.cowFail).forEach((id) -> {
            scoreLayout.findViewById(id).setVisibility(View.INVISIBLE);
        });

        if (gameState.isRoundPlayed(1)) {
            scoreLayout.findViewById(gameState.isRoundWon(1) ? R.id.duckOk : R.id.duckFail).setVisibility(View.VISIBLE);
        }
        if (gameState.isRoundPlayed(2)) {
            scoreLayout.findViewById(gameState.isRoundWon(2) ? R.id.catOk : R.id.catFail).setVisibility(View.VISIBLE);
        }
        if (gameState.isRoundPlayed(3)) {
            scoreLayout.findViewById(gameState.isRoundWon(3) ? R.id.bananaOk : R.id.bananaFail).setVisibility(View.VISIBLE);
        }
        if (gameState.isRoundPlayed(4)) {
            scoreLayout.findViewById(gameState.isRoundWon(4) ? R.id.cowOk : R.id.cowFail).setVisibility(View.VISIBLE);
        }

        GameState.GameResult lastRound = gameState.rounds.get(gameState.rounds.size() - 1);
        scoreLayout.findViewById( !GameState.GameResult.WON.equals(lastRound) ? R.id.correct : R.id.incorrect).setVisibility(View.INVISIBLE);

        return scoreLayout;
    }
}
