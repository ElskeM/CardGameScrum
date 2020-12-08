package com.yrgo.sp.cardgame.game;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.yrgo.sp.cardgame.data.CardRepository;
import com.yrgo.sp.cardgame.domain.Card;

@Controller
public class Deck {
	
	
	private Set<Card> cards;
	
	@Autowired
	private CardRepository cardData;
	
	
	public Deck() {
		
	//	this.cards = cardData.findAll().stream().collect(Collectors.toSet());
			}

	/*public Card draw() {
		// TODO Auto-generated method stub
		return new Card("testcard"+new Random().nextInt(50),1000);
	}
	*/
	

	
	public Card draw() {
		List<Card> cardlist = this.cards.stream().collect(Collectors.toList());
		Card random = cardlist.remove(new Random().nextInt(cardlist.size()));
		this.cards = cardlist.stream().collect(Collectors.toSet());
		return random;
	}

	
	
	
	public void fillDeck() {
		/*Detta fungerar inte då jag måste autowirea in cardData och för att få göra det måste jag autowirea 
		in deck och för att göra det måste jag autowirea in game och allt blir ett helvete
		*/
		//this.cards = cardData.findAll().stream().collect(Collectors.toSet());
		
		Set<Card> cardList = new HashSet<Card>();
		Card card1 = new Card("Blandkost", 2000);
		card1.setBackImage("http://localhost:8080/images/Kort1_back.jpg");
		card1.setFrontImage("http://localhost:8080/images/Kort1_front.jpg");
		card1.setSubtitle("Svenskt genomsnitt");
		card1.setFrequence(365);
		card1.setDescription( "1 års mat för en genomsnittlig svensk");
		card1.setExtraInfo("Utsläpp från nöttkött: 45 %, mjölkprodukter: 25%");
		card1.setId(1l);
		
		Card card2 = new Card("Vegetarisk kost", 900);
		card2.setBackImage("http://localhost:8080/images/Kort3_front.jpg");
		card2.setFrontImage("http://localhost:8080/images/Kort1_front.jpg");
		card2.setSubtitle(null);
		card2.setFrequence(365);
		card2.setDescription( "1 års mat för en genomsnittlig svensk vegetarian, protein från växter, ägg och mjölkprodukter");
		card2.setExtraInfo("Utsläpp från mjölkprodukter: 50%");
		card2.setId(2l);
	
		Card card3 = new Card("Ostmackor", 250);
		card3.setBackImage("http://localhost:8080/images/Kort5_back.jpg");
		card3.setFrontImage("http://localhost:8080/images/Kort5_front.jpg");
		card3.setSubtitle(null);
		card3.setFrequence(365);
		card3.setDescription(  "två ostmackor med smör om dagen under ett år");
		card3.setExtraInfo( "Utsläpp från ost: 50%, smör: 35%");
		card3.setId(3l);
		
		Card card4 = new Card("Äggmackor", 120);
		card4.setBackImage("http://localhost:8080/images/Kort6_back.jpg");
		card4.setFrontImage("http://localhost:8080/images/Kort6_front.jpg");
		card4.setSubtitle(null);
		card4.setFrequence(365);
		card4.setDescription(  "två äggmackor med margarin om dagen under ett år");
		card4.setExtraInfo( "Utsläpp från ägg: 70%, margarin: 10%");
		card4.setId(4l);
		
		Card card5 = new Card("Yoghurt", 200);
		card5.setBackImage("http://localhost:8080/images/Kort7_back.jpg");
		card5.setFrontImage("http://localhost:8080/images/Kort7_front.jpg");
		card5.setSubtitle("med müsli");
		card5.setFrequence(365);
		card5.setDescription(  "En tallrik yoghurt med müsli varje dag under ett år");
		card5.setExtraInfo( "Utsläpp från yoghurt: 70%");
		card5.setId(5l);
		
		Card card6 = new Card("Gröt", 60);
		card6.setBackImage("http://localhost:8080/images/Kort8_back.jpg");
		card6.setFrontImage("http://localhost:8080/images/Kort8_front.jpg");
		card6.setSubtitle("med havremjölk");
		card6.setFrequence(365);
		card6.setDescription(  "En tallrik havregrynsgröt med havremjölk varje dag under ett år");
		card6.setExtraInfo( null);
		card6.setId(6l);
		
		Card card7 = new Card("Halloumiburgare", 400);
		card7.setBackImage("http://localhost:8080/images/Kort9_back.jpg");
		card7.setFrontImage("http://localhost:8080/images/Kort9_front.jpg");
		card7.setSubtitle(null);
		card7.setFrequence(365);
		card7.setDescription(  "En halloumiburgare eller annan ostrik måltid varje dafg under ett år");
		card7.setExtraInfo( "Utsläpp av metan: 40%");
		card7.setId(7l);
		
		
		cardList.add(card1);
		cardList.add(card2);
		cardList.add(card3);
		cardList.add(card4);
		cardList.add(card5);
		cardList.add(card6);
		cardList.add(card7);
		
		this.cards = cardList; 

	}
}
