package by.gsu.epamlab.exceptions;

public class SourceException extends Exception{
    public SourceException(String message){
        super(message);
    }
    @Override
    public String getMessage(){ return super.getMessage();}
}
