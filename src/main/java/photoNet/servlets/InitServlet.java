package photoNet.servlets;

import com.oracle.webservices.internal.api.message.PropertySet;
import photoNet.utils.Ref;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Julien on 23/12/2016.
 */
public class InitServlet extends HttpServlet {


    @Override
    public void init() throws ServletException {
        System.out.println("Initializing...");
        Properties prop = new Properties();
        InputStream configFileStream = getClass().getClassLoader().getResourceAsStream("server.properties");
        try {
            prop.load(configFileStream);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("failed to initalized");
            return;
        }
        Ref.CONTEXT = prop.getProperty("server.context");
        Ref.PHOTO_MAIN_DIR = prop.getProperty("upload.photo.location");
        Ref.PRROFIL_MAIN_DIR = prop.getProperty("upload.profile.location");
        System.out.println("initializing done !");
    }
}
