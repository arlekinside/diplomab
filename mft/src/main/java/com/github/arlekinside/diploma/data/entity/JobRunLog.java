package com.github.arlekinside.diploma.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(indexes = @Index(columnList = "date_created"))
@Data
public class JobRunLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean success;

    private int processedNum;

    @Embedded
    private TimeData timeData;

}
