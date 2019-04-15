package com.mediastore.audio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediastore.audio.entity.DBFile;
import com.mediastore.audio.exception.AudioNotAvailableException;
import com.mediastore.audio.repository.DBFileRepository;

@Service
public class DBFileService {
	
	@Autowired
	DBFileRepository dBFileRepository;

	public DBFile getAudioFile(Long audioId)
	{
		DBFile dBFile = dBFileRepository.findById(audioId).orElseThrow(() -> new AudioNotAvailableException(audioId));
		return dBFile;
	}
}
