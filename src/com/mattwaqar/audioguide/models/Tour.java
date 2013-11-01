package com.mattwaqar.audioguide.models;

import java.util.List;

import com.activeandroid.Model;

public class Tour extends Model {
	
	/*
	 * A tour is set of ordered tracks created by author
	 * as continuous and complete experience.
	 */
	
	List<Guide> guides;
	String title;

}
