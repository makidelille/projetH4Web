package photoNet.exceptions;

/**
 * Created by Julien on 17/12/2016.
 */
public class ProfileNotFoundException extends PhotoNetRuntimeException {
    public ProfileNotFoundException(String s) {
        super(s);
    }
}
