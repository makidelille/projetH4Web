package photoNet.daos;

import photoNet.exceptions.CantAddPhotoException;
import photoNet.exceptions.DaoRuntimeException;
import photoNet.exceptions.PhotoNetRuntimeException;
import photoNet.exceptions.PhotoNotFoundException;
import photoNet.utils.Photo;
import photoNet.utils.Profile;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhotoDao {

    public Photo getPhotoWithAuthor(String id) throws PhotoNetRuntimeException {
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM photos INNER JOIN users ON photos.auteur = users.name WHERE id = ? ");
            statement.setString(1, id);
            try (ResultSet result = statement.executeQuery()){
                if(result.first()) return new Photo().setId(Integer.parseInt(id))
                        .setTitle(result.getString("photos.titre"))
                        .setDesc(result.getString("photos.description"))
                        .setPath(result.getString("photos.image"))
                        .setAuthor(new Profile().setName(result.getString("users.name")).setProfilPicPath(result.getString("users.imageProfil")));
                else throw new PhotoNotFoundException("photo not found: " + id );
            }
        }catch(SQLException e){
            throw new DaoRuntimeException("exception raised in the photoDao",e);
        }
    }


    public int addPhoto(String name, String desc, String author, String imagePath) throws PhotoNetRuntimeException {
        try(Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()){
            PreparedStatement statement = connection.prepareStatement("INSERT INTO photos(`auteur`,`titre`,`description`,`image`) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,author);
            statement.setString(2, name);
            statement.setString(3,desc);
            statement.setString(4,imagePath);
            int rows = statement.executeUpdate();
            if(rows == 0){
                throw new CantAddPhotoException("no rows affected");
            }
            try(ResultSet generatedKeys = statement.getGeneratedKeys()){
                if(generatedKeys.next()){
                    return generatedKeys.getInt(1);
                }else{
                    throw new CantAddPhotoException("failed to get id");
                }
            }
        } catch (SQLException e) {
           throw new DaoRuntimeException("excetpiton raised in the photoDao",e);
        }

    }

    public List<Photo> getRandomPhotos(int i) throws PhotoNetRuntimeException{
        List<Photo> l = new ArrayList<>();
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM photos ORDER BY rand() LIMIT ?");
            statement.setInt(1,i);
            try(ResultSet result = statement.executeQuery()){
                while(result.next()){
                    l.add(new Photo().setId(result.getInt("id")).setTitle(result.getString("titre")));
                }
            }
        } catch (SQLException e) {
            throw new DaoRuntimeException("excetpiton raised in the photoDao",e);
        }

        return l;
    }

    public List<Photo> getAllPhotos() throws PhotoNetRuntimeException{
        List<Photo> l = new ArrayList<>();
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM photos");
            try(ResultSet result = statement.executeQuery()){
                while(result.next()){
                    l.add(new Photo().setId(result.getInt("id")).setTitle(result.getString("titre")));
                }
            }
        } catch (SQLException e) {
            throw new DaoRuntimeException("excetpiton raised in the photoDao",e);
        }

        return l;
    }

    public boolean deletePhoto(String photoId)  throws PhotoNetRuntimeException {
        try(Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()){
            PreparedStatement statement = connection.prepareStatement("DELETE FROM photos WHERE id = ?");
            statement.setString(1,photoId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw  new DaoRuntimeException("excetpiton raised in the photoDao",e);
        }
    }
}
