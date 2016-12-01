package com.zippi.test;
import junit.framework.TestCase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.allowify.controller.BookmarkController;
import com.zippi.test.code.CodeControllerTest;
import com.zippi.test.code.UserControllerTest;

@RunWith(Suite.class)
@SuiteClasses({CodeControllerTest.class,BookmarkController.class})
public class RunTestSuite extends TestCase {
	
	@BeforeClass
    public static void doYourOneTimeSetup() {
        
    }

    @AfterClass
    public static void doYourOneTimeTeardown() {
        
    }   

}
