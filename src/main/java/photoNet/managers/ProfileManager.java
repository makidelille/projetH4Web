package photoNet.managers;

import photoNet.utils.Profile;

public class ProfileManager {

	private static ProfileManager _instance;
	
	public static ProfileManager get_instance() {
		return _instance = (_instance == null ? new ProfileManager() : _instance);
	}
	
	private ProfileManager() {}
	
	public Profile getProfile(String name){
		return new Profile();
	}
	
}
