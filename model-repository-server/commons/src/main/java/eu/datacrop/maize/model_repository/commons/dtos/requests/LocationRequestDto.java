package eu.datacrop.maize.model_repository.commons.dtos.requests;

import eu.datacrop.maize.model_repository.commons.dtos.requests.templates.RequestDto;
import eu.datacrop.maize.model_repository.commons.enums.ResponseCode;
import eu.datacrop.maize.model_repository.commons.error.messages.LocationErrorMessages;
import eu.datacrop.maize.model_repository.commons.wrappers.single.LocationResponseWrapper;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**********************************************************************************************************************
 * This class is a data transfer object representing Locations (Geographical or Virtual ones). Used in HTTP requests.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
@Slf4j
public class LocationRequestDto extends RequestDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -5500314095045018717L;


    /******************************************************************************************************************
     * Αn object representing a Geographical Location (a pair of  coordinates).
     *****************************************************************************************************************/
    private GeoLocationRequestDto geoLocation;

    /******************************************************************************************************************
     * A string representing a Virtual Location (a URL or the identifier of a resource/subsystem).
     *****************************************************************************************************************/
    private String virtualLocation;

    /******************************************************************************************************************
     * Constructor of the LocationRequestDto class, both for Builder pattern and instantiation with "new".
     *****************************************************************************************************************/
    public LocationRequestDto(double latitude, double longitude, String virtualLocation) {
        this.geoLocation = GeoLocationRequestDto.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();
        this.virtualLocation = virtualLocation;
    }

    /******************************************************************************************************************
     * Empty constructor of the LocationRequestDto class.
     *****************************************************************************************************************/
    public LocationRequestDto() {
        this.virtualLocation = "";
        this.geoLocation = GeoLocationRequestDto.builder()
                .latitude(0.0)
                .longitude(0.0)
                .build();
    }

    /******************************************************************************************************************
     * "Getter" method for "geoLocation" attribute.
     *
     * @return The current value of the object's "geoLocation" attribute.
     *****************************************************************************************************************/
    public GeoLocationRequestDto getGeoLocation() {
        return geoLocation;
    }

    /**************************************************************************************************************
     * "Getter" function for "latitude" attribute.
     *
     * @return The current value of the object's "latitude" attribute.
     *************************************************************************************************************/
    public double getLatitude() {
        return geoLocation.getLatitude();
    }


    /**************************************************************************************************************
     * "Getter" function for "longitude" attribute.
     *
     * @return The current value of the object's "longitude" attribute.
     *************************************************************************************************************/
    public double getLongitude() {
        return geoLocation.getLongitude();
    }

    /******************************************************************************************************************
     * "Setter" method for "geoLocation" attribute. If the Virtual Location is null, then the Geographical Location
     * must have a value and vice versa.
     *
     * @param latitude The latitude of the Geographical Location, not null if virtualLocation is null.
     * @param longitude The longitude of the Geographical Location, not null if virtualLocation is null.
     * @param virtualLocation A string representing a Virtual Location, not null if coordinates are null.
     *****************************************************************************************************************/
    public void setGeoLocation(double latitude, double longitude, String virtualLocation) {
        this.geoLocation = GeoLocationRequestDto.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();
        this.virtualLocation = virtualLocation;
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
     * @param virtualLocation A value to assign to the object's "virtualLocation" attribute, not null.
     *****************************************************************************************************************/
    public void setVirtualLocation(String virtualLocation) {
        this.virtualLocation = virtualLocation;
    }

    /******************************************************************************************************************
     * Method that checks whether two LocationRequestDto objects are equal.
     *
     * @param o The second Object to compare with the current Object, not null.
     *****************************************************************************************************************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationRequestDto that = (LocationRequestDto) o;
        return Objects.equals(geoLocation, that.geoLocation) && Objects.equals(virtualLocation, that.virtualLocation);
    }

    /******************************************************************************************************************
     * Method that returns the integer hash code value of the LocationRequestDto object.
     *****************************************************************************************************************/
    @Override
    public int hashCode() {
        return Objects.hash(geoLocation, virtualLocation);
    }

    /******************************************************************************************************************
     * Transforms a LocationRequestDto object to String.
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
     * Transforms a LocationRequestDto object to JSONObject.
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
     * This method triggers validation of the data transfer object's attributes and external relationships.
     *
     * @return A SystemResponseWrapper (the user will receive a more elaborate one, here it is used only for
     * internal intra-module communication).
     *****************************************************************************************************************/
    @Override
    public LocationResponseWrapper performValidation() {
        LocationResponseWrapper wrapper;
        try {
            // Validating attributes.
            wrapper = (LocationResponseWrapper) super.getValidator().validateAttributes(this);

            // If we already have an error there is no point in checking further.
            if (wrapper == null || !wrapper.getCode().equals(ResponseCode.SUCCESS)) {
                log.debug("Issues discovered during attribute validation.");
                return wrapper;
            }

            // Validating relationships (if applicable).
            wrapper = (LocationResponseWrapper) super.getValidator().validateRelationships(this);

            // If an error has been discovered report it and return.
            if (wrapper == null || !wrapper.getCode().equals(ResponseCode.SUCCESS)) {
                log.debug("Issues discovered during attribute validation.");
                return wrapper;
            }

            // Reporting that the validation discovered no issues.
            log.debug("Validation of the Request DTO has no issues to report.");
            return wrapper;
        } catch (IllegalArgumentException e) {
            String message = "Error occurred during Request DTO validation.";
            log.error(message);
            return new LocationResponseWrapper(ResponseCode.ERROR, message, null, LocationErrorMessages.INTERNAL_SERVER_ERROR);
        }
    }

    /******************************************************************************************************************
     * This inner class is a data transfer object for Geographical Locations.
     *
     * @author Angela-Maria Despotopoulou [Athens, Greece]
     * @since version 0.3.0
     *****************************************************************************************************************/
    @Builder
    public static class GeoLocationRequestDto implements Serializable {

        @Serial
        private static final long serialVersionUID = -7873833700409892138L;


        /**************************************************************************************************************
         * The latitude of the Geographical Location.
         *************************************************************************************************************/
        private double latitude;

        /**************************************************************************************************************
         * The longitude of the Geographical Location.
         *************************************************************************************************************/
        private double longitude;

        /**************************************************************************************************************
         * Constructor of the GeoLocationRequestDto class, both for Builder pattern and instantiation with "new".
         *
         * @param latitude  The latitude of the new Geographical Location, not null.
         * @param longitude The longitude of the new Geographical Location, not null.
         *************************************************************************************************************/
        private GeoLocationRequestDto(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        /**************************************************************************************************************
         * Empty constructor of the GeoLocationRequestDto class.
         *************************************************************************************************************/
        private GeoLocationRequestDto() {
            this(0.0, 0.0);
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
         * @param latitude A value to assign to the object's "latitude" attribute, not null.
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
         * @param longitude A value to assign to the object's "longitude" attribute, not null.
         *************************************************************************************************************/
        private void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        /**************************************************************************************************************
         * Method that checks whether two GeoLocationRequestDto objects are equal.
         *
         * @param o The second Object to compare with the current Object, not null.
         *************************************************************************************************************/
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoLocationRequestDto that = (GeoLocationRequestDto) o;
            return Double.compare(that.latitude, latitude) == 0 && Double.compare(that.longitude, longitude) == 0;
        }

        /**************************************************************************************************************
         * Method that returns the integer hash code value of the GeoLocationRequestDto object.
         *************************************************************************************************************/
        @Override
        public int hashCode() {
            return Objects.hash(latitude, longitude);
        }

        /**************************************************************************************************************
         * Transforms a GeoLocationRequestDto object to String.
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
         * Transforms a GeoLocationRequestDto object to JSONObject.
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
