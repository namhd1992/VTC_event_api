/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.common.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vtc.event.common.dao.entity.Session;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Sep 24, 2019
 */
public interface SessionRepository extends JpaRepository<Session, Long> {

    Session findByAccessToken(String accessToken);

    Session findByScoinId(Long scoinId);

}
