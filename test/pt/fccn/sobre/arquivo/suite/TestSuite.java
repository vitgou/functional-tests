package pt.fccn.sobre.arquivo.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import pt.fccn.sobre.arquivo.tests.CommonQuestionsTest;
import pt.fccn.sobre.arquivo.tests.ExamplesTest;

	
/**
 * @author João Nobre
 *
 */
@RunWith( Suite.class )
@SuiteClasses({ CommonQuestionsTest.class , ExamplesTest.class }) 
public class TestSuite {

}

