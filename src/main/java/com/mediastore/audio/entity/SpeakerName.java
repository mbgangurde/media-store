package com.mediastore.audio.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author mbgangurde
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpeakerName {

	private String name;
	private String surname;

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

}
