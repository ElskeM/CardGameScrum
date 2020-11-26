package com.yrgo.sp.cardgame.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author simon 
 * Entity that saves the picture of the card as a byte array in the database.
 */
@Entity
public class Card {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(nullable=false)
	private String title;
	private String description;
	@Column(nullable=false)
	private String score;
	
	@ManyToOne
	private Category category;
	private String author = "Admin";
	private Integer frequence;// Enum? Tänkt att kopplas till ikonerna i klimatkoll

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] picture;// https://vaadin.com/blog/saving-and-displaying-images-using-jpa

	public Card() {
		// TODO Auto-generated constructor stub
	}

	public Card(String title, String score) {
		this.title = title;
		this.score = score;
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

	public Card setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public Card setDescription(String description) {
		this.description = description;
		return this;
	}

	public Category getCategory() {
		return category;
	}

	public Card setCategory(Category category) {
		this.category = category;
		return this;
	}

	public String getScore() {
		return score;
	}

	public Card setScore(String score) {
		this.score = score;
		return this;
	}

	public byte[] getPicture() {
		return picture;
	}

	public Card setPicture(byte[] picture) {
		this.picture = picture;
		return this;
	}

	public Integer getInterval() {
		return frequence;
	}

	public Card setInterval(Integer interval) {
		this.frequence = interval;
		return this;
	}

	public String getAuthor() {
		return author;
	}

	public Card setAuthor(String author) {
		this.author = author;
		return this;
	}

}
