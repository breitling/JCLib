package com.breitling.jclib.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.breitling.jclib.persistence.Source;

@Repository
public interface ResourceDAO extends CrudRepository<Source,Long>
{

}
