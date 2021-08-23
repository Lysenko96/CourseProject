package edu.lysenko.catalog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

	private int id;
	private String tag;
	private String title;

	public Task(String tag, String title) {
		this.tag = tag;
		this.title = title;
	}

}
