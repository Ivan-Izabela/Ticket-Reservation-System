package ft.services;

public class FTException extends Exception{
    public void FTExceptionException(){}
    public FTException(String message)
    {
        super(message);
    }
    public FTException(String message , Throwable cause)
    {
        super(message,cause);
    }

}
