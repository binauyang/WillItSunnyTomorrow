package cn.edu.tju.willitsunnytomorrow.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by N550 on 2016/3/6.
 */
public class LocationUtil {

    private static Context mContext;

    private static LocationRequestListener mLocationRequestListener;

    public interface LocationRequestListener {
        public void postLocation(String location);
    }

    private static LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            final Location l = location;
            if (null != location) {
                final StringBuilder addrSb = new StringBuilder();
                final Geocoder gc = new Geocoder(mContext, Locale.getDefault());
                new AsyncTask<Void, Void, Object>() {
                    @Override
                    protected Object doInBackground(Void... voids) {
                        try {
                            return gc.getFromLocation(l.getLatitude(), l.getLongitude(), 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(Object result) {
                        List<Address> addressList = (List<Address>) result;
                        if (null == addressList) {
                            addrSb.append("no address available");
                            mLocationRequestListener.postLocation(addrSb.toString());
                            return;
                        }
                        if (addressList.size() > 0) {
                            Address address = addressList.get(0);
                            int addrMaxLine = address.getMaxAddressLineIndex();
                            for (int i = 0; i< addrMaxLine; ++i) {
                                addrSb.append(address.getAddressLine(i) + ",");
                            }
                            LocationManager lmgr = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
                            lmgr.removeUpdates(mLocationListener);
                            mLocationRequestListener.postLocation(addrSb.toString());
                        }

                        super.onPostExecute(result);
                    }
                }.execute();
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    public static void requestLocation(Context context, LocationRequestListener l) {
        mContext = context;
        LocationManager lmgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!lmgr.isProviderEnabled(LocationManager.GPS_PROVIDER) && !lmgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            l.postLocation("no address available");
            return;
        }
        mLocationRequestListener = l;
        lmgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 0, mLocationListener);
        lmgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 6000, 0, mLocationListener);
    }
}
