package by.gsu.epamlab.beans;

import static by.gsu.epamlab.Constants.DOT;

public class Mark {

    private Mark() {
    }

    private static Type typePatern;

    public enum Type {
        XML, CSV_INT, CSV_DB
    }

    public static void setTypeMark(Type type){
        if(typePatern != null){
            return;
        }
        typePatern = type;
    }

    public static String print(int mark) {
        StringBuilder stringBuilder = new StringBuilder();

        switch (typePatern) {
            case CSV_INT:
                stringBuilder.append(mark / 10);
                break;
            case XML:
                stringBuilder.append(mark / 10).append(DOT).append(mark % 10);
                break;
            case CSV_DB:
                if (mark % 10 > 0){
                    stringBuilder.append(mark / 10).append(DOT).append(mark % 10);
                } else stringBuilder.append(mark / 10);
                break;
        }
        return stringBuilder.toString();
    }
}
