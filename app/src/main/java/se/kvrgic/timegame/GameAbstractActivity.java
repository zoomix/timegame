package se.kvrgic.timegame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

import se.kvrgic.timegame.data.Answer;
import se.kvrgic.timegame.data.GameState.GameMode;
import se.kvrgic.timegame.data.GameState;

public abstract class GameAbstractActivity extends Activity {

    public static final String TAG = "GameAbstractActivity";

    public static GameState gameState = null;
    TextToSpeech tts = null;

    public abstract void doShowHelp(View view);
    public abstract String getGameStore();
    public abstract String getBestgamestore();
    public abstract Answer getRandomAnswer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);
        Log.d(TAG, "onCreate: GAMESTORE:" + getGameStore());
        Log.d(TAG, "onCreate: BESTGAMESTORE:" + getBestgamestore());
        tts = new TextToSpeech(getApplicationContext(), (status) -> tts.setLanguage(Locale.forLanguageTag("sv")) );
        if (gameState == null) {
            gameState = GameState.getStoredState(getSharedPreferences(getGameStore(), MODE_PRIVATE));
        }
        if(gameState.gameMode.equals(GameMode.FINISHED)) {
            gameState = new GameState();
            gameState.gameMode = GameMode.NEXTROUND;
        }
        if(gameState.gameMode.equals(GameMode.NEXTROUND)) {
            doSetupGame();
        }
        doDrawGame();
    }




    public void doSpeak(View view) {
        String whatToSay = ((Answer)view.getTag()).getWhatToSay();
        tts.speak(whatToSay, TextToSpeech.QUEUE_FLUSH, null, "" + whatToSay.hashCode());
    }

    public void onPause() {
        if(tts !=null) {
            tts.stop();
            tts.shutdown();
        }
        gameState.storeState(getSharedPreferences(getGameStore(), MODE_PRIVATE));
        super.onPause();
    }



    public void doAcceptAnswer(View view) {
        Log.d(TAG, "doAcceptAnswer: ");
        int checkedIndex = getCheckedIndex();
        if (checkedIndex == -1) {
            Toast.makeText(this, "Du valde inget alternativ", Toast.LENGTH_SHORT).show();
            return;
        }

        gameState.gameMode = GameMode.PAUSED;

        boolean isWon = gameState.answers.get(checkedIndex).equals(gameState.correctAnswer);
        gameState.rounds.add(isWon ? GameState.GameResult.WON : GameState.GameResult.LOST);

        AlertDialog gameDone;
        gameDone = new AlertDialog.Builder(this)
                           .setView(getGameScore())
                           .setCancelable(false)
                           .setPositiveButton(isWon ? "Rätt" : "Ojdå", (dialogInterface, i) -> doAnswerAccepted() )
                           .create();
        gameDone.show();
        gameState.gameMode = GameMode.NEXTROUND;
    }




    void doSetupGame() {
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
        uncheckAllApartFrom(null);
        gameState.storeState(getSharedPreferences(getGameStore(), MODE_PRIVATE));
        gameState.gameMode = GameMode.RUNNING;
    }

    void doDrawGame() {
        View hour_hand = findViewById(R.id.game1_hour);
        hour_hand.setRotation(gameState.correctAnswer.getHourAngle());
        View minute_hand = findViewById(R.id.game1_minute);
        minute_hand.setRotation(gameState.correctAnswer.getMinuteAngle());

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
            checkbox.setTag(gameState.answers.get(i));
        }

        doAnimateWatchface(R.id.game1_clock);
        doAnimateWatchhands(R.id.game1_hour, 0);
        doAnimateWatchhands(R.id.game1_minute, 100);
    }



    private void doAnswerAccepted() {
        Log.d(TAG, "doAcceptAnswer: Nu vann vi höddu");
        if(gameState.rounds.size() >= 4) {
            Intent menuActivityIntent = new Intent(this, MenuActivity.class);
            menuActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            GameState bestGameState =  GameState.getStoredState(getSharedPreferences(getBestgamestore(), MODE_PRIVATE));
            if (gameState.isBetterThan(bestGameState)) {
                gameState.storeState(getSharedPreferences(getBestgamestore(), MODE_PRIVATE));
            }
            startActivity(menuActivityIntent);
            gameState.gameMode = GameMode.FINISHED;
        } else {
            doSetupGame();
            doDrawGame();
        }
    }

    private View getGameScore() {
        View scoreLayout = View.inflate(this, R.layout.game1_score, null);

        Arrays.asList(R.id.duckOk, R.id.duckFail, R.id.catFail, R.id.catOk, R.id.bananaOk, R.id.bananaFail, R.id.cowOk, R.id.cowFail)
              .forEach((id) -> scoreLayout.findViewById(id).setVisibility(View.INVISIBLE) );

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

    private void uncheckAllApartFrom(CompoundButton compoundButton) {
        LinearLayout answersLayout = findViewById(R.id.game_answers);
        for(int i=0; i<gameState.answers.size(); i++) {
            CheckBox checkBox = answersLayout.getChildAt(i).findViewById(R.id.checkBox);
            if (!checkBox.equals(compoundButton)) {
                checkBox.setChecked(false);
            }
        }
    }

    private void doAnimateWatchface(int viewId) {
        Animation watchface = AnimationUtils.loadAnimation(this, R.anim.game_watchface);
        watchface.setStartOffset(0);
        findViewById(viewId).startAnimation(watchface);
    }

    private void doAnimateWatchhands(int viewId, int offset) {
        Animation watchhands = AnimationUtils.loadAnimation(this, R.anim.game_watchhands);
        watchhands.setStartOffset(offset);
        findViewById(viewId).startAnimation(watchhands);
    }
}
