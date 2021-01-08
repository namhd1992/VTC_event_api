/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vtc.event.common.dao.entity.UserVTC;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * May 6, 2019
 */
@Repository
public interface UserVTCRepository extends JpaRepository<UserVTC, Long> {

    Optional<UserVTC> findByUsername(String userName);
    
    Optional<UserVTC> findByScoinId(Long scoinId);
    
}
