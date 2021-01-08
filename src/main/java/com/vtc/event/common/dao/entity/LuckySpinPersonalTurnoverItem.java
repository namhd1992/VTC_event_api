package com.vtc.event.common.dao.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;


/**
 * The persistent class for the tblLuckySpinPersonalTurnoverItem database table.
 * 
 */
@Entity
@Table(name="tblLuckySpinPersonalTurnoverItem")
@Getter
@Setter
@NoArgsConstructor
public class LuckySpinPersonalTurnoverItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int                      id;

    private Timestamp                createOn;

    private Timestamp                updateOn;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "luckySpin")
    private LuckySpin                luckySpin;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spinItem")
    private LuckySpinItemOfLuckySpin spinItem;

    private int                      personalTopupLandmark;

    private int                      limitPerUser;

    private boolean                  isMainItem;

}