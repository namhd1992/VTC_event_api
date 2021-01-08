package com.vtc.event.common.dao.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the game_ranking_item database table.
 * 
 */
@Entity
@Table(name="game_ranking_item")
@NamedQuery(name="GameRankingItem.findAll", query="SELECT g FROM GameRankingItem g")
@Getter
@Setter
@NoArgsConstructor
public class GameRankingItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long              id;

    @Column(name = "create_on")
    private Timestamp         createOn;

    private String            description;

    @Column(name = "item_icon_url")
    private String            itemIconUrl;

    @Column(name = "item_name")
    private String            itemName;

    @Column(name = "item_type")
    private String            itemType;
    
    @Column(name = "item_quantity")
    private int               itemQuantity;

    @Column(name = "item_value")
    private int               itemValue;

    private byte[]            status;

    @Column(name = "update_on")
    private Timestamp         updateOn;

    @Column(name = "winning_tittle")
    private String            winningTittle;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "game_ranking")
    private GameRanking       gameRanking;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "rank")
    private GameRankingRank   gameRankingRank;

}