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

import javax.xml.crypto.Data;

public class ProfileDao{

		public Profile getProfile(String id) throws PhotoNetRuntimeException {
			try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()){
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE name = ? ");
				statement.setString(1, id);
				try (ResultSet result = statement.executeQuery()){
					if(result.first()) {
						Profile p = new Profile()
								.setName(result.getString("name"))
								.setHashpass(result.getString("hashPass"));
						String desc = result.getString("description");
						if (!result.wasNull())
							p.setDesc(desc);
						String path = result.getString("imageProfil");
						if (!result.wasNull())
							p.setProfilPicPath(path);
						return p;
					}
					else throw new ProfileNotFoundException("profile not found: " + id );
				}
			}catch(SQLException e){
				throw new DaoRuntimeException("exception raised in the profileDao",e);
			}
		}

	public Profile getProfileWithPhotosId(String id) throws PhotoNetRuntimeException{
		try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()){
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM users LEFT JOIN photos ON users.name = photos.auteur   WHERE users.name = ? ");
			statement.setString(1, id);
			try (ResultSet result = statement.executeQuery()){
				if(result.first()){
					Profile p =  new Profile().setName(result.getString("name"))
							.setHashpass(result.getString("hashPass"));
					String desc = result.getString("description");
					if(!result.wasNull())
							p.setDesc(desc);
					String path = result.getString("imageProfil");
					if(!result.wasNull())
							p.setProfilPicPath(path);
					int pid = result.getInt("photos.id");
					if(!result.wasNull())
							p.addPhoto(new Photo().setId(pid).setTitle(result.getString("photos.titre")));
					while(result.next()){
						p.addPhoto(new Photo().setId(result.getInt("photos.id")).setTitle(result.getString("photos.titre")));
					}
					return p;
				}
				else throw new ProfileNotFoundException("profile not found: " + id );
			}
		}catch(SQLException e){
			throw new DaoRuntimeException("exception raised in the profileDao",e);
		}
	}

	public boolean addNewPorfile(String name, String pass) throws PhotoNetRuntimeException{
		try(Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()){
			PreparedStatement stmt = connection.prepareStatement("INSERT INTO users(name,hashPass) VALUES (?,?)");
			stmt.setString(1,name);
			stmt.setString(2,pass);
			return stmt.executeUpdate() > 0;
		}catch (SQLException e){
			throw new DaoRuntimeException("Exception raisend in the profileDao",e);
		}
	}

	public boolean updateProfileWithPhoto(String name, String desc, String profilPicPath) throws PhotoNetRuntimeException{
		try(Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()){
			PreparedStatement stmt = connection.prepareStatement("UPDATE users SET description = ?, imageProfil = ? WHERE name = ?");
			stmt.setString(3,name);
			stmt.setString(1,desc);
			stmt.setString(2, profilPicPath);
			return stmt.executeUpdate() > 0;
		}catch (SQLException e){
			throw new DaoRuntimeException("Exception raisend in the profileDao",e);
		}
	}

	public boolean updateProfile(String name, String desc) throws PhotoNetRuntimeException{
		try(Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection()){
			PreparedStatement stmt = connection.prepareStatement("UPDATE users SET description = ? WHERE name = ?");
			stmt.setString(2,name);
			stmt.setString(1,desc);
			return stmt.executeUpdate() > 0;
		}catch (SQLException e){
			throw new DaoRuntimeException("Exception raisend in the profileDao",e);
		}
	}
}
