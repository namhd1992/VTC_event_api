package com.vtc.event.common.dao.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by phucnguyen on 23/06/2017.
 */
@Entity
@Table(name = "tblLuckySpinItemOfLuckySpin")
@Setter
@Getter
@NoArgsConstructor
public class LuckySpinItemOfLuckySpin {
    
    @Id
    @GeneratedValue
    private Long       id;

    @CreationTimestamp
    private Date       createOn;

    @UpdateTimestamp
    private Date       updateOn;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "luckySpin")
    private LuckySpin  luckySpin;

    // @JsonIgnoreProperties({"hibernateLazyInitializer",
    // "handler"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item")
    private LuckySpinItem item;

    private int        totalQuantity;

    private int        receivedQuantity;

    private int        ratioIndex;

    private double     ratio;
    
    public LuckySpinItemOfLuckySpin(final LuckySpin luckySpin, final LuckySpinItem item) {
        this.luckySpin = luckySpin;
        this.item = item;
    }

}
