package com.epam.demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category extends AbstractEntity {
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Category parent;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
	private Set<Product> products;

	@OneToMany(mappedBy = "parent")
	private Set<Category> childCategories;
}
