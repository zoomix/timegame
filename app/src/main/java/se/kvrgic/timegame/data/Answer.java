package se.kvrgic.timegame.data;

public class Answer {

    long hour;
    long minute;

    public Answer(long hour, long minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public String toString() {
        String hourStr = (hour == 0 && minute == 0) ? "12" : String.format("%02d", hour);
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

    public String getWhatToSay() {
        if (minute == 0)  return "Klockan " + hour;
        if (minute == 15) return "Kvart över " + hour;
        if (minute == 30) return "Halv " + (hour + 1);
        if (minute == 45) return "Kvart i " + (hour + 1);
        return "Oj. Det här var svårt.. Ehm. Du kanske kan fråga en vuxen?";
    }

}
