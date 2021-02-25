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
@Table(name = "tblUserTurnSpin")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserTurnSpin {
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

    private long      turnsFree;

    private long      personalTopup;
    
    private long      balance;

    public UserTurnSpin(UserInfo userInfo, LuckySpin luckySpin) {
        this.userInfo = userInfo;
        this.luckySpin = luckySpin;
    }

}
