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

    @Serial
    private static final long serialVersionUID = -8672136538769317008L;

    /******************************************************************************************************************
     * Total of entities found in the database.
     *****************************************************************************************************************/
    private int totalItems;

    /******************************************************************************************************************
     * Total of pages that can be retrieved from the database.
     *****************************************************************************************************************/
    private int totalPages;

    /******************************************************************************************************************
     * The index of the current Page returned from the database.
     *****************************************************************************************************************/
    private int currentPage;

    public PaginationInfo(int totalItems, int totalPages, int currentPage) {
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    public PaginationInfo() {
        this(0, 0, 0);
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public String toString() {
        return "{" +
                "totalItems=" + totalItems +
                ", totalPages=" + totalPages +
                ", currentPage=" + currentPage +
                '}';
    }
}
