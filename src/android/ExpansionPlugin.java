package com.jernung.cordova.expansion;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import org.apache.commons.io.IOUtils;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import com.android.vending.expansion.zipfile.APKExpansionSupport;
import com.android.vending.expansion.zipfile.ZipResourceFile;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

public class ExpansionPlugin extends CordovaPlugin implements OnPreparedListener {
	private final static String EXPANSION_PATH = "/Android/obb/";
	private static int MAIN_VERSION = 1;
	private static int PATCH_VERSION = 1;
	private MediaPlayer media;
	private ZipResourceFile expansionFile;

	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		media = new MediaPlayer();
	}

	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		Context ctx = cordova.getActivity().getApplicationContext();
		if (action.equals("getFile")) {
			final String filename = args.getString(0);
			try {
				byte[] data = this.getFile(filename);
				if (data == null) {
					callbackContext.success(0);
				} else {
					String encoded = Base64
							.encodeToString(data, Base64.DEFAULT);
					callbackContext.success(encoded);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		if (action.equals("getPaths")) {
			String[] results = this.getPaths(ctx, MAIN_VERSION, PATCH_VERSION);
			if (results != null) {
				callbackContext.success(results.toString());
			} else {
				callbackContext.success();
			}
			return true;
		}
		if (action.equals("load")) {
			MAIN_VERSION = Integer.parseInt(args.getString(0), 10);
			PATCH_VERSION = Integer.parseInt(args.getString(0), 10);
			this.loadExpansion(MAIN_VERSION, PATCH_VERSION);
			return true;
		}
		if (action.equals("isPlaying")) {
			callbackContext.success(this.isPlaying() ? 1 : 0);
			return true;
		}
		if (action.equals("pauseMedia")) {
			this.pauseMedia();
			return true;
		}
		if (action.equals("playMedia")) {
			final String filename = args.getString(0);
			try {
				this.playMedia(filename);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		if (action.equals("stopMedia")) {
			this.stopMedia();
			return true;
		}
		return false;
	}

	public String[] getPaths(Context ctx, int mainVersion, int patchVersion) {
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

	public byte[] getFile(String filename) throws IOException {
		if (expansionFile == null) {
			Log.e("EXPANSION", "Expansion file not found!");
			return null;
		}
		InputStream file = expansionFile.getInputStream(filename);
		if (file == null) {
			Log.e("EXPANSION", "Filename '" + filename + "' not found!");
			return null;
		}
		byte[] data = IOUtils.toByteArray(file);
		file.close();
		return data;
	}

	public boolean isPlaying() {
		if (media != null && media.isPlaying())
			return true;
		return false;
	}

	public void loadExpansion(int mainVersion, int patchVersion) {
		try {
			Log.e("EXPANSION", "MAIN_VERSION: " + mainVersion
					+ " PATCH_VERSION: " + patchVersion);
			expansionFile = APKExpansionSupport.getAPKExpansionZipFile(
					this.cordova.getActivity().getApplicationContext(),
					mainVersion, patchVersion);
		} catch (IOException e) {
			Log.e("EXPANSION", "Unable to load expansion files.");
			e.printStackTrace();
		}
	}

	@Override
	public void onPrepared(MediaPlayer media) {
		media.start();
	}

	public void pauseMedia() {
		if (media != null && media.isPlaying()) {
			media.pause();
		}
	}

	public void playMedia(String filename) throws IOException {
		if (expansionFile == null) {
			Log.e("EXPANSION", "Expansion file not found!");
			return;
		}
		AssetFileDescriptor file = expansionFile
				.getAssetFileDescriptor(filename);
		if (file == null) {
			Log.e("EXPANSION", "Filename '" + filename + "' not found!");
			return;
		}
		if (this.isPlaying()) {
			Log.e("EXPANSION", "Media is currently playing!");
			this.stopMedia();
		}
		media.reset();
		media.setDataSource(file.getFileDescriptor(), file.getStartOffset(),
				file.getLength());
		media.setOnPreparedListener(this);
		media.prepareAsync();
	}

	public void stopMedia() {
		if (media != null && media.isPlaying()) {
			media.stop();
		}
	}

}
