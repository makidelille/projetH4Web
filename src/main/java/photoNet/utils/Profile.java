package photoNet.utils;

import java.util.ArrayList;
import java.util.List;

public class Profile {

	private String name;
	private String hashpass;
	private String desc;
	private String profilPicPath;
	private List<Photo> photos = new ArrayList<>();
	
	public Profile(){}

	public Profile(String name, String hashPass, String description, String imageProfil) {
		this.name = name;
		this.hashpass = hashPass;
		this.desc = description;
		this.profilPicPath = imageProfil;
	}


	public String getName() {
		return name;
	}

	public Profile setName(String name) {
		this.name = name;
		return  this;
	}

	public String getDesc() {
		return desc;
	}

	public Profile setDesc(String desc) {
		this.desc = desc;
		return  this;
	}

	public String getProfilPicPath() {
		return profilPicPath;
	}

	public Profile setProfilPicPath(String profilPicPath) {
		this.profilPicPath = profilPicPath;
		return  this;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public Profile setPhotos(List<Photo> photos) {
		this.photos = photos;
		return  this;
	}

	public String getHashpass() {
		return hashpass;
	}

	public Profile setHashpass(String hashpass) {
		this.hashpass = hashpass;
		return  this;
	}

	public Profile addPhoto(Photo p) {
		this.photos.add(p);
		return this;
	}
}
