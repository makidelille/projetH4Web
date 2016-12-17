package photoNet.daos;

import photoNet.exceptions.DaoRuntimeException;
import photoNet.exceptions.PhotoNetRuntimeException;
import photoNet.exceptions.PhotoNotFoundException;
import photoNet.utils.Photo;
import photoNet.utils.Profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PhotoDao {

    public Photo getPhotoWithAuthor(String id) throws PhotoNetRuntimeException {
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM photos INNER JOIN users ON photos.auteur = users.name WHERE id = ? ");
            statement.setString(0, id);
            try (ResultSet result = statement.executeQuery()){
                if(result.first()) return new Photo().setId(Integer.parseInt(id))
                        .setTitle(result.getString("photos.titre"))
                        .setDesc(result.getString("photos.description"))
                        .setPath(result.getString("photos.image"))
                        .setAuthor(new Profile().setName(result.getString("users.name")).setProfilPicPath(result.getString("users.imageProfil")));
                else throw new PhotoNotFoundException("photo not found: " + id );
            }
        }catch(SQLException e){
            throw new DaoRuntimeException("exception raised in the profileDao",e);
        }
    }


}
