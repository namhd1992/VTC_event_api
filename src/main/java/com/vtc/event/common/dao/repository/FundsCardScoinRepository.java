/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vtc.event.common.dao.entity.FundsCardScoin;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Jul 9, 2019
 */
@Repository
public interface FundsCardScoinRepository extends JpaRepository<FundsCardScoin, Long> {
    
    Optional<FundsCardScoin> findByOrgTransIDAndVtcTransID(String orgTransID, String vtcTransID);

}
