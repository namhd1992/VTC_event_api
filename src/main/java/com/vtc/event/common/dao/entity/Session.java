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
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Sep 24, 2019
 */
@Entity
@Table(name = "tblSession")
@Getter
@Setter
@NoArgsConstructor
public class Session {

    @Id
    @GeneratedValue
    private Long   id;

    @CreationTimestamp
    private Date   createOn;

    @UpdateTimestamp
    private Date   updateOn;

    private Long   scoinId;

    private String accessToken;

    private Date   loginTime;

    private Date   expiresDate;

    private String clientIP;

}
