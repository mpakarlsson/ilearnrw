package ilearnrw;
//import ilearnrw.datalogger.DataLoggerTest;
import ilearnrw.datalogger.UserStoreTest;
import ilearnrw.textclassification.StringMatchesInfoTest;
import ilearnrw.user.UserTest;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	//DataLoggerTest.class,
	UserStoreTest.class,
	UserTest.class,
	StringMatchesInfoTest.class
})
public class TestSuite {
 // nothing 
}
