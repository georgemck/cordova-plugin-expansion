package com.jernung.cordova.expansionfile;

import java.io.File;
import java.util.Vector;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.Environment;

public class Expansion extends CordovaPlugin {
	private final static String EXPANSION_PATH = "/Android/obb/";
	private final static int MAIN_VERSION = 1;
	private final static int PATCH_VERSION = 1;

	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
	}

	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) {
		Context ctx = cordova.getActivity().getApplicationContext();
		if (action.equals("getFile")) {
			return true;
		}
		if (action.equals("getPaths")) {
			String[] results = getAPKExpansionFiles(ctx, MAIN_VERSION,
					PATCH_VERSION);
			if (results != null) {
				callbackContext.success(results.toString());
			} else {
				callbackContext.error("Unable to retrieve expansions paths.");
			}
			return true;
		}
		return false;
	}

	static String[] getAPKExpansionFiles(Context ctx, int mainVersion,
			int patchVersion) {
		String packageName = ctx.getPackageName();
		Vector<String> ret = new Vector<String>();
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File root = Environment.getExternalStorageDirectory();
			File expPath = new File(root.toString() + EXPANSION_PATH
					+ packageName);
			if (expPath.exists()) {
				if (mainVersion > 0) {
					String strMainPath = expPath + File.separator + "main."
							+ mainVersion + "." + packageName + ".obb";
					File main = new File(strMainPath);
					if (main.isFile()) {
						ret.add(strMainPath);
					}
				}
				if (patchVersion > 0) {
					String strPatchPath = expPath + File.separator + "patch."
							+ mainVersion + "." + packageName + ".obb";
					File main = new File(strPatchPath);
					if (main.isFile()) {
						ret.add(strPatchPath);
					}
				}
			}
		}
		String[] retArray = new String[ret.size()];
		ret.toArray(retArray);
		return retArray;
	}
}
