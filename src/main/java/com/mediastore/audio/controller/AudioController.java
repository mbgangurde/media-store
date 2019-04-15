package com.mediastore.audio.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.mediastore.audio.entity.Audio;
import com.mediastore.audio.entity.DBFile;
import com.mediastore.audio.entity.Gender;
import com.mediastore.audio.service.AudioService;
import com.mediastore.audio.service.DBFileService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author mbgangurde
 *
 */

@Slf4j
@RestController
public class AudioController {

	@Value("${valid.file.format}")
	private String validFileFormat;

	@Autowired
	private AudioService audioService;

	@Autowired
	private DBFileService dBFileService;

	@GetMapping(value = "/audio/{id}")
	public ResponseEntity<ByteArrayResource> getAudio(@PathVariable(value = "id") Long audioId) {
		DBFile dBFile = dBFileService.getAudioFile(audioId);
		
		log.info("Audio is returned for " + audioId);
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(dBFile.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dBFile.getFileName() + "\"")
				.body(new ByteArrayResource(dBFile.getData()));
	}

	@GetMapping(value = "/audio/info/{id}")
	public ResponseEntity<Audio> getAudioInfo(@PathVariable(value = "id") Long audioId) {
		Audio audio = audioService.getAudioInfo(audioId);

		log.info("Audio info is returned for " + audioId);
		return ResponseEntity.ok().body(audio);
	}

	@PostMapping(value = "/audio")
	public ResponseEntity<?> createAudio(@Valid @RequestParam("file") MultipartFile file,
			@RequestParam(value = "recordingDate", required = true) @DateTimeFormat(iso = ISO.DATE) Date recordingDate,
			@RequestParam(value = "speakerGender", required = true) Gender speakerGender,
			UriComponentsBuilder ucBuilder) {

		if (file.isEmpty()) {
			log.warn("File is empty");
			return ResponseEntity.badRequest().body("File is empty");
		}
		if (!validFileFormat.equals(file.getContentType())) {
			log.warn("Audio file save rejected due to invalid content type " + file.getContentType());
			return ResponseEntity.badRequest()
					.body("Only WAV file format is supported.., you are loading " + file.getContentType());
		}

		Audio audio = audioService.saveAudio(file, recordingDate, speakerGender);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/audio/{id}").buildAndExpand(audio.getAudioId()).toUri());
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/audio/{id}")
	public void removeAudio(@PathVariable(value = "id") Long audioId) {
		log.info("Audio info is deleted for " + audioId);
		audioService.deleteAudio(audioId);
	}

}
