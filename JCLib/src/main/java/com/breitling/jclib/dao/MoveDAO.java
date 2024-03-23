package com.breitling.jclib.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.breitling.jclib.persistence.Move;

@Repository
public interface MoveDAO extends CrudRepository<Move,Long>
{

}
