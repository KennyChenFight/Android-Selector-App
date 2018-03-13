package com.example.kenny.selector;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wang.avi.AVLoadingIndicatorView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {
    private Context context;
    private String searchType;
    private String googlePlacesData;
    private String googlePlacesDetail[];
    private GoogleMap map;
    private String nearbyPlaceUrl;
    private List<HashMap<String, String>> nearbyPlaceList = null;
    private List<HashMap<String, String>> nearbyPlaceListDetail = new ArrayList<>();
    private List<Place> restaurant = new ArrayList<>();
    private List<Place> cafe = new ArrayList<>();
    private List<Place> park = new ArrayList<>();
    private List<Place> theater = new ArrayList<>();
    private List<Place> store = new ArrayList<>();

    public GetNearbyPlacesData(Context context) {
        this.context = context;
    }

    // 取得googleMap以及url，並傳送實作url的get
    @Override
    protected String doInBackground(Object... objects) {
        map = (GoogleMap) objects[0];
        nearbyPlaceUrl = (String)objects[1];

        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googlePlacesData = downloadUrl.readUrl(nearbyPlaceUrl);
            Logger.d("nearbyPlaceUrl:" + nearbyPlaceUrl);
        } catch (Exception ex) {
            Logger.d(ex.getMessage());
        }

        return googlePlacesData;
    }
    // 顯示在googleMap
    @Override
    protected void onPostExecute(String s) {
        DataParser dataParser = new DataParser();
        nearbyPlaceList = dataParser.parseNearbyPlaces(s);
        if(checkExitNearbyPlaces(nearbyPlaceList)) {
            parseNearbyPlacesDetail(nearbyPlaceList);
            Logger.d("at least have one site");
        }
        else {
            SharedPreferences preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
            preferences.edit().putInt("placeNumber", 0).apply();
            Logger.d("no site or googleMap place web service status error");
        }
    }

    // 根據不同類型將景點添加 googleMap Marker
    private void parseNearbyPlacesDetail(List<HashMap<String, String>> nearbyPlaceList) {
        ArrayList<String> place_id_List = new ArrayList<>();
        for(int i = 0; i < nearbyPlaceList.size(); i++) {
            HashMap<String, String> googlePlace = nearbyPlaceList.get(i);
            String place_id = googlePlace.get("place_id");
            place_id_List.add(getNearbyPlacesDetailUrl(place_id));
        }
        new GetNearbyPlacesDetail().execute(place_id_List);
        Logger.d();
    }

    private boolean checkExitNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList) {
        if(nearbyPlaceList.size() > 0) {
            Logger.d();
            return true;
        }
        else {
            Logger.d();
            return false;
        }
    }
    // 取得要傳去Google Place Web Service的網址
    private String getNearbyPlacesDetailUrl(String place_id) {
        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
        stringBuilder.append("placeid=" + place_id);
        stringBuilder.append("&key=" + "AIzaSyC63lWsqP6jcNoquzhzYPHRniIjgXhhV74");
        Logger.d(stringBuilder.toString());
        return stringBuilder.toString();
    }

    class GetNearbyPlacesDetail extends AsyncTask<ArrayList<String>, String, String[]> {

        @Override
        protected String[] doInBackground(ArrayList<String>[] arrayLists) {
            DownloadUrl downloadUrl = new DownloadUrl();
            googlePlacesDetail = new String[arrayLists[0].size()];
            try {
                for(int i = 0; i < googlePlacesDetail.length; i++) {
                    googlePlacesDetail[i] = downloadUrl.readUrl(arrayLists[0].get(i));
                }
                Logger.d("googlePlacesDetail" + googlePlacesDetail);
            } catch (Exception ex) {
                Logger.d(ex.getMessage());
            }

            return googlePlacesDetail;
        }

        @Override
        protected void onPostExecute(String[] s) {
            DataParser dataParser = new DataParser();
            for(int i = 0; i < s.length; i++) {
                nearbyPlaceListDetail.add(dataParser.parseNearbyPlacesDetail(s[i]));
            }
            if(checkExitNearbyPlaces(nearbyPlaceList)) {
                showNearbyPlaces(nearbyPlaceList);
                Logger.d("at least have one site");
            }
            else {
                Logger.d("no site or googleMap place web service status error");
            }
            // loading畫面停止
            AVLoadingIndicatorView loadingIndicatorView = ((Activity) context).findViewById(R.id.avi);
            loadingIndicatorView.hide();
        }
    }
    // 根據不同類型將景點添加 googleMap Marker
    private void showNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList) {
        for(int i = 0; i < nearbyPlaceListDetail.size(); i++) {
            Logger.d(nearbyPlaceListDetail.get(i).toString());
        }
        Collections.sort(nearbyPlaceListDetail, mapComparator);
        restaurant.clear();
        cafe.clear();
        theater.clear();
        park.clear();
        store.clear();
        for(int i = 0; i < nearbyPlaceListDetail.size(); i++) {
            for(int j = 0; j < nearbyPlaceList.size(); j++) {
                HashMap<String, String> googlePlace = nearbyPlaceList.get(j);
                HashMap<String, String> googlePlaceDetail = nearbyPlaceListDetail.get(i);
                if(googlePlace.get("place_name").equals(googlePlaceDetail.get("place_name"))) {
                    MarkerOptions markerOptions = new MarkerOptions();
                    Place place;

                    String placeName = googlePlace.get("place_name");
                    String vicinity = googlePlace.get("vicinity");
                    String place_id = googlePlace.get("place_id");
                    String rating = googlePlaceDetail.get("rating");
                    String phone = googlePlaceDetail.get("phone");

                    String open_now = googlePlaceDetail.get("openNow");

                    double lat = Double.parseDouble(googlePlace.get("lat"));
                    double lng = Double.parseDouble(googlePlace.get("lng"));

                    LatLng latLng = new LatLng(lat, lng);
                    markerOptions.position(latLng);
                    markerOptions.title(placeName);
                    if(open_now.equals("true")) {
                        open_now = "營業中";
                    }
                    else {
                        open_now = "休息中";
                    }
                    markerOptions.snippet("評價:" + rating + "\n" + "電話:" + phone + "\n" + "營業狀況:" + open_now);
                    loadPerferences();
                    switch (searchType) {
                        case "movie_theater":
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.theater_icon));
                            place = new Place(placeName, String.valueOf(lat), String.valueOf(lng));
                            theater.add(place);
                            break;
                        case "cafe":
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.cafe_icon));
                            place = new Place(placeName, String.valueOf(lat), String.valueOf(lng));
                            cafe.add(place);
                            break;
                        case "park":
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.park_icon));
                            place = new Place(placeName, String.valueOf(lat), String.valueOf(lng));
                            park.add(place);
                            break;
                        case "restaurant":
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.food_icon));
                            place = new Place(placeName, String.valueOf(lat), String.valueOf(lng));
                            restaurant.add(place);
                            break;
                        case "department_store":
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.store_icon));
                            place = new Place(placeName, String.valueOf(lat), String.valueOf(lng));
                            store.add(place);
                            break;
                    }
                    CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter((Activity)context);
                    map.setInfoWindowAdapter(adapter);
                    map.addMarker(markerOptions);
                    map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    map.animateCamera(CameraUpdateFactory.zoomTo(15.9f));
                    Logger.d(googlePlace.get("place_name"));
                }
            }
            if(i == 4) {
                break;
            }
        }
        for(int i = 0; i < nearbyPlaceListDetail.size(); i++) {
            Logger.d(nearbyPlaceListDetail.get(i).toString());
        }
        savePreferences();
    }

    public Comparator<Map<String, String>> mapComparator = new Comparator<Map<String, String>>() {
        public int compare(Map<String, String> m1, Map<String, String> m2) {
            return -m1.get("rating").compareTo(m2.get("rating"));
        }
    };

    // 存取使用者想要搜尋附近的哪一類型的景點
    private void loadPerferences() {
        SharedPreferences preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        searchType = preferences.getString("type", "");
        Logger.d(searchType);
    }

    private void savePreferences() {
        Gson gson = new Gson();
        Type listOfObjects = new TypeToken<List<Place>>(){}.getType();
        String strObject;
        SharedPreferences preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        switch (searchType) {
            case "restaurant":
                strObject = gson.toJson(restaurant, listOfObjects);
                preferences.edit().putString("restaurantList",  strObject).putInt("placeNumber", restaurant.size()).apply();
                break;
            case "movie_theater":
                strObject = gson.toJson(theater, listOfObjects);
                preferences.edit().putString("movieList",  strObject).putInt("placeNumber", theater.size()).apply();
                Logger.d("placeNumber" + theater.size());
                break;
            case "park":
                strObject = gson.toJson(park, listOfObjects);
                preferences.edit().putString("parkList",  strObject).putInt("placeNumber", park.size()).apply();
                break;
            case "department_store":
                strObject = gson.toJson(store, listOfObjects);
                preferences.edit().putString("storeList",  strObject).putInt("placeNumber", store.size()).apply();
                break;
            case "cafe":
                strObject = gson.toJson(cafe, listOfObjects);
                preferences.edit().putString("cafeList",  strObject).putInt("placeNumber", cafe.size()).apply();
                break;
        }
        Logger.d();
    }
}
