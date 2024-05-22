package com.github.arlekinside.diploma.data.repo;

import com.github.arlekinside.diploma.data.entity.SchedulerLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SchedulerLogRepo extends JpaRepository<SchedulerLog, Long> {

    @Query("select log from SchedulerLog log where log.timeData.dateCreated >= :dateTime order by log.timeData.dateCreated desc")
    List<SchedulerLog> findAllAfterDate(@Param("dateTime") LocalDateTime dateTime);
}
