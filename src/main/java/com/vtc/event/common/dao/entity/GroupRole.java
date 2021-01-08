package com.vtc.event.common.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by phucnguyen on 24/04/2017.
 */
@Entity
@Table(name = "tblGroupRole")
@Getter
@Setter
@NoArgsConstructor
public class GroupRole {
    @Id
    @GeneratedValue
    private int    id;

    private String name;

    private int    point = 1;

    private String status;

}
