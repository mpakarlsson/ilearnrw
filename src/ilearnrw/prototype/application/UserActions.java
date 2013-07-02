package ilearnrw.prototype.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import ilearnrw.user.UserAction;

public class UserActions {
	private List<UserAction> mActions;
	private Logger mLogger;

	private String mFilePath;
	
	public UserActions(String filePath){
		
		mActions = new ArrayList<UserAction>();
		mFilePath = new StringBuilder(filePath).insert(filePath.length()-3, "_LOG").toString();
		mLogger = Logger.getLogger("UserActions");
		
		load();
	}
	
	
	public List<UserAction> getActions(){
		if(mActions.size() == 0)
			return null;
		
		return mActions;
	}
	
	public UserAction getAction(int i){
		if(i>=0 && i<mActions.size())
			return mActions.get(i);
		
		return null;
	}
	
	public void addUserAction(UserAction action){
		if(action == null)
			return;
		mActions.add(action);
	}
	
	public void addUserActionTest(UserAction action){
		mActions.add(action);
	}
	
	public void clearLog(){
		mActions.clear();
		save();
	}
	
	public void save(){
		try {
			FileOutputStream fos = new FileOutputStream(new File(mFilePath));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(mActions);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			mLogger.severe("Could not save User actions:" + e.getMessage());
			mActions.clear();
		} catch (IOException e) {
			mLogger.severe("Could not save User actions:" + e.getMessage());
			mActions.clear();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void load(){
		try {
			FileInputStream fis = new FileInputStream(new File(mFilePath));
			ObjectInputStream ois = new ObjectInputStream(fis);
			mActions = (ArrayList<UserAction>) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			//mLogger.severe("Could not load UserActions: " + e.getMessage());
			mActions = new ArrayList<UserAction>();
		} catch (IOException e) {
			mLogger.severe("Could not load UserActions: " + e.getMessage());
			mActions = new ArrayList<UserAction>();
		} catch (ClassNotFoundException e) {
			mLogger.severe("Could not load UserActions: " + e.getMessage());
			mActions = new ArrayList<UserAction>();
		}
	}
}
