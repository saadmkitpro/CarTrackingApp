package com.khanstech.ownerdriverapp.helper;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapFunctionality {

    public static Marker addMarker(GoogleMap map, double lat, double lon,
                                   String title, String snippet, boolean isIcon, int id) {
        if (map != null)
            return map.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lon))
                    .title(title)
                    .snippet(snippet)
                    .icon(isIcon ? BitmapDescriptorFactory.fromResource(id)
                            : BitmapDescriptorFactory.defaultMarker(id)));
        else
            return null;
    }

    public static void GotoLocation(GoogleMap map, LatLng pos, float zoom) {
        CameraUpdate cameraUpdate = CameraUpdateFactory
                .newLatLngZoom(pos, zoom);
        map.animateCamera(cameraUpdate);
    }

    public static List<Address> GetAddressFromLocation(Context context,
                                                       LatLng pos) {
        List<Address> addresses = null;
        try {
            Geocoder geocoder = new Geocoder(context);
            addresses = geocoder
                    .getFromLocation(pos.latitude, pos.longitude, 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses;
    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
