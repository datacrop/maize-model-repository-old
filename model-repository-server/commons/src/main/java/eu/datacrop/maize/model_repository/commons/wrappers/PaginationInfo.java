package eu.datacrop.maize.model_repository.commons.wrappers;

import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;

/**********************************************************************************************************************
 * This class is auxiliary and contains pagination information to accompany lists returned in ResponseWrappers.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
@Builder
public class PaginationInfo implements Serializable {

    /******************************************************************************************************************
     * A unique identifier for the PaginationInfo class.
     *****************************************************************************************************************/
    @Serial
    private static final long serialVersionUID = -8672136538769317008L;

    /******************************************************************************************************************
     * Total of entities found in the database.
     *****************************************************************************************************************/
    private long totalItems;

    /******************************************************************************************************************
     * Total of pages that can be retrieved from the database.
     *****************************************************************************************************************/
    private int totalPages;

    /******************************************************************************************************************
     * The index of the current Page returned from the database.
     *****************************************************************************************************************/
    private int currentPage;

    /******************************************************************************************************************
     * Constructor of the PaginationInfo class.
     *
     * @param totalItems Total of entities found in the database.
     * @param totalPages Total of pages that can be retrieved from the database.
     * @param currentPage The index of the current Page returned from the database.
     *****************************************************************************************************************/
    public PaginationInfo(long totalItems, int totalPages, int currentPage) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    /******************************************************************************************************************
     * Empty constructor of the PaginationInfo class.
     *
     *****************************************************************************************************************/
    public PaginationInfo() {
        this(0, 0, 0);
    }

    /******************************************************************************************************************
     * "Getter" method for "totalItems" attribute.
     *
     * @return The current value of the object's "totalItems" attribute.
     *****************************************************************************************************************/
    public long getTotalItems() {
        return totalItems;
    }

    /******************************************************************************************************************
     * "Setter" function for "totalItems" attribute.
     *
     * @param totalItems A value to assign to the object's "totalItems" attribute, not null.
     *****************************************************************************************************************/
    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    /******************************************************************************************************************
     * "Getter" method for "totalPages" attribute.
     *
     * @return The current value of the object's "totalPages" attribute.
     *****************************************************************************************************************/
    public int getTotalPages() {
        return totalPages;
    }

    /******************************************************************************************************************
     * "Setter" function for "totalPages" attribute.
     *
     * @param totalPages A value to assign to the object's "totalPages" attribute, not null.
     *****************************************************************************************************************/
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /******************************************************************************************************************
     * "Getter" method for "currentPage" attribute.
     *
     * @return The current value of the object's "currentPage" attribute.
     *****************************************************************************************************************/
    public int getCurrentPage() {
        return currentPage;
    }

    /******************************************************************************************************************
     * "Setter" function for "currentPage" attribute.
     *
     * @param currentPage A value to assign to the object's "currentPage" attribute, not null.
     *****************************************************************************************************************/
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /******************************************************************************************************************
     * Transforms a PaginationInfo object to String.
     *
     * @return A string representation of the Object.
     *****************************************************************************************************************/
    @Override
    public String toString() {
        return "{" +
                "totalItems=" + totalItems +
                ", totalPages=" + totalPages +
                ", currentPage=" + currentPage +
                '}';
    }
}
