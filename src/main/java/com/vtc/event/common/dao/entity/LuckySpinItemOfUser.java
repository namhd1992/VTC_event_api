package com.vtc.event.common.dao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the
 * tblluckySpinItemOfUser database
 * table.
 */
@Entity
@Table(name = "tblLuckySpinItemOfUser")
@NamedQuery(name = "LuckySpinItemOfUser.findAll", query = "SELECT t FROM LuckySpinItemOfUser t")
@Getter
@Setter
@NoArgsConstructor
public class LuckySpinItemOfUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int               id;

    @CreationTimestamp
    private Date              createOn;

    @UpdateTimestamp
    private Date              updateOn;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "luckySpin")
    private LuckySpin         luckySpin;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "luckySpinItem")
    private LuckySpinItem     luckySpinItem;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userInfo")
    private UserInfo          userInfo;

    private int               quantity;

}