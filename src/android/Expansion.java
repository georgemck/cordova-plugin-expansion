package com.jernung.cordova.expansionfile;

import java.io.BufferedInputStream;
import java.io.IOException;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;

import com.android.vending.expansion.zipfile.APKExpansionSupport;
import com.android.vending.expansion.zipfile.ZipResourceFile;
import com.google.android.vending.expansion.downloader.Helpers;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.util.Base64;
import android.util.Log;

public class Expansion extends CordovaPlugin {
	final static int mainVersion = 1;
	final static int patchVersion = 1;

	/**
	 * Sets the context of the Command. This can then be used to do things like
	 * get file paths associated with the Activity.
	 * 
	 * @param cordova
	 *            The context of the main Activity.
	 * @param webView
	 *            The CordovaWebView Cordova is running in.
	 */
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
	}

	/**
	 * Executes the request and returns PluginResult.
	 * 
	 * @param action
	 *            The action to execute.
	 * @param args
	 *            JSONArry of arguments for the plugin.
	 * @param callbackContext
	 *            The callback id used when calling back into JavaScript.
	 * @return True if the action was valid, false otherwise.
	 */
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) {
		if (action.equals("getFile")) {
			try {
				final String filename = args.getString(0);
				Context ctx = cordova.getActivity().getApplicationContext();
				byte[] data = Expansion.readFile(ctx, filename);
				if (null != data) {
					String encoded = Base64
							.encodeToString(data, Base64.DEFAULT);
					callbackContext.sendPluginResult(new PluginResult(
							PluginResult.Status.OK, encoded));
				} else {
					callbackContext.error("File not found.");
				}
			} catch (Exception e) {
				e.printStackTrace();
				callbackContext.error(e.getMessage());
			}
			return true;
		}
		return false;
	}

	/**
	 * Find and read the file from the expansion file..
	 * 
	 * @param ctx
	 * @param filename
	 * @return
	 */
	private static byte[] readFile(Context ctx, String filename)
			throws IOException {
		ZipResourceFile expansionFile = APKExpansionSupport
				.getAPKExpansionZipFile(ctx, Expansion.mainVersion,
						Expansion.patchVersion);
		if (null == expansionFile) {
			Log.e("Expansion", "File not found.");
			return null;
		}
		String fileName = Helpers.getExpansionAPKFileName(ctx, true,
				Expansion.patchVersion);
		fileName = fileName.substring(0, fileName.lastIndexOf("."));
		AssetFileDescriptor file = expansionFile
				.getAssetFileDescriptor(fileName + "/" + filename);
		if (null == file) {
			Log.e("Expansion", "File not found (" + filename + ").");
			return null;
		}
		int size = (int) file.getLength();
		byte[] data = new byte[size];
		BufferedInputStream buf = new BufferedInputStream(
				file.createInputStream());
		buf.read(data, 0, data.length);
		buf.close();
		return data;
	}
}
