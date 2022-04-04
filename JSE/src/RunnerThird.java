import by.gsu.epamlab.beans.Mark;
import by.gsu.epamlab.beans.Result;
import by.gsu.epamlab.loads.LoadFromXML;
import by.gsu.epamlab.utils.classes.DAO;
import by.gsu.epamlab.utils.classes.ResultsUtils;

import java.util.List;

import static by.gsu.epamlab.Constants.*;


public class RunnerThird {
    public static void main(String[] args) {
        try{
            Mark.Type typeMark = Mark.Type.CSV_DB;
            Mark.setTypeMark(typeMark);

            DAO.buildDBConnection();
            DAO.prepareDB();
            ResultsUtils.loadFromFile(new LoadFromXML(), PATH + FILE_NAME + typeMark.ordinal() + EXT_CSV);

            ResultsUtils.meanMarks();
            List<Result> list = ResultsUtils.printTestByCurrentMonth();
            ResultsUtils.printTestsInTheLatestDay(list);
        } catch (IllegalStateException e) {
            System.err.println();
        }finally {
            DAO.closeDB();
        }
    }
}
