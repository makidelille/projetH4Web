package photoNet.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import photoNet.exceptions.DaoRuntimeException;
import photoNet.exceptions.PhotoNetRuntimeException;
import photoNet.exceptions.ProfileNotFoundException;
import photoNet.utils.Photo;
import photoNet.utils.Profile;

public class ProfileDao{

		public Profile getProfile(String id) throws PhotoNetRuntimeException {
			try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()){
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE name = ? ");
				statement.setString(0, id);
				try (ResultSet result = statement.executeQuery()){
					if(result.first()) return new Profile()
							.setName(result.getString("name"))
							.setHashpass(result.getString("hashPass"))
							.setDesc(result.getString("description"))
							.setProfilPicPath(result.getString("imageProfil"));
					else throw new ProfileNotFoundException("profile not found: " + id );
				}
			}catch(SQLException e){
				throw new DaoRuntimeException("exception raised in the profileDao",e);
			}
		}

	public Profile getProfileWithPhotosId(String id) throws PhotoNetRuntimeException{
		try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()){
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM users INNER JOIN photos ON users.name = photos.auteur   WHERE name = ? ");
			statement.setString(0, id);
			try (ResultSet result = statement.executeQuery()){
				if(result.first()){
					Profile p =  new Profile().setName(result.getString("name"))
							.setHashpass(result.getString("hashPass"))
							.setDesc(result.getString("description"))
							.setProfilPicPath(result.getString("imageProfil"))
							.addPhoto(new Photo().setId(result.getInt("photos.id")));
					while(result.next()){
						p.addPhoto(new Photo().setId(result.getInt("photos.id")));
					}
					return p;
				}
				else throw new ProfileNotFoundException("profile not found: " + id );
			}
		}catch(SQLException e){
			throw new DaoRuntimeException("exception raised in the profileDao",e);
		}


	}
	
}
