package com.vtc.event.common.dao.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the game_ranking database table.
 * 
 */
@Entity
@Table(name="game_ranking")
@NamedQuery(name="GameRanking.findAll", query="SELECT g FROM GameRanking g")
@Getter
@Setter
@NoArgsConstructor
public class GameRanking implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long                   id;

    @CreationTimestamp
    @Column(name = "create_on")
    private Date                  createOn;

    @UpdateTimestamp
    @Column(name = "update_on")
    private Date                  updateOn;

    @Column(name = "create_by")
    private Long                  createBy;

    @Lob
    private String                description;

    private String                name;

    @Column(name = "service_id")
    private long                  serviceId;

    private String                status;

//    @OneToMany(mappedBy = "gameRankingBean")
//    private List<GameRankingItem> gameRankingItems;

    @OneToMany(mappedBy = "gameRanking")
    private List<GameRankingRank> gameRankingRanks;

}