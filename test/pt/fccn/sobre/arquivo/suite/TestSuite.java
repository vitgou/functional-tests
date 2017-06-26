package pt.fccn.sobre.arquivo.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import pt.fccn.arquivo.tests.AdvancedTest;
import pt.fccn.arquivo.tests.ReplayTest;
import pt.fccn.arquivo.tests.TestArcproxy;
import pt.fccn.arquivo.tests.TestSearchOneTerm;
import pt.fccn.arquivo.tests.TestSearchOneTermOpenSearch;
import pt.fccn.arquivo.tests.TestSponsorImage;
import pt.fccn.arquivo.tests.UrlsearchTest;
import pt.fccn.sobre.arquivo.tests.CommonQuestionsTest;

	
/**
 * @author João Nobre
 *
 */
@RunWith( Suite.class )
@SuiteClasses({ CommonQuestionsTest.class }) 
public class TestSuite {

}

