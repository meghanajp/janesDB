package com.example.janesdb.repository;
import com.example.janesdb.entity.JanesDBEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface JanesDBRepository extends JpaRepository<JanesDBEntity, UUID>, JpaSpecificationExecutor<JanesDBEntity> {

//    @Query(value="SELECT * FROM janes_db_info jd WHERE jd.ship_length BETWEEN :length*0.95 AND :length*1.05")
//    JanesDBEntity findByLength(@Param("length") BigDecimal length);

    @Query("SELECT jd FROM JanesDBEntity jd WHERE jd.shipLength BETWEEN :minLength AND :maxLength")
    List<JanesDBEntity> findByLengthRange(@Param("minLength") BigDecimal minLength, @Param("maxLength") BigDecimal maxLength);


}
