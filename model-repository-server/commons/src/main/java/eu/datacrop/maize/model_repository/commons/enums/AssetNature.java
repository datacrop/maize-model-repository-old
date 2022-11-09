package eu.datacrop.maize.model_repository.commons.enums;

/**********************************************************************************************************************
 * This enumeration contains classifications of Assets (Asset Nature options).
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
public enum AssetNature {

    /******************************************************************************************************************
     * Indicates that an Asset exists in the physical world and is a device connected to the Internet.
     *****************************************************************************************************************/
    TANGIBLE_PHYSICAL("TANGIBLE_PHYSICAL"),

    /******************************************************************************************************************
     * Indicates that an Asset exists in the physical world and fuses computation, networking and physical processes.
     *****************************************************************************************************************/
    TANGIBLE_CYBER_PHYSICAL("TANGIBLE_CYBER_PHYSICAL"),

    /******************************************************************************************************************
     * Indicates that an Asset is not represented in the physical world; instead it might be a web resource or
     * a remote (micro)service.
     *****************************************************************************************************************/
    INTANGIBLE("INTANGIBLE");

    /******************************************************************************************************************
     * The text representing the AssetNature enumeration values.
     *****************************************************************************************************************/
    private final String text;

    /******************************************************************************************************************
     * Constructor of the AssetNature enumeration.
     *****************************************************************************************************************/
    AssetNature(String text) {
        this.text = text;
    }

    /******************************************************************************************************************
     * Transforms an AssetNature enumeration object to String.
     *
     *  @return A string representation of the Object.
     *****************************************************************************************************************/
    @Override
    public String toString() {
        return text;
    }
}
