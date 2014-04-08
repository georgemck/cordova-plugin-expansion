package com.jernung.cordova.expansionfile;

import com.google.android.vending.expansion.downloader.impl.DownloaderService;

public class ExpansionDownloaderService extends DownloaderService {
	public static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwfbJgVyApOKSfeNtWqQPdikWCWYzNfh4ujKVxv5QZRTFxAKlfZnhT563Ttv1tUSS5OOBHiC+FJfTWKowcWTwRpT0+3WAD+5GiFpCE2khivssSrKxvL3A3dU+MhNp+CndVzMX/jIYTq5WPakV74oEATJT1MUCrWNklQTirt8H2cwtMZ7A7Nlhw8dn3gLyThEMyFSQN/J8au9H9NvPyQA8g9HjVJbC6EBQxotfnwWxTkmcD4nFStS5oelKCWrvmyzceYrsDTYGAL8wXNd+5RZ62B7w1jVnUS6JMBVCnpfTN/BeH80KcLmr3gBVDEbyjKoH6Ov47FgwLJWQc/+fKjNJvwIDAQAB";
	public static final byte[] SALT = new byte[] { 1, 42, -12, -1, 54, 98,
			-100, -12, 43, 2, -8, -4, 9, 5, -106, -107, -33, 45, -1, 84 };

	@Override
	public String getPublicKey() {
		return BASE64_PUBLIC_KEY;
	}

	@Override
	public byte[] getSALT() {
		return SALT;
	}

	@Override
	public String getAlarmReceiverClassName() {
		return ExpansionAlarmReceiver.class.getName();
	}
}
