package by.gsu.epamlab.beans;

import by.gsu.epamlab.Constants;

import java.util.Formatter;
import java.util.Locale;

public class MeanMark {

    private String student;
    private double meanMark;

    public MeanMark(String student, double meanMark) {
        this.student = student;
        this.meanMark = meanMark;
    }

    @Override
    public String toString(){
        Formatter formatter = new Formatter(Locale.US);
        formatter.format("%.2f", meanMark);
        return student + Constants.DELIMITER + formatter;
    }
}
