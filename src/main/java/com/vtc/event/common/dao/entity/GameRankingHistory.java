package com.vtc.event.common.dao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the game_ranking_history database table.
 * 
 */
@Entity
@Table(name="game_ranking_history")
@NamedQuery(name="GameRankingHistory.findAll", query="SELECT g FROM GameRankingHistory g")
@Getter
@Setter
@NoArgsConstructor
public class GameRankingHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue()
    private Long              id;

    @CreationTimestamp
    private Date              createOn;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "gameRanking")
    private GameRanking       gameRanking;

    @ManyToOne
    @JoinColumn(name = "item")
    private GameRankingItem   item;

    private String            itemType;

    private String            content;

    private int               quantity;

    private String            status;

    @UpdateTimestamp
    private Date              updateOn;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "userInfo")
    private UserInfo          userInfo;

    private int               value;

}