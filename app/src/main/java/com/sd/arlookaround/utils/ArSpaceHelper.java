package com.sd.arlookaround.utils;

import android.app.Activity;
import android.widget.Toast;

import com.google.ar.core.ArCoreApk;
import com.google.ar.core.Config;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException;
import com.sd.arlookaround.R;

import java.util.Random;

class ArSpaceHelper {

    private static final int RENDER_MARKER_AT_MIN_DISTANCE = 5; //meters
    private static final int RENDER_MARKER_AT_MAX_DISTANCE = 10000;//meters
    private static final float INVALID_MARKER_SCALE_MODIFIER = -1F;
    private static final float INITIAL_MARKER_SCALE_MODIFIER = 0.5f;

    public static Session setupARSession(Activity activity, Boolean isInstallRequested) throws UnavailableUserDeclinedInstallationException, UnavailableDeviceNotCompatibleException, UnavailableArcoreNotInstalledException, UnavailableSdkTooOldException, UnavailableApkTooOldException {

        if (ArCoreApk.getInstance().requestInstall(activity, !isInstallRequested) == ArCoreApk.InstallStatus.INSTALL_REQUESTED)
            return null;
        else {
            Session session = new Session(activity);
            Config config = new Config(session);
            config.setUpdateMode(Config.UpdateMode.LATEST_CAMERA_IMAGE);
            session.configure(config);
            return session;
        }
    }

    public static void handleSessionException(Activity activity, UnavailableException exception) {
        String errorMsg = "";
        if (exception instanceof UnavailableArcoreNotInstalledException || exception instanceof UnavailableUserDeclinedInstallationException)
            errorMsg = activity.getResources().getString(R.string.arcore_not_installed);
        else if (exception instanceof UnavailableApkTooOldException)
            errorMsg = activity.getResources().getString(R.string.arcore_not_updated);
        else
            errorMsg = activity.getResources().getString(R.string.arcore_not_supported);

        Toast.makeText(activity, errorMsg, Toast.LENGTH_SHORT).show();
    }

    public static float generateRandomHeightBasedOnDistance(int distance) {
        Random random = new Random();
        if (distance >= 0 && distance <= 1000)
            return 1 + random.nextFloat() * (2);
        else if (distance >= 1001 && distance <= 1500)
            return 4 + random.nextFloat() * (2);
        else if (distance >= 1501 && distance <= 2000)
            return 7 + random.nextFloat() * (2);
        else if (distance >= 2001 && distance <= 3000)
            return 10 + random.nextFloat() * (2);
        else if (distance >= 3001 && distance <= RENDER_MARKER_AT_MAX_DISTANCE)
            return 13 + random.nextFloat() * (1);
        else
            return 0f;

    }


    public static float getScaleModifierBasedOnRealDistance(int distance) {
        if (distance >= Integer.MIN_VALUE && distance <= RENDER_MARKER_AT_MIN_DISTANCE)
            return INVALID_MARKER_SCALE_MODIFIER;
        else if (distance >= RENDER_MARKER_AT_MIN_DISTANCE + 1 && distance <= 20)
            return 0.8f;
        else if (distance >= 21 && distance <= 40)
            return 0.75f;
        else if (distance >= 41 && distance <= 60)
            return 0.7f;
        else if (distance >= 61 && distance <= 80)
            return 0.65f;
        else if (distance >= 81 && distance <= 100)
            return 0.6f;
        else if (distance >= 101 && distance <= 1000)
            return 0.55f;
        else if (distance >= 1001 && distance <= 1500)
            return 0.50f;
        else if (distance >= 1501 && distance <= 2000)
            return 0.45f;
        else if (distance >= 2001 && distance <= 2500)
            return 0.40f;
        else if (distance >= 2501 && distance <= 3000)
            return 0.35f;
        else if (distance >= 3001 && distance <= RENDER_MARKER_AT_MAX_DISTANCE)
            return 0.30f;
        else
            return 0.25f;

    }


    public static String getDistanceInStringFormat(int distance) {
        String dist;
        if (distance >= 1000)
            dist = String.format("%.2f", (double) distance / 1000) + " km";
        else
            dist = distance + " mtr";
        return dist;
    }

}
