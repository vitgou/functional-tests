package pt.fccn.mobile.arquivo.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import pt.fccn.mobile.arquivo.tests.AdvancedTest;
import pt.fccn.mobile.arquivo.tests.FullTextSearchTest;
import pt.fccn.mobile.arquivo.tests.URLSearchTest;

/**
 * @author João Nobre
 *
 */
@RunWith( Suite.class )
@SuiteClasses( { AdvancedTest.class } )
//Error , AdvancedTest.class
//Success FullTextSearchTest.class , URLSearchTest.class
public class TestSuite {

}
