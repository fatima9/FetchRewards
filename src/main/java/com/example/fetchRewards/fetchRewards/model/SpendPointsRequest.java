package com.example.fetchRewards.fetchRewards.model;

import static com.example.fetchRewards.fetchRewards.util.FetchRewardsServiceConstants.VALUE_NOT_EMPTY;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class SpendPointsRequest {

	@NotBlank(message = VALUE_NOT_EMPTY)
	private String points;
}
