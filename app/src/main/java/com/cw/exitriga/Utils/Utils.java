package com.cw.exitriga.Utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	public static void setPref(Context c, String pref, String val) {
		Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
		e.putString(pref, val);
		e.commit();

	}

	public static String getPref(Context c, String pref, String val) {
		return PreferenceManager.getDefaultSharedPreferences(c).getString(pref, val);
	}
	public static void removePref(Context c, String pref) {
		Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
		e.remove(pref);
		e.commit();

	}

	public static boolean isInternetConnected(Context mContext) {

		try {
			ConnectivityManager connect = null;
			connect = (ConnectivityManager) mContext
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			if (connect != null) {
				NetworkInfo resultMobile = connect
						.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

				NetworkInfo resultWifi = connect
						.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

				if ((resultMobile != null && resultMobile
						.isConnectedOrConnecting())
						|| (resultWifi != null && resultWifi
								.isConnectedOrConnecting())) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean isValidEmailAddress(String emailAddress) {
		String expression = "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
		CharSequence inputStr = emailAddress;
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		return matcher.matches();
	}
	
	public static void hideKeyBoard(Context c, View v) {
		InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}
	
	
	
	public static Typeface getHedingUjjain(Context c) {
		try {
			return Typeface.createFromAsset(c.getAssets(), "CaviarDreams.ttf");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static Typeface getLight(Context c) {
		try {
			return Typeface.createFromAsset(c.getAssets(), "ufonts.com_futurabt-medium-opentype.otf");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static Typeface getBold(Context c) {
		try {
			return Typeface.createFromAsset(c.getAssets(), "CaviarDreams_Bold.ttf");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static Typeface getBoldItalic(Context c) {
		try {
			return Typeface.createFromAsset(c.getAssets(), "CaviarDreams_BoldItalic.ttf");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
		public static Typeface getItalic(Context c) {
			try {
				return Typeface.createFromAsset(c.getAssets(), "CaviarDreams_Italic.ttf");
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
	}
	public static Typeface getNormal(Context c) {
		try {
			return Typeface.createFromAsset(c.getAssets(), "FuturaStd-Book.otf");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
		
		
	}



	public static Typeface getNunito(Context c) {
		try {
			return Typeface.createFromAsset(c.getAssets(), "fonts/gothamroundedbook_21018.ttf");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;


	}

	public static Typeface getNunitoMedium(Context c) {
		try {
			return Typeface.createFromAsset(c.getAssets(), "fonts/gothamroundedlight_21020.ttf");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;


	}

	public static Typeface getNunitoBold(Context c) {
		try {
			return Typeface.createFromAsset(c.getAssets(), "fonts/gothamroundedmedium_21022.ttf");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;


	}



//	public static String saveInCache(Bitmap bitmap) {
//		String profile_image = "";
//		File outCacheFile;
//		try {
//
//			outCacheFile = FragSlideText.getOutputMediaFile(Constants.IMAGE_TYPE);
//			FileOutputStream out = new FileOutputStream(outCacheFile);
//			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
//			out.flush();
//			out.close();
//			return outCacheFile.getPath();
//			
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return profile_image;
//	}
//	
	
//	public static File getOutputMediaFile(String imageType) {
//		try {
//			// To be safe, you should check that the SDCard is mounted
//			// using Environment.getExternalStorageState() before doing this.
//			File mediaStorageDir;
//			if (isSDcardMounted()) {
//				mediaStorageDir = new File(Constants.storagePath);
//			} else {
//				mediaStorageDir = new File(Environment.getRootDirectory(), Constants.APP_FOLDER);
//			}
//			// This location works best if you want the created images to be
//			// shared
//			// between applications and persist after your app has been
//			// uninstalled.
//
//			// Create the storage directory if it does not exist
//			if (!mediaStorageDir.exists()) {
//				if (!mediaStorageDir.mkdirs()) {
//					return null;
//				}
//			}
//
//			// Create a media file name
//
//			File mediaFile = null;
//
//			if (imageType.equalsIgnoreCase(Constants.IMAGE_TYPE)) {
//				mediaFile = new File(mediaStorageDir.getPath() + File.separator + new Date().getTime() + ".jpg");
//			} else if (imageType.equalsIgnoreCase(Constants.VIDEO_TYPE)) {
//				mediaFile = new File(mediaStorageDir.getPath() + File.separator + new Date().getTime() + ".mp4");
//			}
//
//			return mediaFile;
//		} catch (Exception e) {
//			return null;
//		}
//	}
//
//	
//	public static boolean isSDcardMounted() {
//
//		String state = Environment.getExternalStorageState();
//		if (state.equals(Environment.MEDIA_MOUNTED)) {
//			return true;
//		}
//		return false;
//	}
}
