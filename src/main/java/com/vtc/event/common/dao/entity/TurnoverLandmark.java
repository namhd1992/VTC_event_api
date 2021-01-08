package com.vtc.event.common.dao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Jul 15, 2019
 */
@Entity
@Table(name = "tblTurnoverLandmark")
@NamedQuery(name = "TurnoverLandmark.findAll", query = "SELECT t FROM TurnoverLandmark t")
@Setter
@Getter
@NoArgsConstructor
public class TurnoverLandmark implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long              id;

    @CreationTimestamp
    private Date              createOn;

    @UpdateTimestamp
    private Date              updateOn;

    private String            game;

    private Long              gameId;

    private Long              itemId;

    private int               limitQuantity;

    private String            limitType;

    private long              turnoverLandmark;

}