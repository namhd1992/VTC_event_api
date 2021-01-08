package com.vtc.event.common.dao.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by phucnguyen on 23/06/2017.
 */
@Entity
@Table(name = "tblLuckySpinItem")
@Setter
@Getter
@NoArgsConstructor
public class LuckySpinItem {
    @Id
    @GeneratedValue
    private Long               id;

    private String             name;

    private String             keyName;

    private long               value;

    private String             type;

    private int                quantity;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "createBy")
    private UserInfo           createBy;

    private String             status;

    private int                isDefault;

    @CreationTimestamp
    private Date               createOn;

    private int                weight;

    private String             urlImage;

    private int                defaultPosition    = 1;

    private String             winningTitle;

    private String             wheelTitle;

    private String             description;

    private boolean            bigItem;

    private boolean            highLights;

    @JsonBackReference
    @ManyToMany(mappedBy = "luckySpinItems", fetch = FetchType.LAZY)
    private Set<LuckySpinGift> luckySpinItemOfGifts ;

}
