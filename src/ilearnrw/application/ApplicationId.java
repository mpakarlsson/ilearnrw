package ilearnrw.application;

/** \brief ApplicationId identifies an application in different parts of the system.
 * 
 * It should be a unique identifier
 */
public class ApplicationId {

	String mId;

	public ApplicationId(String id) {
		mId = id;
	}
	
	public String getId() {
		return mId;
	}
	
	public boolean equals(ApplicationId id) {
		return mId.equals(id);
	}

}
