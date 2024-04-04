package com.breitling.jclib.dao;

import java.util.Optional;

import com.breitling.jclib.persistence.Source;

public interface SourceDAO
{
	public Optional<Source> findById(long id);
	
	public int persistSource(Source source);
}
