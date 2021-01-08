package com.vtc.event.common.dao.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * May 28, 2019
 */
@Entity
@Table(name = "giftcode")
@Setter
@Getter
@NoArgsConstructor
public class Giftcode {
    @Id
    @GeneratedValue
    private Long     id;
    
    @CreationTimestamp
    private Date     createOn;

    private String   mainCode;

    private String   subCode;

    private String   eventType;

    private Long     mainEventId;

    private Long     subEventId;

    @ManyToOne
    @JoinColumn(name = "userInfo")
    private UserInfo userInfo;

}
