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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by phucnguyen on 04/07/2017.
 */
@Entity
@Table(name = "tblLuckySpinSetting")
@Getter
@Setter
@NoArgsConstructor
public class LuckySpinSetting {
    
    @Id
    @GeneratedValue
    private Long      id;

    @CreationTimestamp
    private Date      createOn;

    @UpdateTimestamp
    private Date      updateOn;

    private String    name;

    private String    keyName;

    private String    type;

    private long      intValue;

    private double    doubleValue;

    private String    stringValue;

    private String    status;

    @ManyToOne
    @JoinColumn(name = "luckySpin")
    private LuckySpin luckySpin;

}
