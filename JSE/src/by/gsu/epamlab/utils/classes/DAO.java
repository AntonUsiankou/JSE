package by.gsu.epamlab.utils.classes;

import by.gsu.epamlab.beans.MeanMark;
import by.gsu.epamlab.beans.Result;
import by.gsu.epamlab.enums.Fields;
import by.gsu.epamlab.enums.ResultsEnum;
import com.mysql.cj.xdevapi.Table;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DAO {

    public static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/results";
    public static final String LOGIN = "root";
    public static final String PASSWORD = "root";

    private static final String DELETE_LOGINS = "DELETE FROM logins";
    private static final String DELETE_TESTS = "DELETE FROM tests";
    private static final String DELETE_RESULTS = "DELETE FROM results";

    private static final String INSERT_INTO_LOGINS = "INSERT INTO logins (name) values(?)";
    private static final String INSERT_INTO_TESTS = "INSERT INTO tests (name) values(?)";
    private static final String INSERT_INTO_RESULTS = "INSERT INTO results (loginId, testId, data, mark) VALUES(?,?,?,?)";

    private static final String SELECT_ID_LOGIN = "SELECT idLogin FROM logins WHERE name = ?";
    private static final String SELECT_ID_TEST = "SELECT idTest FROM tests WHERE name = ?";

    private static final String SELECT_AVERAGE = "select loginId, avg(mark) from results " +
            "inner join logins on results.loginId = logins.idlogin " +
            "group by name " +
            "order by 2 desc";

    private static final String SELECT_BY_MONTH_YEAR = "SELECT loginId,testId,data,mark FROM ((results " +
            "INNER JOIN tests ON tests.idTest = results.testId) " +
            "INNER JOIN logins ON logins.idLogin = results.loginId) " +
            "WHERE MONTH(data) = MONTH(now()) AND YEAR(data) = YEAR(now()) " +
            "ORDER BY data";

    private static Connection connection;
    private static PreparedStatement statement;
    private static ResultSet resultSet;

    private enum Table {
        LOGIN, TEST
    }
    private DAO(){
    }

    public static void buildDBConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeDB(){
        try{
            if(resultSet != null){
                resultSet.close();
            }
            if(statement != null){
                statement.close();
            }
            if(connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void clear(String sql) {
        try {
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void prepareDB() {
        clear(DELETE_LOGINS);
        clear(DELETE_TESTS);
        clear(DELETE_RESULTS);
    }

    public static List selectByMonthYear() {
        List<Result> results = new LinkedList<>();
        try {
            statement = connection.prepareStatement(SELECT_BY_MONTH_YEAR);
            resultSet.next();
            while (resultSet.next()) {
                results.add(new Result(
                        resultSet.getString(Fields.LOGIN.ordinal()),
                        resultSet.getString(Fields.TEST_NAME.ordinal()),
                        resultSet.getDate(Fields.DATE.ordinal()),
                        resultSet.getInt(Fields.MARK.ordinal())
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    private static int getId(String value, Table table) {
        int id = -1;
        String sql = null;
        switch (table) {
            case LOGIN:
                sql = SELECT_ID_LOGIN;
                break;
            case TEST:
                sql = SELECT_ID_TEST;
                break;
        }

        try{
            statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1,value);
            resultSet = statement.executeQuery();

            resultSet.last();
            int count = resultSet.getRow();
            resultSet.beforeFirst();
            if(count != 0){
                resultSet.next();
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return id;
    }

    private static void addNewValue(String value, Table table){
        
        String sql = null;
        switch(table){
            case LOGIN:
                sql = INSERT_INTO_LOGINS;
                break;
            case TEST:
                sql = INSERT_INTO_TESTS;
                break;
        }
        try{
            statement = connection.prepareStatement(sql);
            statement.setString(1, value);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void add(Result result){
        int loginId = getId(result.getLogin(), Table.LOGIN);
        if(loginId == -1){
            addNewValue(result.getLogin(), Table.LOGIN);
            loginId = getId(result.getLogin(), Table.LOGIN);
        }

        int testId = getId(result.getTestName(), Table.TEST);
        if(testId == -1){
            addNewValue(result.getTestName(), Table.TEST);
            testId = getId(result.getTestName(), Table.TEST);
        }
        try {
            statement = connection.prepareStatement(INSERT_INTO_RESULTS);
            statement.setInt(ResultsEnum.LOGIN_ID.ordinal(), loginId);
            statement.setInt(ResultsEnum.TEST_ID.ordinal(), testId);
            statement.setDate(ResultsEnum.DATE.ordinal(), result.getDate());
            statement.setInt(ResultsEnum.MARK.ordinal(), result.getMark());
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static List<MeanMark> selectMeanMarks(){

        List<MeanMark> meanMarks = new LinkedList<>();
        try{
            statement = connection.prepareStatement(SELECT_AVERAGE);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                meanMarks.add(new MeanMark(resultSet.getString(1), resultSet.getDouble(2)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meanMarks;
    }
}
