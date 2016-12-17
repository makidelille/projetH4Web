package photoNet.service;

import photoNet.daos.CommentDao;
import photoNet.daos.PhotoDao;
import photoNet.daos.ProfileDao;
import photoNet.exceptions.PhotoNetRuntimeException;
import photoNet.utils.Photo;
import photoNet.utils.Profile;

/**
 * Created by Julien on 17/12/2016.
 */
public class DataService {

    private static final DataService instance = new DataService();

    private DataService(){};

    private PhotoDao photoDao = new PhotoDao();
    private ProfileDao profileDao = new ProfileDao();
    private CommentDao commentDao = new CommentDao();

    public static DataService getInstance(){
        return instance;
    }

    public Profile getProfile(String id) {
        try { //TODO handle try catch;
            return profileDao.getProfile(id);
        } catch (PhotoNetRuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Photo getPhoto(String photoId) {
        try {
            return photoDao.getPhotoWithAuthor(photoId);
        } catch (PhotoNetRuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

}
