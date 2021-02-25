package com.vtc.event.common.dao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the tblLuckySpinRadiusPersonalTopup database table.
 * 
 */
@Entity
@Table(name="tblLuckySpinRadiusPersonalTopup")
@Getter
@Setter
@NoArgsConstructor
public class LuckySpinRadiusPersonalTopup implements Serializable {

    private static final long        serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long                     id;

    @CreationTimestamp
    private Date                     createOn;

    @UpdateTimestamp
    private Date                     updateOn;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "luckySpin")
    private LuckySpin                luckySpin;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spinItem")
    private LuckySpinItemOfLuckySpin spinItem;

    private long                     personalTopupLandmark;

    private int                      ratioIndex;

    private float                    ratio;

    private boolean                  isMainItem;

}