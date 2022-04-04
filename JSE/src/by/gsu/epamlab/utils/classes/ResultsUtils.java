package by.gsu.epamlab.utils.classes;

import by.gsu.epamlab.beans.MeanMark;
import by.gsu.epamlab.beans.Result;
import by.gsu.epamlab.loads.Load;

import java.util.Calendar;
import java.util.List;

import static by.gsu.epamlab.Constants.*;

public class ResultsUtils {

    public static void loadFromFile(Load load, String fileName){
        load.load(fileName);
    }

    public static void meanMarks(){
        List<MeanMark> meanMarks = DAO.selectMeanMarks();
        System.out.println(MEAN_MARK);
        for (MeanMark meanMark : meanMarks){
            System.out.println(meanMark);
        }
        System.out.println(DATA_SEPARATOR);
    }

    public static List<Result> printTestByCurrentMonth(){
        List<Result> results = DAO.selectByMonthYear();
        System.out.println(PRINT_TEST_BY_CURRENT_MONTH_MESSAGE);
        for(Result result : results){
            System.out.println(result);
        }
        System.out.println(DATA_SEPARATOR);
        return results;
    }

    public static void printTestsInTheLatestDay(List<Result> list){
        Calendar day1 = Calendar.getInstance();
        Calendar day2 = Calendar.getInstance();
        day1.setTime(list.get(list.size() -1).getDate());

        System.out.println(TESTS_RESULTS_LATEST_DAY_OF_THE_CURRENT_MONTH);
        for(int i = list.size(); i > 0; i--){
            day2.setTime(list.get(list.size() -1).getDate());

            boolean sameDay = day1.get(Calendar.YEAR) == day2.get(Calendar.YEAR) &&
                    day1.get(Calendar.DAY_OF_YEAR) == day2.get(Calendar.DAY_OF_YEAR);
            if (sameDay){
                System.out.println(list.get(i - 1));
            } else {
                return;
            }
        }
    }
}
