package se.kvrgic.timegame.data;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GameState {

    public static final String TAG = "GameState";

    public static enum GameResult { WON, LOST }
    public static enum GameMode { RUNNING, PAUSED, NEXTROUND, FINISHED }

    public List<GameResult> rounds;
    public List<Answer> answers;
    public Answer correctAnswer;
    public GameMode gameMode;

    public GameState() {
        rounds = new ArrayList<>();
        answers = new LinkedList<>();
        correctAnswer = null;
        gameMode = GameMode.FINISHED;
    }

    public boolean isRoundPlayed(int round) {
        return rounds.size() >= round;
    }

    public boolean isRoundWon(int round) {
        return GameResult.WON.equals( rounds.get(round - 1) );
    }

    public void storeState(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("rounds", new Gson().toJson(rounds));
        editor.putString("answers", new Gson().toJson(answers));
        editor.putString("correctAnswer", new Gson().toJson(correctAnswer));
        editor.putString("gameMode", new Gson().toJson(gameMode));
        editor.apply();
    }

    public static GameState getStoredState(SharedPreferences sharedPreferences) {
        GameState gameState = new GameState();
        String roundsJson = sharedPreferences.getString("rounds", null);
        if (roundsJson != null) {
            gameState.rounds = new ArrayList<>(Arrays.asList(new Gson().fromJson(roundsJson, GameResult[].class)));
        }
        String answersJson = sharedPreferences.getString("answers", null);
        if (answersJson != null) {
            gameState.answers = new LinkedList<>(Arrays.asList(new Gson().fromJson(answersJson, Answer[].class)));
        }
        String correctAnswerJson = sharedPreferences.getString("correctAnswer", null);
        if (correctAnswerJson != null) {
            gameState.correctAnswer = new Gson().fromJson(correctAnswerJson, Answer.class);
        }
        String gameModeJson = sharedPreferences.getString("gameMode", null);
        if (gameModeJson != null) {
            gameState.gameMode = new Gson().fromJson(gameModeJson, GameMode.class);
        }

        return gameState;
    }
}
