/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dao.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Jan 4, 2020
 */
@Entity
@Table(name = "tblLuckySpinGift")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class LuckySpinGift {

    @Id
    @GeneratedValue
    private Long               id;

    @CreationTimestamp
    private Date               createOn;

    @UpdateTimestamp
    private Date               updateOn;

    private String             name;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "luckySpin")
    private LuckySpin          luckySpin;

    private int                setPoint;

    private String             typeGift;

    private long               value;

    private int                quantity;

    private String             description;

    @JsonBackReference
    @ManyToMany()
    @JoinTable(
        name = "tblLuckySpinItemOfGift", 
        joinColumns = @JoinColumn(name = "luckySpinGift"), 
        inverseJoinColumns = @JoinColumn(name = "luckySpinItem"))
    private Set<LuckySpinItem> luckySpinItems;

}
