package com.vtc.event.common.dao.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by phucnguyen on 26/06/2017.
 */
@Entity
@Table(name = "tblLuckySpinHistory")
@Setter
@Getter
@NoArgsConstructor
public class LuckySpinHistory {
    @Id
    @GeneratedValue
    private int           id;

    @CreationTimestamp
    private Date          createOn;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userInfo")
    private UserInfo      userInfo;

    private String        userName;

    private String        phoneNumber;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "luckySpin")
    private LuckySpin     luckySpin;

    @ManyToOne
    @JoinColumn(name = "item")
    private LuckySpinItem item;

    @ManyToOne
    @JoinColumn(name = "gift")
    private LuckySpinGift gift;
    
    private String        description;

    private String        status;

    private long          value;

    private String        message;

    private String        turnType;

    @Transient
    private int           itemId;

    @Transient
    private String        itemName;

    @Transient
    private String        defaultImage;

    @Transient
    private String        luckyspinName;

}
