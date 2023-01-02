package com.example.zero_study_mid.api;

public class GetDistance {
    public static void main(String[] args) {
//        GetDistance.37.5026529,126.7635544;
        GetDistance getDistance = new GetDistance();
        double distance;
        double ndb = Math.pow(10.0, 4);
//        distance = getDistance.distance(37.502982, 127.041564, "37.504158", "127.04021");
//        System.out.println(distance);
    }

    public double distance (double lat1, double lnt1, String LAT, String LNT){
        double ndb = Math.pow(10.0, 4);
        double lat2 = Double.parseDouble(LAT);
        double lnt2 = Double.parseDouble(LNT);
        double theta = lnt1 - lnt2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) +
                Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        dist = Math.round(dist * ndb);
        dist = dist / ndb;
        return dist;
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }


}