package com.breitling.jclib.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.breitling.jclib.persistence.GamePosition;

@Repository
public interface GamePositionDAO extends CrudRepository<GamePosition,Long>
{

}
