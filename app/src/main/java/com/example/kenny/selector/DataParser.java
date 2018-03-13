package com.example.kenny.selector;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {
    // 將googlePlace Web Service 回傳過來Json資料拆解
    // 取出name、vicinity、latitude、longtitude、place_id
    private HashMap<String, String> getPlace(JSONObject googlePlaceJson) {
        HashMap<String, String> googlePlaceMap = new HashMap<>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String place_id = "";
        Logger.d(googlePlaceJson.toString());
        try {
            if(!googlePlaceJson.isNull("name")) {
                placeName = googlePlaceJson.getString("name");
            }
            if(!googlePlaceJson.isNull("vicinity")) {
                vicinity = googlePlaceJson.getString("vicinity");
            }
            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
            place_id = googlePlaceJson.getString("place_id");

            googlePlaceMap.put("place_name", placeName);
            googlePlaceMap.put("vicinity", vicinity);
            googlePlaceMap.put("lat", latitude);
            googlePlaceMap.put("lng", longitude);
            googlePlaceMap.put("place_id", place_id);

            Logger.d("place_name:" + placeName + "\n" + "vincinity:" + vicinity
                    + "\n" + "lat:" + latitude + "\n" + "lng:" + longitude + "\n" + "place_id:" + place_id);
        } catch (Exception ex) {
            Logger.d(ex.getMessage());
        }
        return googlePlaceMap;
    }
    // 將每個景點的HashMap收集為一連串List
    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray) {
        int count = jsonArray.length();
        List<HashMap<String, String>> placesList = new ArrayList<>();
        HashMap<String, String> placeMap = null;

        for(int i = 0; i < count; i++) {
            try {
                placeMap = getPlace((JSONObject)jsonArray.get(i));
                placesList.add(placeMap);
                Logger.d();
            } catch (Exception ex) {
                Logger.d(ex.getMessage());
            }
        }
        return placesList;
    }
    // 取得Google Place Web Service的NearbyPlaces的JsonData
    // 擷取"results"的JsonArray
    public List<HashMap<String, String>> parseNearbyPlaces(String jsonData) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(jsonData);
            if(jsonObject.get("status").equals("OK")) {
                Logger.d("已順利偵測到地點並至少傳回一個結果");
                jsonArray = jsonObject.getJSONArray("results");
                Logger.d(jsonArray.toString());
            }
            else {
                Logger.d(jsonArray.toString());
            }
        } catch (Exception ex) {
            Logger.d(ex.getMessage());
        }

        return getPlaces(jsonArray);
    }

    // 取得Google Place Web Service的NearbyPlaces的每一個Detail的JsonData
    // 擷取"result"的JsonArray
    public HashMap<String, String> parseNearbyPlacesDetail(String jsonData) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject = new JSONObject(jsonData);
            if(jsonObject.get("status").equals("OK")) {
                Logger.d("已順利偵測到地點並至少傳回一個結果");
                jsonObject = jsonObject.getJSONObject("result");
                Logger.d(jsonArray.toString());
            }
            else {
                Logger.d(jsonArray.toString());
            }
        } catch (Exception ex) {
            Logger.d(ex.getMessage());
        }
        return getPlacesDetailsList(jsonObject);
    }

    // 將每個景點的HashMap收集為一連串List
    private HashMap<String, String> getPlacesDetailsList(JSONObject jsonObject) {

        HashMap<String, String> placeMap = null;


            try {
                placeMap = getPlaceDetail(jsonObject);
                Logger.d();
            } catch (Exception ex) {
                Logger.d(ex.getMessage());
            }

        return placeMap;
    }

    private HashMap<String, String> getPlaceDetail(JSONObject googlePlaceJson) {
        HashMap<String, String> googlePlaceMap = new HashMap<>();
        String name = "無資料";
        String place_id = "";
        String rating = "0";
        String openNow = "無資料";
        String phone = "無資料";

        Logger.d(googlePlaceJson.toString());
        try {
            if(!googlePlaceJson.isNull("name")) {
                name = googlePlaceJson.getString("name");
            }
            place_id = googlePlaceJson.getString("place_id");
            if(!googlePlaceJson.isNull("rating")) {
                rating = googlePlaceJson.getString("rating");
            }
            if(!googlePlaceJson.isNull("formatted_phone_number")) {
                phone = googlePlaceJson.getString("formatted_phone_number");
            }
            if(!googlePlaceJson.isNull("opening_hours")) {
                if(!googlePlaceJson.getJSONObject("opening_hours").getString("open_now").isEmpty()) {
                    openNow = googlePlaceJson.getJSONObject("opening_hours").getString("open_now");
                }
            }
            googlePlaceMap.put("place_name", name);
            googlePlaceMap.put("place_id", place_id);
            googlePlaceMap.put("rating", rating);
            googlePlaceMap.put("openNow", openNow);
            googlePlaceMap.put("phone", phone);

            Logger.d("place_name:" + name + "\n" + "place_id:" + place_id
                    + "\n" + "rating:" + rating + "\n" + "openNow:" + openNow + "\n" + "phone:" + phone);
        } catch (Exception ex) {
            Logger.d(ex.getMessage());
        }
        return googlePlaceMap;
    }
}
