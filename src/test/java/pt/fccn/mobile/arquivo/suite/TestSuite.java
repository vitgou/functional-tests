package pt.fccn.mobile.arquivo.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import pt.fccn.mobile.arquivo.tests.FullTextSearchTest;
import pt.fccn.mobile.arquivo.tests.URLSearchTest;

/**
 * @author João Nobre
 *
 */
@RunWith( Suite.class )
@SuiteClasses( { FullTextSearchTest.class , URLSearchTest.class } )
//TODO review:  AdvancedTest.class
public class TestSuite {

}
