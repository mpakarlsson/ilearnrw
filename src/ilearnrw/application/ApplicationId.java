package ilearnrw.application;
/*
 * Copyright (c) 2015, iLearnRW. Licensed under Modified BSD Licence. See licence.txt for details.
 */
import java.io.Serializable;

/** \brief ApplicationId identifies an application in different parts of the system.
 * 
 * It should be a unique identifier
 */
public class ApplicationId implements Serializable{

	private static final long serialVersionUID = 1L;

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
