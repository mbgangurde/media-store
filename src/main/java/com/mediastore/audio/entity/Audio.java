package com.mediastore.audio.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mbgangurde
 *
 */

@Entity
@Table(name = "audios")
public class Audio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	private Long audioId;

	@Enumerated(EnumType.STRING)
	@NotNull
	private Gender speakerGender;

	@Getter
	private Date recordingDate;

	@Getter
	private String speakerName;

	@Getter
	@Setter
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "audio")
	private DBFile dBFile;

	public Audio() {
		super();
	}

	public Audio(Gender speakerGender, Date recordingDate, String speakerName) {
		super();
		this.speakerGender = speakerGender;
		this.recordingDate = recordingDate;
		this.speakerName = speakerName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((audioId == null) ? 0 : audioId.hashCode());
		result = prime * result + ((recordingDate == null) ? 0 : recordingDate.hashCode());
		result = prime * result + ((speakerGender == null) ? 0 : speakerGender.hashCode());
		result = prime * result + ((speakerName == null) ? 0 : speakerName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Audio other = (Audio) obj;
		if (audioId == null) {
			if (other.audioId != null)
				return false;
		} else if (!audioId.equals(other.audioId))
			return false;
		if (recordingDate == null) {
			if (other.recordingDate != null)
				return false;
		} else if (!recordingDate.equals(other.recordingDate))
			return false;
		if (speakerGender != other.speakerGender)
			return false;
		if (speakerName == null) {
			if (other.speakerName != null)
				return false;
		} else if (!speakerName.equals(other.speakerName))
			return false;
		return true;
	}

}
