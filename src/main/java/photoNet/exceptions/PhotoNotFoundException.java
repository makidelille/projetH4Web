package photoNet.exceptions;

/**
 * Created by Julien on 17/12/2016.
 */
public class PhotoNotFoundException extends PhotoNetRuntimeException {
    public PhotoNotFoundException(String s) {
        super(s);
    }
}
