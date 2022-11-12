package eu.datacrop.maize.model_repository.mongodb.repositories;

import eu.datacrop.maize.model_repository.mongodb.model.entities.AssetCategory;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**********************************************************************************************************************
 * This class is the bridge between MongoDb and the Spring Framework application as far as Asset Category transactions
 * are concerned.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.4.0
 *********************************************************************************************************************/
@Repository
@Profile("devmongo")
public interface AssetCategoryRepository extends MongoRepository<AssetCategory, String> {

    /*****************************************************************************************************************
     * This method retrieves an Asset Category given its human-readable name as identifier. Business-wise the name is
     * unique but the attribute is not technically unique. Therefore, only the first of multiple entities will
     * be returned as a protection measure against database failures.
     *
     * @param  name A human-readable string that uniquely identifies a persisted Asset Category, not null.
     * @return The retrieved AssetCategory entity.
     ****************************************************************************************************************/
    AssetCategory findFirstByName(String name);

    /*****************************************************************************************************************
     * This method retrieves all Asset Categories ever persisted on the database. Employs pagination.
     *
     * @param  pageable Information on the pagination configurations, not null.
     * @return The retrieved collection of AssetCategory entities paginated.
     ****************************************************************************************************************/
    Page<AssetCategory> findAll(Pageable pageable);

}


