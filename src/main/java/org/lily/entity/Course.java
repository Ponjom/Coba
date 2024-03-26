package org.lily.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "courses")
public class Course extends AbstractEntity {
	@Serial
	private static final long serialVersionUID = 2L;

	private String name;
	private String description;
	private Integer price;
	private String image_path;
	private Boolean active;

	@Column(name = "name", length = 255)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", columnDefinition = "text")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "price")
	public Integer getPrice() {
		return this.price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@Column(name = "image_path", length = 255)
	public String getImagePath() {
		return this.image_path;
	}

	public void setImagePath(String image_path) {
		this.image_path = image_path;
	}

	@Column(name = "active")
	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}