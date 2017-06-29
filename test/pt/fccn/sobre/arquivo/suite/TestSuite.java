package pt.fccn.sobre.arquivo.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import pt.fccn.sobre.arquivo.tests.CommonQuestionsTest;
import pt.fccn.sobre.arquivo.tests.ExamplesTest;
import pt.fccn.sobre.arquivo.tests.FooterTest;
import pt.fccn.sobre.arquivo.tests.PublicationsTest;
import pt.fccn.sobre.arquivo.tests.SiteMapTest;

	
/**
 * @author João Nobre
 *
 */
@RunWith( Suite.class )
@SuiteClasses( {  CommonQuestionsTest.class , ExamplesTest.class , FooterTest.class , PublicationsTest.class } ) //TODO SiteMapTest.class    
public class TestSuite {

}

