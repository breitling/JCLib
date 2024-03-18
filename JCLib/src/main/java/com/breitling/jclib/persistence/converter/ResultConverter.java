package com.breitling.jclib.persistence.converter;

import com.breitling.jclib.chess.Result;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ResultConverter implements AttributeConverter<Result,String>
{
	@Override
	public String convertToDatabaseColumn(Result attribute) 
	{
		String r = attribute.getValue();
		
		return r;
	}

	@Override
	public Result convertToEntityAttribute(String dbData) 
	{
		Result r = Result.valueOfResult(dbData);
		
		return r;
	}
}
