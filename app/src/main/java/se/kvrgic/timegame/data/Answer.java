package se.kvrgic.timegame.data;

public class Answer {

    long hour;
    long minute;

    public Answer(long hour, long minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public String toString() {
        String hourStr = (hour == 0) ? "12" : String.format("%02d", hour);
        String minuteStr = String.format("%02d", minute);
        return hourStr + ":" + minuteStr;
    }

    public float getHourAngle() {
        return hour * 360f / 12 + getMinuteAngle() / 12;
    }

    public float getMinuteAngle() {
        return minute * 360f / 60;
    }

    @Override
    public boolean equals(Object o) {
        Answer other = (Answer)o;
        return hour == other.hour && minute == other.minute;
    }
}
