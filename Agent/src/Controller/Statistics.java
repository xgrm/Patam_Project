package Controller;

import java.util.ArrayList;

public class Statistics {

public static float sumDistanceInMiles(ArrayList<Float> longitude_deg, ArrayList<Float>latitude_deg){
    float sumInMiles = 0;
    for (int i = 0; i <latitude_deg.size()-1 ; i++) {
        sumInMiles+= Statistics.distance(longitude_deg.get(i),latitude_deg.get(i),longitude_deg.get(i+1),latitude_deg.get(i+1));
    }
    return sumInMiles;
}
    private static Double distance(float loc1Longitude,float loc1Latitude,float loc2Longitude, float loc2Latitude) {

        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        double lon1 = loc1Longitude;
        double lon2 = loc2Longitude;
        double lat1 = loc1Latitude;
        double lat2 = loc2Latitude;
        final int R = 3956; // 3956 Radius of the earth in miles, for km use 6371

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c ;
        double height = 0 - 0;
        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);

    }
}
