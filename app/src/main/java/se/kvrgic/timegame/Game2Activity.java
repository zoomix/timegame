package se.kvrgic.timegame;

import android.app.AlertDialog;
import android.view.View;

import se.kvrgic.timegame.data.Answer;

public class Game2Activity extends GameAbstractActivity {

    public static final String TAG = "Game2Activity";


    @Override
    public void doShowHelp(View view) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.game2_help_header)
                .setMessage(R.string.game2_help)
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> {} )
                .create()
                .show();
    }

    @Override
    public String getGameStore() {
        return "score_game2";
    }

    @Override
    public String getBestgamestore() {
        return "bestscore_game2";
    }

    public Answer getRandomAnswer() {
        return new Answer( Math.round(Math.random() * 12), Math.round(Math.floor(Math.random() * 4)) * 15);
    }
}
