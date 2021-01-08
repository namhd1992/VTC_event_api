package com.vtc.event.common.dao.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by phucnguyen on 03/04/2018.
 */
@Entity
@Table(name = "tblSplayTag")
@Setter
@Getter
@NoArgsConstructor
public class TblSplayTag {
    @Id
    @GeneratedValue
    private long   id;

    private String name;

    private String typeName;

    private String backgroundColor;

}
