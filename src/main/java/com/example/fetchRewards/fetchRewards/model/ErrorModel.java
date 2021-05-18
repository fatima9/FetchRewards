package com.example.fetchRewards.fetchRewards.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ErrorModel {

	private String errorCode;
	private String message;

	public ErrorModel() {
		super();
	}

	public ErrorModel(String errorCode, String message) {
		super();
		this.errorCode = errorCode;
		this.message = message;
	}
}
