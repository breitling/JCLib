package com.breitling.jclib.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.breitling.jclib.persistence.Source;

@Repository
public interface SourceDAO extends CrudRepository<Source,Long>
{

}
