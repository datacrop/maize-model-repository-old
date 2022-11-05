package eu.datacrop.maize.model_repository.mongodb.repositories;

import eu.datacrop.maize.model_repository.mongodb.model.System;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**********************************************************************************************************************
 * This class is the bridge between MongoDb and the Spring Framework application as far as System transactions
 * are concerned.
 *
 * @author Angela-Maria Despotopoulou [Athens, Greece]
 * @since version 0.3.0
 *********************************************************************************************************************/
@Repository
@Profile("devmongo")
public interface SystemRepository extends MongoRepository<System, String> {

    /*****************************************************************************************************************
     * This method retrieves a System given its human-readable name as identifier. Business-wise the name is
     * unique but the attribute is not technically unique. Therefore, only the first of multiple entities will
     * be returned as a protection measure against database failures.
     *
     * @param  name A human-readable string that uniquely identifies a persisted System, not null.
     * @return The retrieved System entity.
     ****************************************************************************************************************/
    System findFirstByName(String name);

    /*****************************************************************************************************************
     * This method retrieves all Systems ever persisted on the database. Employs pagination.
     *
     * @param  pageable Information on the pagination configurations, not null.
     * @return The retrieved collection of System entities paginated.
     ****************************************************************************************************************/
    Page<System> findAll(Pageable pageable);

}


