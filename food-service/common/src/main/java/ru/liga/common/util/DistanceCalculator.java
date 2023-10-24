package ru.liga.common.util;


public class DistanceCalculator {

    private static final double EARTH_RADIUS = 6371;

    public static double calculateDistance(String coordinate1, String coordinate2 ) {

        double lat1 = Double.parseDouble(
                coordinate1.substring(0,coordinate1.indexOf(',')));
        double lon1 = Double.parseDouble(
                coordinate1.substring(coordinate1.indexOf(',')+1));

        double lat2 = Double.parseDouble(
                coordinate2.substring(0,coordinate1.indexOf(',')));
        double lon2 = Double.parseDouble(
                coordinate2.substring(coordinate1.indexOf(',')+1));


        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.pow(Math.sin(dLon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }
}

