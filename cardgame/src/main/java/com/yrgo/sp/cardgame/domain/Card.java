package com.yrgo.sp.cardgame.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * @author simon 
 * Entity that saves the picture of the card as a byte array in the database.
 */
@Entity
public class Card {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String title;
	private String description;
	private String score;
	private Category category;
	// private Player author;?
	private int interval;// Enum? Tänkt att kopplas till ikonerna i klimatkoll

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] picture;// https://vaadin.com/blog/saving-and-displaying-images-using-jpa

	public Card() {
		// TODO Auto-generated constructor stub
	}

	public Card(String title, String description, Category category, String score, byte[] picture, int interval) {
		super();
		this.title = title;
		this.description = description;
		this.category = category;
		this.score = score;
		this.picture = picture;
		this.interval = interval;
	}

	@Override
	public String toString() {
		return "Card: " + title + ", Description: " + description + ", Score: " + score;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

}
