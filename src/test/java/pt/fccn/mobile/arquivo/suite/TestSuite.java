package pt.fccn.mobile.arquivo.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import pt.fccn.mobile.arquivo.tests.FullTextSearchTest;
import pt.fccn.mobile.arquivo.tests.URLSearchTest;
import pt.fccn.mobile.arquivo.tests.imagesearch.ImageAdvancedSearchTest;
import pt.fccn.mobile.arquivo.tests.imagesearch.ImageSearchDirectUrlTest;
import pt.fccn.mobile.arquivo.tests.imagesearch.ImageSearchTest;

/**
 *
 * @author João Nobre
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ FullTextSearchTest.class, URLSearchTest.class, ImageAdvancedSearchTest.class,
		ImageSearchDirectUrlTest.class, ImageSearchTest.class })
public class TestSuite {

}
