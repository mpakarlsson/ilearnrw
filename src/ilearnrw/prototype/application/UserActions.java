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


import ilearnrw.datalogger.UserActionFilter;
import ilearnrw.user.UserAction;
/** 
 * @todo getActions(filter) should not return null? It's easier to return just an empty list
 */

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
	
	
	private List<UserAction> getActions(){
		if(mActions.size() == 0)
			return null;
		
		return mActions;
	}

	
	// TODO: Optimize, one for loop
	public List<UserAction> getActions(UserActionFilter filter){
		boolean hasValues = false;
		
		if(filter == null)
			return getActions();
		
		List<UserAction> filteredActions = new ArrayList<UserAction>();

		if(filter.getUserId() != -1){
			for(UserAction action : mActions){
				if(action.getUserId() == filter.getUserId()){
					filteredActions.add(action);	
					hasValues = true;
				}
			}
		}
		
		if(filter.getApplicationId() != null){
			if(filteredActions.size() > 0){
				for(int i = filteredActions.size()-1; i >= 0; i--){
					if(!filter.getApplicationId().getId().equals(filteredActions.get(i).getApplicationId().getId()))
						filteredActions.remove(i);
				}	
			} else {
				for(UserAction action : mActions){
					if(filter.getApplicationId().getId().equals(action.getApplicationId().getId())){
						filteredActions.add(action);
						hasValues = true;
					}
				}
			}
		}
		
		if(filteredActions.size() == 0 && hasValues)
			return null;
		
		boolean exists = false;
		if(filter.getTags() != null){
			if(filter.getTags().size() > 0){
				if(filteredActions.size() > 0){
					for(int i = filteredActions.size()-1; i>=0; i--){
						for(String tag : filter.getTags()){
							if(filteredActions.get(i).getTag().equals(tag)){
								exists = true;
								break;
							}
						}
						if(!exists)
							filteredActions.remove(i);
						exists = false;
					}
				} else {
					for(UserAction action : mActions){
						for(String tag : filter.getTags()){
							if(action.getTag().equals(tag)){
								filteredActions.add(action);
								hasValues = true;
							}
						}
					}
				}
			}
		}
		
		if(filteredActions.size() == 0 && hasValues)
			return null;
		
		if(filter.getStartTime() != null){
			if(filteredActions.size() > 0){
				for(int i = filteredActions.size()-1; i>=0; i--){
					if(filter.getStartTime().after(filteredActions.get(i).getTimeStamp())){
						filteredActions.remove(i);
					}
				}
			} else {
				for(UserAction action : mActions){
					if(filter.getStartTime().before(action.getTimeStamp())){
						filteredActions.add(action);
						hasValues = true;
					}
				}
			}	
		}
		
		if(filteredActions.size() == 0 && hasValues)
			return null;
		
		if(filter.getEndTime() != null){
			if(filteredActions.size() > 0){
				for(int i = filteredActions.size()-1; i>=0; i--){
					if(filter.getEndTime().before(filteredActions.get(i).getTimeStamp())){
						filteredActions.remove(i);
					}
				}
			} else {
				for(UserAction action : mActions){
					if(filter.getEndTime().after(action.getTimeStamp())){
						filteredActions.add(action);
						hasValues = true;
					}
				}
			}
		}
		
		if(!hasValues)
			return getActions();
		
		return filteredActions;
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
		save();
	}
	
	private void save(){
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
	private void load(){
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
