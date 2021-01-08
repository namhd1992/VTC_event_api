package com.vtc.event.common.dao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * May 28, 2019
 */
@Entity
@Table(name="tblLuckySpinBuyTurnHistory")
@Setter
@Getter
@NoArgsConstructor
public class LuckySpinBuyTurnHistory implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long              id;

    @CreationTimestamp
    private Date              createOn;

    @UpdateTimestamp
    private Date              updateOn;

    @ManyToOne
    @JoinColumn(name = "userInfo")
    private UserInfo          userInfo;

    @ManyToOne
    @JoinColumn(name = "luckySpin")
    private LuckySpin         luckySpin;

    private String            buyType;

    private long              buyValue;

    private int               spinTurn;

    private int               buyQuantity;

    private String            status;

}