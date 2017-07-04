package pt.fccn.arquivo.tests.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class AnalyzeURLs {
	
	
	/**
	 * Check if link exists
	 * @param URLName
	 * @param text
	 * @return
	 */
	public static int linkExists( String URLName ) {
	    boolean redirect = false;
		try {
	    	System.out.println( "[linkExists] url[" + URLName + "]" );
	    	
	    	HttpURLConnection con = ( HttpURLConnection ) new URL( URLName ).openConnection( );
	    	con.setConnectTimeout( 5000 );
	    	con.setRequestMethod( "HEAD" );
	    	con.addRequestProperty( "Accept-Language", "en-US,en;q=0.8" );
	    	con.addRequestProperty( "User-Agent", "Mozilla" );
	    	con.addRequestProperty( "Referer", "google.com" );
	    	
	    	// normally, 3xx is redirect
	    	int status = con.getResponseCode( );
	    	System.out.println( "Status-code = " + status );
	  	    redirect = checkRedirect( status );
	    	System.out.println( "[linkExists] url[" + URLName + "] Status-code = " + con.getResponseCode( ) );
	    	con.disconnect( );
	    	
	    	while( redirect ) {
	    		// get redirect url from "location" header field
	    		String newUrl = con.getHeaderField( "Location" );
	    		System.out.println( "Redirect: true url["+URLName+"] newurl["+newUrl+"]" );
	    		// get the cookie if need, for login
	    		String cookies = con.getHeaderField( "Set-Cookie" );
	    		
	    		// open the new connection again
				con = ( HttpURLConnection ) new URL( newUrl ).openConnection( );
				con.setConnectTimeout( 5000 );
				con.setRequestMethod( "HEAD" );
	    		con.setRequestProperty( "Cookie", cookies );
	    		con.addRequestProperty( "Accept-Language", "en-US,en;q=0.8" );
	    		con.addRequestProperty( "User-Agent", "Mozilla" );
	    		con.addRequestProperty( "Referer", "google.com" );
	    		status = con.getResponseCode( );

	    		URLName = newUrl;
	    		System.out.println( "Novo redirect status = " + status + " message = " + con.getResponseMessage( ) );
	    		redirect = checkRedirect( status );
	    		con.disconnect( );
	    	}
	    	
	    	//System.out.println( "Compare textTolink.get( "+text+" ) = " + textTolink.get( text ) + " URLName = " + URLName + " Status-code = " + status );
	    	return status;
	
	    } catch ( MalformedURLException e ) {
	    	System.out.println( "MalformedURLException e = " + e.getMessage( ) );
			return -1;
		} catch ( IOException e ) {
			System.out.println( "IOException e = " + e.getMessage( ) );
			return -1;
		}
	    
	}
	
	/**
	 * Check if link exists
	 * @param URLName
	 * @return
	 */
	public static boolean linkExists( String URLName , String text , Map< String, String > textTolink ) {
	    boolean redirect = false;
		try {
	    	System.out.println( "[Footer] url[" + URLName + "]" );
	    	
	    	HttpURLConnection con = ( HttpURLConnection ) new URL( URLName ).openConnection( );
	    	con.setConnectTimeout( 5000 );
	    	con.setRequestMethod( "HEAD" );
	    	con.addRequestProperty( "Accept-Language", "en-US,en;q=0.8" );
	    	con.addRequestProperty( "User-Agent", "Mozilla" );
	    	con.addRequestProperty( "Referer", "google.com" );
	    	
	    	// normally, 3xx is redirect
	    	int status = con.getResponseCode( );
	    	System.out.println( "Status-code = " + status );
	    	redirect = checkRedirect( status );
	    	
	    	System.out.println( "[Footer] url[" + URLName + "] Status-code = " + con.getResponseCode( ) );
	    	con.disconnect( );
	    	
	    	while( redirect ) {
	    		// get redirect url from "location" header field
	    		String newUrl = con.getHeaderField( "Location" );
	    		System.out.println( "Redirect: true url["+URLName+"] newurl["+newUrl+"]" );
	    		// get the cookie if need, for login
	    		String cookies = con.getHeaderField( "Set-Cookie" );
	    		
	    		// open the new connection again
				con = ( HttpURLConnection ) new URL( newUrl ).openConnection( );
				con.setConnectTimeout( 5000 );
				con.setRequestMethod( "HEAD" );
	    		con.setRequestProperty( "Cookie", cookies );

		    	
		    	con.addRequestProperty( "Accept-Language", "en-US,en;q=0.8" );
	    		con.addRequestProperty( "User-Agent", "Mozilla" );
	    		con.addRequestProperty( "Referer", "google.com" );
	    		status = con.getResponseCode( );

	    		URLName = newUrl;
	    		System.out.println( "Novo redirect status = " + status + " message = " + con.getResponseMessage( ) );
	    		redirect = checkRedirect( status );
	    		con.disconnect( );
	    	}
	    	
	    	System.out.println( "Compare textTolink.get( "+text+" ) = " + textTolink.get( text ) + " URLName = " + URLName + " Status-code = " + status );
	    	if( status == HttpURLConnection.HTTP_OK )
	    		System.out.println( status + " == " + HttpURLConnection.HTTP_OK );
	    	else
	    		System.out.println( status + " != " + HttpURLConnection.HTTP_OK );
	    		
	    	if(  textTolink.get( text ).trim( ).equals( URLName.trim( ) ) )
	    		return true;
	    	else
	    		return false;
	
	    } catch ( MalformedURLException e ) {
	    	System.out.println( "MalformedURLException e = " + e.getMessage( ) );
			return false;
		} catch ( IOException e ) {
			System.out.println( "IOException e = " + e.getMessage( ) );
			return false;
		}
	    
	}
	
	
	/**
	 * 
	 * @param status
	 * @return
	 */
	public static boolean checkRedirect( int status ){
		if ( status != HttpURLConnection.HTTP_OK ) {
    		if ( status == HttpURLConnection.HTTP_MOVED_TEMP
    			|| status == HttpURLConnection.HTTP_MOVED_PERM
    				|| status == HttpURLConnection.HTTP_SEE_OTHER )
    		return true;
    	}
		return false;
	}
	
	/**
	 * 
	 * @param status
	 * @return
	 */
	public static boolean checkOk( int status ){
		return ( status == HttpURLConnection.HTTP_OK ); 
	}
	
	
}
