package com.mattwaqar.audioguide;

import android.app.Application;

import com.mattwaqar.audioguide.models.Track;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class AudioGuideApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		ParseObject.registerSubclass(Track.class);
		Parse.initialize(this, "PcaEtwm2yHVAvLrrwZzGT3rTEJc7MOVDjCOv9FuR", 
				"xRBCzqMftva1Y5X3LC8Ovi9ImIfIY8lCqVILEjAm");

		/*
		 * This app lets an anonymous user create and save data.
		 * An anonymous user is a user that can be created
		 * without a username and password but still has all of the same
		 * capabilities as any other ParseUser.
		 * 
		 * After logging out, an anonymous user is abandoned, and its data is no
		 * longer accessible. In your own app, you can convert anonymous users
		 * to regular users so that data persists.
		 * 
		 * Learn more about the ParseUser class:
		 * https://www.parse.com/docs/android_guide#users
		 */
		ParseUser.enableAutomaticUser();

		/*
		 * For more information on app security and Parse ACL:
		 * https://www.parse.com/docs/android_guide#security-recommendations
		 */
		ParseACL defaultACL = new ParseACL();

		/*
		 * If you would like all objects to be private by default, remove this
		 * line
		 */
		defaultACL.setPublicReadAccess(true);
		defaultACL.setPublicWriteAccess(true);

		ParseACL.setDefaultACL(defaultACL, true);
	}

}