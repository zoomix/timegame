package se.kvrgic.timegame;

import android.app.AlertDialog;
import android.view.View;

import se.kvrgic.timegame.data.Answer;

public class Game1Activity extends GameAbstractActivity {

    public static final String TAG = "Game1Activity";


    @Override
    public void doShowHelp(View view) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.game1_help_header)
                .setMessage(R.string.game1_help)
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> {} )
                .create()
                .show();
    }

    @Override
    public String getGameStore() {
        return "score_game1";
    }

    @Override
    public String getBestgamestore() {
        return "bestscore_game1";
    }

    public Answer getRandomAnswer() {
        return new Answer( Math.round(Math.random() * 12), 0);
    }

}
