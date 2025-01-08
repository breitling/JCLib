package com.breitling.jclib.dao;

import com.breitling.jclib.model.Move;

public class MoveDAOImpl extends CrudNitriteRepository<Move> implements MoveDAO
{
	public MoveDAOImpl(String name) {
		super(name);
	}
}