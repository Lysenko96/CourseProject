package edu.lysenko.catalog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersTasks {

	private int userId;
	private int taskId;
}
