package com.github.arlekinside.diploma.data.entity;

import com.github.arlekinside.diploma.data.enums.SchedulerType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Entity
@Table(indexes = @Index(columnList = "date_created"))
@Data
public class SchedulerLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean success;

    @Enumerated(EnumType.STRING)
    private SchedulerType type;

    private int processedNum;

    @Embedded
    private TimeData timeData;

    @Transient
    private String dateCreated;

    @PostLoad
    private void postLoad() {
        this.dateCreated = timeData.getDateCreated().format(DateTimeFormatter.ofPattern("MMM dd"));
    }

}
