/** \brief DataLogger package.
 *
 * \note Exactly what package the IProfileAccessUpdater
 * should be part of is still unknown.
 */

package ilearnrw.datalogger;

/** \page "Log format"
 *
 * Actions
 * * Add difficult word
 * * Remove difficult word
 *
 * Profile|Marker|TimeStamp|Action|Value
 * -------|------|---------|------|-----
 * child1 |teacher1|2013-06-14|ADD|cat
 * child1 |child1|2013-06-14|REMOVE|cat
 * 
 *
 */

/** \page About
 *
 * This document was generated using Doxygen from a number of (mainly) stub classes.
 *
 * The documentation is published in the iLearnRW git repository.
 * Currently located in the `playground/DataLogger` directory.
 *
 * To be able to build the documentation the following is required:
 *  
 *  * Doxygen
 *  * Latex
 *  * Graphviz
 *
 */


/** DataLogger class.
 * 
 *
 */
public class DataLogger
{
	private UserStore mUserStore = null;
	
	/** Loads the UserStore from disk.
	 * 
	 * @note filePath does not have to exist.
	 * 	 	 But the directory it is in has to.
	 * 
	 * @param filePath FilePath to a disk copy.
	 * @return true if successful. false if already loaded.
	 */
	public boolean loadUserStore(String filePath) {
		if( mUserStore != null )
			return false;
		mUserStore = new UserStore(filePath);
		return true;
	}
	/**
	 * 
	 * @return The UserStore if loaded, null otherwise.
	 */
	public UserStore getUserStore() {
		return mUserStore;
	}
	
	
}