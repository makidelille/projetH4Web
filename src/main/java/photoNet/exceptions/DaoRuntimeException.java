package photoNet.exceptions;

import java.sql.SQLException;

/**
 * Created by Julien on 17/12/2016.
 */
public class DaoRuntimeException extends PhotoNetRuntimeException {
    public DaoRuntimeException(String s, SQLException e) {
        super(s,e);
    }
}
