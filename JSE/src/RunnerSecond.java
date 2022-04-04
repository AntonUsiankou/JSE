import by.gsu.epamlab.beans.Mark;
import by.gsu.epamlab.beans.Result;
import by.gsu.epamlab.loads.LoadFromXML;
import by.gsu.epamlab.utils.classes.DAO;
import by.gsu.epamlab.utils.classes.ResultsUtils;


import java.util.List;

import static by.gsu.epamlab.Constants.*;

public class RunnerSecond {

    public static void main(String[] args) {
        try {
            DAO.buildDBConnection();
            DAO.prepareDB();

            Mark.setTypeMark(Mark.Type.XML);
            ResultsUtils.loadFromFile(new LoadFromXML(), PATH + FILE_NAME + EXT_XML);

            ResultsUtils.meanMarks();
            List<Result> list = ResultsUtils.printTestByCurrentMonth();
            ResultsUtils.printTestsInTheLatestDay(list);

        } catch (IllegalStateException e) {
            System.err.println(e);
        } finally {
            DAO.closeDB();
        }
    }
}