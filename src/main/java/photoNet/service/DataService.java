package photoNet.service;

import photoNet.daos.CommentDao;
import photoNet.daos.PhotoDao;
import photoNet.daos.ProfileDao;
import photoNet.exceptions.PhotoNetRuntimeException;
import photoNet.exceptions.ProfileNotFoundException;
import photoNet.utils.Comment;
import photoNet.utils.Photo;
import photoNet.utils.Profile;

import java.util.List;

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

    public Profile getProfile(String id, boolean withPhotos) {
        if(id == null) return null;
        try {
            return withPhotos ? profileDao.getProfileWithPhotosId(id) : profileDao.getProfile(id);
        } catch (PhotoNetRuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Photo getPhoto(String photoId) {
        if(photoId == null) return null;
        try {
            return photoDao.getPhotoWithAuthor(photoId);
        } catch (PhotoNetRuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Comment> getCommentsForPhoto(String photoId){
        if(photoId == null) return null;
        try {
            return commentDao.getCommentsForPhoto(photoId);
        } catch (PhotoNetRuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isNameFreeToUser(String regName) {
        if(regName == null) return false;
        try{
            profileDao.getProfile(regName);
            return false;
        } catch (ProfileNotFoundException e) {
            return true;
        } catch (PhotoNetRuntimeException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public boolean createNewUser(String regName, String regPass) {
        if(regName == null || regPass == null) return false;
        try {
            return profileDao.addNewPorfile(regName,regPass);
        } catch (PhotoNetRuntimeException e) {
            e.printStackTrace();
            return false;
        }

    }
}
