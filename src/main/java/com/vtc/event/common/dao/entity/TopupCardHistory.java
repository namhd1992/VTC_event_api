/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dao.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Jul 16, 2019
 */
@Entity
@Table(name = "tblTopupCardHistory")
@Setter
@Getter
@NoArgsConstructor
public class TopupCardHistory {
  
    @Id
    @GeneratedValue
    private Long     id;

    @CreationTimestamp
    private Date     createOn;

    private Long     scoinId;

    @ManyToOne
    @JoinColumn(name = "userInfo")
    private UserInfo userInfo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date     paymentTime;

    private String   paymentType;

    private long     totalPayment;

    private long     billingTransId;

    private boolean  luckyWheelUsed;

}
