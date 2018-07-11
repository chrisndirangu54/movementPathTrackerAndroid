package xyz.vinayak.maps;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private LatLng lastLastLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        attachLocationListener();

        
        LatLng sourceCoordinates = new LatLng(21.585308, 70.312701);
        lastLastLng = sourceCoordinates;
        mMap.addMarker(new MarkerOptions().position(sourceCoordinates).title("Coding Blocks Noida"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sourceCoordinates,10.0f));

        LatLng destinationCoordinates = new LatLng(21.38040207,70.27864855);
        mMap.addMarker(new MarkerOptions().position(destinationCoordinates).title("Home"));

        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                LatLng latLng = marker.getPosition();

            }

            @Override
            public void onMarkerDrag(Marker marker) {
                MarkerOptions markerOptions = new MarkerOptions().position(lastLastLng).draggable(true);
                mMap.addMarker(markerOptions);
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

                LatLng endLatLng = marker.getPosition();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(endLatLng,10.0f));
                PolylineOptions polylineOptions = new PolylineOptions()
                        .add(endLatLng,lastLastLng);
                lastLastLng = endLatLng;
                mMap.addPolyline(polylineOptions);

            }
        });

    }

    @SuppressLint("MissingPermission")
    private void attachLocationListener() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,50,this);

    }

    @Override
    public void onLocationChanged(Location location) {

        if(location != null) {
            LatLng newLatLng = new LatLng(location.getLatitude(), location.getLongitude());

            PolylineOptions polylineOptions = new PolylineOptions().add(lastLastLng, newLatLng);
            mMap.addPolyline(polylineOptions);
            lastLastLng = newLatLng;
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
}
