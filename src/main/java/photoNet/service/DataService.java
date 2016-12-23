package photoNet.service;

import photoNet.daos.CommentDao;
import photoNet.daos.PhotoDao;
import photoNet.daos.ProfileDao;
import photoNet.exceptions.PhotoNetRuntimeException;
import photoNet.exceptions.ProfileNotFoundException;
import photoNet.utils.Comment;
import photoNet.utils.Photo;
import photoNet.utils.Profile;
import photoNet.utils.Ref;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        }catch(ProfileNotFoundException e1) {
            System.out.println(e1.getMessage());
            return null;
        }catch (PhotoNetRuntimeException e) {
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

    public Photo addPhoto(Photo photo, Part figure) {
        if(figure == null || photo == null) return null;
        try(InputStream is = figure.getInputStream()){
            String uuid = UUID.randomUUID().toString();
            Path target = Paths.get(Ref.PHOTO_MAIN_DIR, uuid + figure.getSubmittedFileName().substring(figure.getSubmittedFileName().lastIndexOf('.')));
            Files.copy(is, target);
            photo.setPath(target.toString());
            photo.setId(photoDao.addPhoto(photo.getTitle(),photo.getDesc(), photo.getAuthor().getName(), photo.getPath()));
            return photo;
        } catch (IOException | PhotoNetRuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Comment addComment(Comment c, int rep) {
        if(c == null) return null;
        try{
            return c.setId(rep == -1 ? commentDao.addNewComment(c.getAuthor().getName(), c.getPhoto().getId(),c.getText(), java.sql.Date.valueOf(c.getDate())) : commentDao.addNewCommentTo(c.getAuthor().getName(), c.getPhoto().getId(),c.getText(),  java.sql.Date.valueOf(LocalDate.parse(c.getDate().toString())),rep));
        } catch (PhotoNetRuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Photo> getRandomPhotos(int i) {
        if(i < 0) return new ArrayList<>();
        try{
            return photoDao.getRandomPhotos(i);
        }catch(PhotoNetRuntimeException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Photo> getAllPhotos() {
        try{
            return photoDao.getAllPhotos();
        }catch(PhotoNetRuntimeException e){
            e.printStackTrace();
        }
        return null;
    }


    public Profile updateProfile(Profile prof, Part profilePic) {
        if(prof == null) return null;
        if(profilePic != null && profilePic.getSize() > 0)
            try(InputStream is = profilePic.getInputStream()){
                String uuid = UUID.randomUUID().toString();
                Path target = Paths.get(Ref.PRROFIL_MAIN_DIR, uuid + profilePic.getSubmittedFileName().substring(profilePic.getSubmittedFileName().lastIndexOf('.')));
                Files.copy(is, target);
                prof.setProfilPicPath(target.toString());
                profileDao.updateProfileWithPhoto(prof.getName(), prof.getDesc(), prof.getProfilPicPath());
                return prof;
            } catch (IOException | PhotoNetRuntimeException e) {
                e.printStackTrace();
                return null;
            }
        else{
            try {
                profileDao.updateProfile(prof.getName(), prof.getDesc());
                return prof;
            } catch (PhotoNetRuntimeException e) {
                e.printStackTrace();
                return null;
            }
        }

    }
}
