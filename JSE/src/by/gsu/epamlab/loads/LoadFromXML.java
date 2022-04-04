package by.gsu.epamlab.loads;

import by.gsu.epamlab.exceptions.SourceException;
import by.gsu.epamlab.utils.classes.DAO;
import by.gsu.epamlab.utils.classes.Utils;
import by.gsu.epamlab.beans.Result;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.DataOutput;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static by.gsu.epamlab.Constants.*;

public class LoadFromXML extends DefaultHandler implements Load {

    @Override
    public void load(String fileName) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            FileInputStream file = new FileInputStream(fileName);
            parser.parse(file, this);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private String login;

    private enum CurrentEnum {
        RESULTS, STUDENT, LOGIN, TESTS, TEST;
    }

    private CurrentEnum currentEnum;

    private enum TestAttributes {
        NAME, DATE, MARK
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentEnum = CurrentEnum.valueOf(qName.toUpperCase());
        if (currentEnum == CurrentEnum.TEST) {
            String testName = attributes.getValue(TestAttributes.NAME.name().toLowerCase());
            Date date = (Date) Utils.getStringToDate(attributes.getValue(TestAttributes.DATE.name().toLowerCase()));
            int mark = (int) (TEN * Double.parseDouble(attributes.getValue(TestAttributes.MARK.name().toLowerCase())));
            DAO.add(new Result(login, testName, date, mark));
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (currentEnum == CurrentEnum.LOGIN) {
            String value = new String(ch, start, length).trim();
            if (!value.isEmpty()) {
                login = value;
            }
        }
    }
}
