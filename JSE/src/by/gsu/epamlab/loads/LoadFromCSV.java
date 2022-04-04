package by.gsu.epamlab.loads;

import by.gsu.epamlab.utils.classes.DAO;
import by.gsu.epamlab.utils.classes.Utils;
import by.gsu.epamlab.beans.Result;
import by.gsu.epamlab.enums.Fields;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Date;
import java.util.Scanner;

import static by.gsu.epamlab.Constants.*;

public class LoadFromCSV implements Load {

    @Override
    public void load(String fileName) {
        try(Scanner sc = new Scanner(new FileReader(fileName))){
            while(sc.hasNext()){
                String [] elements = sc.next().split(DELIMITER);

                String login = elements[Fields.LOGIN.ordinal()];
                String testName = elements[Fields.TEST_NAME.ordinal()];
                Date date = Utils.parseDate(elements[Fields.DATE.ordinal()]);
                int mark = (int) (TEN * Double.parseDouble(elements[Fields.MARK.ordinal()]));

                DAO.add(new Result(login, testName, date, mark));
            }
        }catch(FileNotFoundException e){
            System.out.println(FILE_NOT_FOUND);
        }
    }
}
