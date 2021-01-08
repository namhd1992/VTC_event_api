/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dao.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Jul 26, 2019
 */
@Entity
@Table(name = "tblLuckySpinHistoryFake")
@Setter
@Getter
@NoArgsConstructor
public class LuckySpinHistoryFake {
    
    @Id
    @GeneratedValue
    private int    id;

    @CreationTimestamp
    private Date   createOn;

    private Long   luckySpinId;

    private String userName;

    private String itemName;

    private String phone;

    private String itemType;

    private long   itemValue;

    private String description;
    
    private boolean highlights;

}
