package pt.fccn.mobile.arquivo.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import pt.fccn.mobile.arquivo.tests.FullTextSearchTest;

/**
 * @author João Nobre
 *
 */
@RunWith( Suite.class )
@SuiteClasses( { FullTextSearchTest.class } )
//TODO AdvancedSearchTest.class, URLSearchTest.class
public class TestSuite {

}
