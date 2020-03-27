package com.livetree.ecransomel.back.Reporitory;

import com.livetree.ecransomel.back.Entities.TrendTable_Jour;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TrendTable_JourRepository extends CrudRepository<TrendTable_Jour,Long> {

    ArrayList<TrendTable_Jour> findAll();
    ArrayList<TrendTable_Jour> findAllByChronoBetweenOrderByChrono(long chrono1, long chrono2);

    TrendTable_Jour findFirstByChronoBetweenAndName(long chrono, long chrono2,String buildingName);
    TrendTable_Jour findFirstByChronoBetween(long chrono, long chrono2);
}
