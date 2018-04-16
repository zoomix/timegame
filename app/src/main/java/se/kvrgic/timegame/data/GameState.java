package se.kvrgic.timegame.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameState {

    public static enum GameResult { WON, LOST }

    public List<GameResult> rounds;
    public List<Answer> answers;
    public Answer correctAnswer;


    public GameState() {
        rounds = new ArrayList<>();
        answers = new LinkedList<>();
        correctAnswer = null;
    }

    public boolean isRoundPlayed(int round) {
        return rounds.size() >= round;
    }

    public boolean isRoundWon(int round) {
        return GameResult.WON.equals( rounds.get(round - 1) );
    }

}
