package com.sn.textile.core.base.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;
/**
 * @Project core-service
 * @author Md. Nayeemul Islam
 * @Since Feb 07, 2023
 */

@Component
@MappedSuperclass
@Data
public class BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(nullable = false, updatable = false)
    private UUID createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    @CreatedDate
    @JsonIgnore
    private Date createdOn;

    private UUID updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @JsonIgnore
    private Date updatedOn;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private Boolean isDeleted;

}