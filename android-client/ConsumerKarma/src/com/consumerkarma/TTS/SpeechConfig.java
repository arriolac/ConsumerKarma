/*
Licensed by AT&T under 'Software Development Kit Tools Agreement' 2012.
TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION: http://developer.att.com/sdk_agreement/
Copyright 2012 AT&T Intellectual Property. All rights reserved. 
For more information contact developer.support@att.com http://developer.att.com
*/
package com.consumerkarma.TTS;

/** Configuration parameters for this application's account on Speech API. **/
public class SpeechConfig {
	private SpeechConfig() {} // can't instantiate
	
	/** The URL of AT&T Speech API. **/
	public static String serviceUrl() {
    	return "https://api.att.com/rest/1/SpeechToText";
	}
	
	/** The URL of AT&T Speech API OAuth service. **/
	public static String oauthUrl() {
    	return "https://api.att.com/oauth/token";
	}
	
    /** Unobfuscates the OAuth client_id credential for the application. **/
    public static String oauthKey() {
		// TODO: Replace this with code to unobfuscate your OAuth client_id.
		return "f44bbd6a2e52e6528915c90e68a5c733"; //myUnobfuscate(MY_OBFUSCATED_CLIENT_ID);
	}

    /** Unobfuscates the OAuth client_secret credential for the application. **/
    public static String oauthSecret() {
		// TODO: Replace this with code to unobfuscate your OAuth client_secret.
		return "f7c91d31b82d31b6";//myUnobfuscate(MY_OBFUSCATED_CLIENT_SECRET);
    }
}
