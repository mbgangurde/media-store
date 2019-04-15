package com.mediastore.audio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mediastore.audio.entity.Audio;

/**
 * @author mbgangurde
 *
 */

public interface AudioRepository extends JpaRepository<Audio, Long> {

}
