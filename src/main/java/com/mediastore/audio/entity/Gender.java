package com.mediastore.audio.entity;

import java.util.Arrays;

/**
 * @author mbgangurde
 *
 */

public enum Gender {
	MALE("male"), FEMALE("female");

	private String value;

	private Gender(String value) {
		this.value = value;
	}

	public static Gender fromValue(String value) {
		for (Gender category : values()) {
			if (category.value.equalsIgnoreCase(value)) {
				return category;
			}
		}
		throw new IllegalArgumentException(
				"Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
	}
}
