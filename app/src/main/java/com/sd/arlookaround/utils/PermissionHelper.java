package com.sd.arlookaround.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

class PermissionHelper {
    private static final int CAMERA_PERMISSION_CODE = 0;
    private static final int MULTIPLE_PERMISSION_CODE = 1;
    private static final String PACKAGE = "package";
    private static final String CAMERA_PERMISSION = Manifest.permission.CAMERA;

    public static Boolean hasCameraAndLocationPermission(Activity activity) {

        return (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);

    }

    public static Boolean hasCameraPermission(Activity activity) {
        return ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestCameraPermission(Activity activity) {
        String[] permissions = new String[]{Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(
                activity, permissions, CAMERA_PERMISSION_CODE);
    }

    public static void requestCameraAndLocationPermission(Activity activity) {
        String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};
        ActivityCompat.requestPermissions(
                activity, permissions, MULTIPLE_PERMISSION_CODE);
    }

    public static Boolean shouldShowRequestPermissionRationale(Activity activity) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, CAMERA_PERMISSION);
    }

    public static void launchPermissionSettings(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts(PACKAGE, activity.getPackageName(), null));
        activity.startActivity(intent);
    }


}
