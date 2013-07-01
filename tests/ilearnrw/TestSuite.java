package ilearnrw;
import ilearnrw.datalogger.DataLoggerTest;
import ilearnrw.user.UserTest;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	DataLoggerTest.class,
	UserTest.class
})
public class TestSuite {

}
