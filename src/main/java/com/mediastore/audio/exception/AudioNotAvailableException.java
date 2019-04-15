package com.mediastore.audio.exception;

/**
 * @author mbgangurde
 *
 */

public class AudioNotAvailableException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AudioNotAvailableException(Long audioId) {
		super("No audio available for the audio id " + audioId);
	}

	
}
