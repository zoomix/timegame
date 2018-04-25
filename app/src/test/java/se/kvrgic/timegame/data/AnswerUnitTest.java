package se.kvrgic.timegame.data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AnswerUnitTest {

    @Test
    public void answer_makes0000to1200() throws Exception {
        Answer answer = new Answer(0, 0);
        assertEquals("12:00", answer.toString());
    }

    @Test
    public void answer_makes0030to0030() throws Exception {
        Answer answer = new Answer(0, 30);
        assertEquals("00:30", answer.toString());
    }

    @Test
    public void whatToSay_kl12() throws Exception {
        Answer answer = new Answer(0, 0);
        assertEquals("Klockan 12", answer.getWhatToSay());
    }

    @Test
    public void whatToSay_kvartOver3() throws Exception {
        Answer answer = new Answer(3, 15);
        assertEquals("Kvart över 3", answer.getWhatToSay());
    }

    @Test
    public void whatToSay_halv1() throws Exception {
        Answer answer = new Answer(0, 30);
        assertEquals("Halv 1", answer.getWhatToSay());
    }

    @Test
    public void whatToSay_halv12() throws Exception {
        Answer answer = new Answer(11, 30);
        assertEquals("Halv 12", answer.getWhatToSay());
    }

    @Test
    public void whatToSay_kvartI1() throws Exception {
        Answer answer = new Answer(0, 45);
        assertEquals("Kvart i 1", answer.getWhatToSay());
    }

    @Test
    public void whatToSay_kl8() throws Exception {
        Answer answer = new Answer(8, 0);
        assertEquals("Klockan 8", answer.getWhatToSay());
    }

}
