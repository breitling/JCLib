package com.breitling.jclib.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.breitling.jclib.persistence.DB;

@Repository
public interface DatabaseDAO extends CrudRepository<DB,Long>
{
}
