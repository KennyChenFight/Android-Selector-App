package com.example.kenny.selector;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wang.avi.AVLoadingIndicatorView;

import java.lang.reflect.Type;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;

public class GoogleMapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private GoogleMap map;
    private Marker myMarker;
    private String searchType;
    private ArrayList<Place> restaurant;
    private ArrayList<Place> theater;
    private ArrayList<Place> park;
    private ArrayList<Place> store;
    private ArrayList<Place> cafe;
    private AVLoadingIndicatorView loadingIndicatorView;

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updateMyLocationMarker(location);
            lastLocation = location;
        }
    };

    private GoogleApiClient.ConnectionCallbacks connectionCallbacks =
            new GoogleApiClient.ConnectionCallbacks() {
                @Override
                public void onConnected(Bundle bundle) {
                    if (ActivityCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_COARSE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED) {
                        lastLocation = LocationServices.FusedLocationApi
                                .getLastLocation(googleApiClient);
                        LocationRequest locationRequest = LocationRequest.create()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setSmallestDisplacement(1000);
                        LocationServices.FusedLocationApi.requestLocationUpdates(
                                googleApiClient, locationRequest, locationListener);
                        getMyLocationMarker(lastLocation);
                    }
                }

                @Override
                public void onConnectionSuspended(int i) {
                    Logger.d();
                }
            };

    private GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener =
            new GoogleApiClient.OnConnectionFailedListener() {
                @Override
                public void onConnectionFailed(@NonNull ConnectionResult result) {
                    Logger.d(result.getErrorMessage());
                }
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_google_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        connectGoogleApiClient();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fmMap);
        mapFragment.getMapAsync(this);
        // 執行loading畫面
        loadingIndicatorView = view.findViewById(R.id.avi);
        loadingIndicatorView.show();
        Logger.d();
    }
    // 連結GoogleApiClient取得LocationServices
    private void connectGoogleApiClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(connectionCallbacks)
                    .addOnConnectionFailedListener(onConnectionFailedListener)
                    .build();
        }
        googleApiClient.connect();
        Logger.d();
    }


    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
    }
    // 更新使用者的位置並更新Marker
    private void updateMyLocationMarker(Location lastLocation){
        if (myMarker != null) {
            myMarker.remove();
        }
        double latitude = lastLocation.getLatitude();
        double longitude = lastLocation.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        myMarker = map.addMarker(new MarkerOptions().position(latLng).title("").icon(BitmapDescriptorFactory.fromResource(R.drawable.self_icon)));
        CameraPosition camPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(16)
                .build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(camPosition));
    }
    // 取得使用者的位置並添加Marker
    private void getMyLocationMarker(Location lastLocation) {
        double latitude = lastLocation.getLatitude();
        double longitude = lastLocation.getLongitude();
        savePreferences();
        LatLng latLng = new LatLng(latitude, longitude);
        CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(getActivity());
        map.setInfoWindowAdapter(adapter);
        myMarker = map.addMarker(new MarkerOptions().position(latLng).title("我的位置").snippet("按此進入隨機轉盤" + "\n" + "若附近無此類型則回到挑選類型").icon(BitmapDescriptorFactory.fromResource(R.drawable.self_icon)));
        myMarker.showInfoWindow();
        CameraPosition camPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(16)
                .build();
        showSearchPlaces();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(camPosition));

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if(marker.getTitle().equals("我的位置")) {
                    SharedPreferences preferences = getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
                    int number = preferences.getInt("placeNumber", 1);
                    Logger.d(searchType);
                    if(number == 0) {
                        Fragment locationFragment = new LocationFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        if (!locationFragment.isAdded()) {
                            transaction.replace(R.id.topicContainer, locationFragment, locationFragment.getClass().getName());
                            Logger.d();
                        }
                        else {
                            transaction.show(locationFragment);
                            Logger.d();
                        }
                        transaction.commit();
                    }
                    else if(number == 1) {
                        toNavigationForOne();
                    }
                    else {
                        Fragment googleMapFragment = GoogleMapFragment.this;
                        Fragment locationRotateFragment = new LocationRotateFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        if (!locationRotateFragment.isAdded()) {
                            transaction.add(R.id.topicContainer, locationRotateFragment, locationRotateFragment.getClass().getName());
                            transaction.addToBackStack(locationRotateFragment.getClass().getName());
                            Logger.d();
                        }
                        else {
                            transaction.show(locationRotateFragment);
                            Logger.d();
                        }
                        transaction.hide(googleMapFragment);
                        transaction.commit();
                    }
                }
            }
        });
    }
    // 根據使用者想要的類型顯示景點
    private void showSearchPlaces() {
        loadPerferences();
        double latitude = lastLocation.getLatitude();
        double longitude = lastLocation.getLongitude();
        String url = getNearbyPlacesUrl(latitude, longitude, searchType);
        Object dataTransfer[] = new Object[2];
        dataTransfer[0] = map;
        dataTransfer[1] = url;

        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(getActivity());
        getNearbyPlacesData.execute(dataTransfer);
        Logger.d();
    }
    // 存取使用者想要搜尋附近的哪一類型的景點
    private void loadPerferences() {
        SharedPreferences preferences = getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        searchType = preferences.getString("type", "");
        Logger.d(searchType);
    }
    // 取得要傳去Google Place Web Service的網址
    private String getNearbyPlacesUrl(double latitude, double longitude, String type) {
        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        stringBuilder.append("location=" + latitude + "," + longitude);
        stringBuilder.append("&radius=500");
        stringBuilder.append("&type=" + type);
        stringBuilder.append("&key=" + "AIzaSyC63lWsqP6jcNoquzhzYPHRniIjgXhhV74");
        Logger.d(stringBuilder.toString());
        return stringBuilder.toString();
    }

    private void savePreferences() {
        double latitude = lastLocation.getLatitude();
        double longitude = lastLocation.getLongitude();
        SharedPreferences preferences = getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        preferences.edit().putString("myLatitude",  String.valueOf(latitude)).putString("myLongitude", String.valueOf(longitude)).apply();
        Logger.d();
    }
    public void toNavigationForOne() {
        SharedPreferences preferences = getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String myLatitude = preferences.getString("myLatitude", "");
        String myLongitude = preferences.getString("myLongitude", "");
        String toLatitude = "";
        String toLongitude = "";

        Type listOfObjects = new TypeToken<List<Place>>(){}.getType();
        Gson gson = new Gson();
        String json;

        switch (searchType) {
            case "restaurant":
                json = preferences.getString("restaurantList", "");
                restaurant = gson.fromJson(json, listOfObjects);
                toLatitude = restaurant.get(0).getLat();
                toLongitude = restaurant.get(0).getLng();
                break;
            case "movie_theater":
                json = preferences.getString("movieList", "");
                theater = gson.fromJson(json, listOfObjects);
                toLatitude = theater.get(0).getLat();
                toLongitude = theater.get(0).getLng();
                break;
            case "park":
                json = preferences.getString("parkList", "");
                park = gson.fromJson(json, listOfObjects);
                toLatitude = park.get(0).getLat();
                toLongitude = park.get(0).getLng();
                break;
            case "department_store":
                json = preferences.getString("storeList", "");
                store = gson.fromJson(json, listOfObjects);
                toLatitude = store.get(0).getLat();
                toLongitude = store.get(0).getLng();
                break;
            case "cafe":
                json = preferences.getString("cafeList", "");
                cafe = gson.fromJson(json, listOfObjects);
                toLatitude = cafe.get(0).getLat();
                toLongitude = cafe.get(0).getLng();
                break;
        }
        String url = String.format("http://maps.google.com/maps?saddr=%s,%s&daddr=%s,%s", myLatitude,
                myLongitude, toLatitude, toLongitude);
        Intent intent = new Intent();
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
