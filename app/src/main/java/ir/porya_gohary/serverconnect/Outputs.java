package ir.porya_gohary.serverconnect;

public class Outputs {
    String time;
    int analog1;
    int analog2;
    int digital1;
    int digital2;

    public Outputs(String time, int analog1, int analog2, int digital1, int digital2) {
        this.time = time;
        this.analog1 = analog1;
        this.analog2 = analog2;
        this.digital1 = digital1;
        this.digital2 = digital2;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getAnalog1() {
        return analog1;
    }

    public void setAnalog1(int analog1) {
        this.analog1 = analog1;
    }

    public int getAnalog2() {
        return analog2;
    }

    public void setAnalog2(int analog2) {
        this.analog2 = analog2;
    }

    public int getDigital1() {
        return digital1;
    }

    public void setDigital1(int digital1) {
        this.digital1 = digital1;
    }

    public int getDigital2() {
        return digital2;
    }

    public void setDigital2(int digital2) {
        this.digital2 = digital2;
    }
}
