package com.yrgo.sp.cardgame.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author simon 
 * Entity that saves the picture of the card as a byte array in the database.
 */
/**
 * @author elske
 *
 */
/**
 * @author elske
 *
 */
@Entity
public class Card implements Comparable<Card> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String title; // Visas överst på kortet, med versaler.
	private String subtitle; // Visas under title, med gemener i mindre storlek än title.
	private String description; // Vad är score ett mått på, inkl. frequence.
	private String extraInfo; // Extra information om källorna till score (t.ex. nötköttproduktion orsakar 45%
								// av score)
	@Column(nullable = false)
	private Integer score;

	@ManyToOne
	private Category category;
	private String author = "Admin";
	private Integer frequence;
	
//	@Lob
//	@Basic(fetch = FetchType.LAZY)
	private String frontImage;// https://vaadin.com/blog/saving-and-displaying-images-using-jpa
//	@Lob
//	@Basic(fetch = FetchType.LAZY)
	private String backImage;

	
	/**
	 * Empty constructor to create a card object
	 */
	public Card() {}

	
	/**
	 * Constructor for that takes a title and score as parameter
	 * @param title
	 * @param score
	 */
	public Card(String title, Integer score) {
		this.title = title;
		this.score = score;
	}

	/**
	 * ToString method
	 */
	@Override
	public String toString() {
		return "Card: " + title + ", Description: " + description + ", Score: " + score;
	}

	// Getter and Setter methods
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Integer getScore() {
		return score;
	}

	public Card setScore(Integer score) {
		this.score = score;
		return this;
	}

	public String getFrontImage() {
		return frontImage;
	}

	public Card setFrontImage(String picture) {
		this.frontImage = picture;
		return this;
	}

	public Integer getFrequence() {
		return frequence;
	}

	public Card setFrequence(Integer frequence) {
		this.frequence = frequence;
		return this;
	}

	public String getAuthor() {
		return author;
	}

	public Card setAuthor(String author) {
		this.author = author;
		return this;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}

	public String getBackImage() {
		return backImage;
	}

	public void setBackImage(String backImage) {
		this.backImage = backImage;
	}
	
	/**
	 * CompareTo method for score
	 */
	@Override
	public int compareTo(Card o) {
		return score.compareTo(o.getScore());
	}
}
