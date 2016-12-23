package photoNet.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Comment {

	private int id;
	private Photo photo;
	private String text;
	private Profile author;
	private List<Comment> responses = new ArrayList<>();
	private LocalDate date;
	private String color;

	public int getId() {
		return id;
	}

	public Comment setId(int id) {
		this.id = id;
		return this;
	}

	public Photo getPhoto() {
		return photo;
	}

	public Comment setPhoto(Photo photo) {
		this.photo = photo;
		return this;
	}

	public String getText() {
		return text;
	}

	public Comment setText(String text) {
		this.text = text;
		return this;
	}

	public Profile getAuthor() {
		return author;
	}

	public Comment setAuthor(Profile author) {
		this.author = author;
		return this;
	}

	public List<Comment> getResponses() {
		return responses;
	}

	public Comment setResponses(List<Comment> responseTo) {
		this.responses = responseTo;
		return this;
	}

	public Comment addResponse(Comment resp){
		this.responses.add(resp);
		return this;
	}

	public LocalDate getDate() {
		return date;
	}

	public Comment setDate(LocalDate date) {
		this.date = date;
		return this;
	}

	public String getColor() {
		return color;
	}

	public Comment setColor(String color) {
		this.color = color;
		return this;
	}
}
