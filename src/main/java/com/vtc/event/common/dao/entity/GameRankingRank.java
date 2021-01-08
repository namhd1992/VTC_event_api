package com.vtc.event.common.dao.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the game_ranking_rank database table.
 * 
 */
@Entity
@Table(name="game_ranking_rank")
@NamedQuery(name="GameRankingRank.findAll", query="SELECT g FROM GameRankingRank g")
@Getter
@Setter
@NoArgsConstructor
public class GameRankingRank implements Serializable {
    private static final long     serialVersionUID = 1L;

    @Id
    private Long                  id;

    @CreationTimestamp
    private Date                  createOn;

    private String                decription;

    @Column(name = "rank_icon_url")
    private String                rankIconUrl;

    @Column(name = "rank_name")
    private String                rankName;

    @Column(name = "rank_position")
    private int                   rankPosition;

    @Column(name = "rank_quantity")
    private int                   rankQuantity;

    @UpdateTimestamp
    private Date                  updateOn;

    private String                status;

    @OneToMany(mappedBy = "gameRankingRank")
    private List<GameRankingItem> gameRankingItems;

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "game_ranking")
    private GameRanking           gameRanking;

}