package com.mediastore.audio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediastore.audio.entity.DBFile;

/**
 * @author mbgangurde
 *
 */

public interface DBFileRepository  extends JpaRepository<DBFile, Long> {

}
