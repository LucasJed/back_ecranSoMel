package com.livetree.ecransomel.back.Reporitory;

import com.livetree.ecransomel.back.Entities.TrendTable_Jour;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TrendTable_JourRepository extends CrudRepository<TrendTable_Jour,Long> {

    /**
     * JPA
     * @return liste de la base de donnée
     */
    ArrayList<TrendTable_Jour> findAll();

    /**
     *
     * @param chrono Chrono début de la liste
     * @param chrono2 Chrono fin de la liste de date
     * @param buildingName
     * @return
     */
	TrendTable_Jour findFirstByChronoBetweenAndName(long chrono, long chrono2,String buildingName);
	
    /**
    *
    * @param chrono Chrono fin de la liste
    * @param chrono2 Chrono début de la liste de date
    * @param buildingName
    * @return
    */
	TrendTable_Jour findFirstByChronoBetweenAndNameOrderByChronoDesc(long chrono, long chrono2,String buildingName);

}
