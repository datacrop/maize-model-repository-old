package eu.datacrop.maize.model_repository.commons.dtos.responses;

import lombok.Builder;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class LocationResponseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 418281017020031854L;

    /******************************************************************************************************************
     * Αn object representing a Geographical Location (a pair of  coordinates).
     *****************************************************************************************************************/
    private GeoLocationResponseDto geoLocation;

    /******************************************************************************************************************
     * A string representing a Virtual Location (a URL or the identifier of a resource/subsystem).
     *****************************************************************************************************************/
    private String virtualLocation;

    /******************************************************************************************************************
     * Constructor of the LocationResponseDto class.
     *****************************************************************************************************************/
    public LocationResponseDto(double latitude, double longitude, String virtualLocation) {

        this.virtualLocation = virtualLocation;

        if (!StringUtils.isBlank(virtualLocation)) {
            this.geoLocation = GeoLocationResponseDto.builder()
                    .latitude(0.0)
                    .longitude(0.0)
                    .build();
        } else {
            this.geoLocation = GeoLocationResponseDto.builder()
                    .latitude(latitude)
                    .longitude(longitude)
                    .build();
        }

    }

    /******************************************************************************************************************
     * Empty constructor of the LocationResponseDto class.
     *****************************************************************************************************************/
    public LocationResponseDto() {
        this.virtualLocation = "";
        this.geoLocation = GeoLocationResponseDto.builder()
                .latitude(0.0)
                .longitude(0.0)
                .build();
    }

    /******************************************************************************************************************
     * "Getter" method for "geoLocation" attribute.
     *
     * @return The current value of the object's "geoLocation" attribute.
     *****************************************************************************************************************/
    public GeoLocationResponseDto getGeoLocation() {
        return geoLocation;
    }

    /******************************************************************************************************************
     * "Setter" method for "geoLocation" attribute. If the Virtual Location is null, then the Geographical Location
     * must have a value and vice versa.
     *
     * @param latitude The latitude of the geographical location, not null if virtualLocation is null.
     * @param longitude The longitude of the geographical location, not null if virtualLocation is null.
     * @param virtualLocation A string representing a Virtual Location, not null if coordinates are null.
     *****************************************************************************************************************/
    public void setGeoLocation(double latitude, double longitude, String virtualLocation) {

        this.virtualLocation = virtualLocation;

        if (!StringUtils.isBlank(virtualLocation)) {
            this.geoLocation = GeoLocationResponseDto.builder()
                    .latitude(0.0)
                    .longitude(0.0)
                    .build();
        } else {
            this.geoLocation = GeoLocationResponseDto.builder()
                    .latitude(latitude)
                    .longitude(longitude)
                    .build();
        }

    }

    /******************************************************************************************************************
     * "Getter" method for "virtualLocation" attribute.
     *
     * @return The current value of the object's "virtualLocation" attribute.
     *****************************************************************************************************************/
    public String getVirtualLocation() {
        return virtualLocation;
    }

    /******************************************************************************************************************
     * "Setter" method for "virtualLocation" attribute.
     *
     * @param virtualLocation A value to assign to the object's "virtualLocation" attribute.
     *****************************************************************************************************************/
    public void setVirtualLocation(String virtualLocation) {
        this.virtualLocation = virtualLocation;
    }

    /******************************************************************************************************************
     * Method that checks whether two LocationResponseDto objects are equal.
     *
     * @param o The second Object two compare with the current Object.
     *****************************************************************************************************************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationResponseDto that = (LocationResponseDto) o;
        return Objects.equals(geoLocation, that.geoLocation) && Objects.equals(virtualLocation, that.virtualLocation);
    }

    /******************************************************************************************************************
     * Method that returns the integer hash code value of the LocationResponseDto object.
     *****************************************************************************************************************/
    @Override
    public int hashCode() {
        return Objects.hash(geoLocation, virtualLocation);
    }

    /******************************************************************************************************************
     * Transforms a LocationResponseDto object to String.
     *
     * @return A string representation of the Object.
     *****************************************************************************************************************/
    @Override
    public String toString() {
        return "{" +
                "geoLocation=" + geoLocation +
                ", virtualLocation='" + virtualLocation + '\'' +
                '}';
    }

    /**************************************************************************************************************
     * Transforms a LocationResponseDto object to JSONObject.
     *
     * @return A JSON representation of the Object.
     *************************************************************************************************************/
    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        jo.put("geoLocation", geoLocation.toJSON());
        jo.put("virtualLocation", virtualLocation);
        return jo;
    }

    /******************************************************************************************************************
     * This inner class is a data transfer object for Geographical Locations.
     *****************************************************************************************************************/
    @Builder
    public static class GeoLocationResponseDto implements Serializable {

        @Serial
        private static final long serialVersionUID = 4100314718361516719L;

        /**************************************************************************************************************
         * The latitude of the geographical location.
         *************************************************************************************************************/
        private double latitude;

        /**************************************************************************************************************
         * The longitude of the geographical location.
         *************************************************************************************************************/
        private double longitude;

        /**************************************************************************************************************
         * Constructor of the GeoLocationResponseDto class, both for Builder pattern and instantiation with "new".
         *
         * @param latitude  The latitude of the new Geographical Location.
         * @param longitude The longitude of the new Geographical Location.
         *************************************************************************************************************/
        private GeoLocationResponseDto(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        /**************************************************************************************************************
         * Empty constructor of the GeoLocationResponseDto class.
         *************************************************************************************************************/
        private GeoLocationResponseDto() {

        }

        /**************************************************************************************************************
         * "Getter" function for "latitude" attribute.
         *
         * @return The current value of the object's "latitude" attribute.
         *************************************************************************************************************/
        private double getLatitude() {
            return latitude;
        }

        /**************************************************************************************************************
         * "Setter" function for "latitude" attribute.
         *
         * @param latitude A value to assign to the object's "latitude" attribute.
         *************************************************************************************************************/
        private void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        /**************************************************************************************************************
         * "Getter" function for "longitude" attribute.
         *
         * @return The current value of the object's "longitude" attribute.
         *************************************************************************************************************/
        private double getLongitude() {
            return longitude;
        }

        /**************************************************************************************************************
         * "Setter" function for "longitude" attribute.
         *
         * @param longitude A value to assign to the object's "longitude" attribute.
         *************************************************************************************************************/
        private void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        /**************************************************************************************************************
         * Method that checks whether two GeoLocationResponseDto objects are equal.
         *
         * @param o The second Object two compare with the current Object.
         *************************************************************************************************************/
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoLocationResponseDto that = (GeoLocationResponseDto) o;
            return Double.compare(that.latitude, latitude) == 0 && Double.compare(that.longitude, longitude) == 0;
        }

        /**************************************************************************************************************
         * Method that returns the integer hash code value of the GeoLocationResponseDto object.
         *************************************************************************************************************/
        @Override
        public int hashCode() {
            return Objects.hash(latitude, longitude);
        }

        /**************************************************************************************************************
         * Transforms a GeoLocationResponseDto object to String.
         *
         * @return A string representation of the Object.
         *************************************************************************************************************/
        @Override
        public String toString() {
            return "{" +
                    "latitude=" + latitude +
                    ", longitude=" + longitude +
                    '}';
        }

        /**************************************************************************************************************
         * Transforms a GeoLocationResponseDto object to JSONObject.
         *
         * @return A JSON representation of the Object.
         *************************************************************************************************************/
        public JSONObject toJSON() {
            JSONObject jo = new JSONObject();
            jo.put("latitude", latitude);
            jo.put("longitude", longitude);
            return jo;
        }
    }

}