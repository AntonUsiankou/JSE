package by.gsu.epamlab.beans;

import by.gsu.epamlab.utils.classes.Utils;

import java.util.Date;

import static by.gsu.epamlab.Constants.DELIMITER;

public class Result {
    private String login;
    private String testName;
    private Date date;
    private int mark;

    public Result(String login, String testName, Date date, int mark) {
        this.login = login;
        this.testName = testName;
        this.mark = mark;
        this.date = date;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public java.sql.Date getDate() {
        return (java.sql.Date) date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return login + DELIMITER + testName + DELIMITER + date + DELIMITER + mark;
    }
}
