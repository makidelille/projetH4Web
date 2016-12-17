package photoNet.exceptions;

import java.sql.SQLException;

/**
 * Created by Julien on 17/12/2016.
 */
public class PhotoNetRuntimeException extends Exception {
    public PhotoNetRuntimeException(String s, SQLException e) {
        super(s,e);
    }

    public PhotoNetRuntimeException(String s) {
        super(s);
    }
}
