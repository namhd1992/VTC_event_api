package com.vtc.event.common.dao.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by phucnguyen on 23/06/2017.
 */
@Entity
@Table(name = "tblLuckySpinUser")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LuckySpinUser {
    @Id
    @GeneratedValue
    private Long      id;

    @CreationTimestamp
    private Date      createOn;

    @UpdateTimestamp
    private Date      updateOn;

    @ManyToOne
    @JoinColumn(name = "luckySpin")
    private LuckySpin luckySpin;

    @ManyToOne
    @JoinColumn(name = "userInfo")
    private UserInfo  userInfo;

    private int       turnsBuy;

    private int       turnsFree;

    private long      personalTopup;
    
    private long      balance;
    
    private long      accumulationPoint;

    public LuckySpinUser(final UserInfo userInfo, final LuckySpin luckySpin) {
        this.userInfo = userInfo;
        this.luckySpin = luckySpin;
    }

}
