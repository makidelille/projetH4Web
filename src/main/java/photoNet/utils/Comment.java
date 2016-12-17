package photoNet.utils;

import java.util.Date;

public class Comment {

	private int id;
	private Photo photo;
	private String text;
	private Profile author;
	private Comment responseTo;
	private Date date;
	private String color;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Comment getResponseTo() {
		return responseTo;
	}

	public Comment setResponseTo(Comment responseTo) {
		this.responseTo = responseTo;
		return this;
	}

	public Date getDate() {
		return date;
	}

	public Comment setDate(Date date) {
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
