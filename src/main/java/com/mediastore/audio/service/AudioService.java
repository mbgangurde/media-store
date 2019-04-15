package com.mediastore.audio.service;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.mediastore.audio.entity.Audio;
import com.mediastore.audio.entity.DBFile;
import com.mediastore.audio.entity.Gender;
import com.mediastore.audio.entity.SpeakerName;
import com.mediastore.audio.exception.AudioNotAvailableException;
import com.mediastore.audio.exception.FileStorageException;
import com.mediastore.audio.repository.AudioRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author mbgangurde
 *
 */

@Slf4j
@Service
public class AudioService {

	@Autowired
	private AudioRepository audioRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${speaker.name.api.url}")
	private String url;

	@Value("${speaker.name.default}")
	private String defaultSpeakerName;

	public Audio getAudioInfo(Long audioId) {
		Audio audio = audioRepository.findById(audioId).orElseThrow(() -> new AudioNotAvailableException(audioId));
		return audio;
	}

	public Audio saveAudio(MultipartFile file, Date recordingDate, Gender speakerGender) {
		String speakerName = getSpeakerName(speakerGender);

		Audio audio = new Audio(speakerGender, recordingDate, speakerName);

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {

			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				log.error("Filename contains invalid path sequence" + fileName);
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());

			audio.setDBFile(dbFile);
			dbFile.setAudio(audio);

			audioRepository.save(audio);

			log.info("Audio data saved with audio id as " + audio.getAudioId());

		} catch (Exception ex) {
			log.error("Could not store file " + fileName + ". Please try again!");
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}

		return audio;
	}

	public void deleteAudio(Long audioId) {
		if (!audioRepository.existsById(audioId)) {
			return;
		}
		audioRepository.deleteById(audioId);
	}

	private String getSpeakerName(Gender speakerGender) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> httpEntity = new HttpEntity<String>(headers);

		try {
			ResponseEntity<SpeakerName> speakerNameResponseEntity = restTemplate.exchange(url + speakerGender.toString().toLowerCase(),
					HttpMethod.GET, httpEntity, SpeakerName.class);

			if (speakerNameResponseEntity.getStatusCode() == HttpStatus.OK) {
				SpeakerName speakerName = speakerNameResponseEntity.getBody();
				return speakerName.getName() + " " + speakerName.getSurname();
			}
		} catch (ResourceAccessException exception) {
			log.error("Invlid URL passed for UINames API, setting default speaker name", exception);
		} catch (Exception exception) {
			log.error("Error connecting UINames API, setting default speaker name", exception);
		}
		return defaultSpeakerName;
	}
}
