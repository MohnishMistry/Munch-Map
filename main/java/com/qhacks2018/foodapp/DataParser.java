package com.qhacks2018.foodapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mohni on 2/3/2018.
 */

public class DataParser
{
    private HashMap<String, String> getPlace(JSONObject googlePlaceJson)
    {
        HashMap<String, String> googlePlacesMap = new HashMap<>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";
        String placeID = "";


            try
            {
                if(!googlePlaceJson.isNull("name"))
                {
                    placeName = googlePlaceJson.getString("name");
                }

                if(!googlePlaceJson.isNull("vicinity"))
                {
                    vicinity = googlePlaceJson.getString("vicinity");

                }

                longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
                latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");

                reference = googlePlaceJson.getString("reference");
                placeID = googlePlaceJson.getString("place_id");

                googlePlacesMap.put("place_name", placeName);
                googlePlacesMap.put("vicinity", vicinity);
                googlePlacesMap.put("lng", longitude);
                googlePlacesMap.put("lat", latitude);
                googlePlacesMap.put("reference", reference);
                googlePlacesMap.put("place_id", placeID);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return googlePlacesMap;
    }

    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray)
    {
        int count = jsonArray.length();
        List<HashMap<String, String>> placesList = new ArrayList<>();
        HashMap<String, String> placeMap = null;

        for (int i = 0; i< count; i++)
        {
            try {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placesList.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return placesList;
    }

    public List<HashMap<String, String>> parse(String jsonData)
    {
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }
}
