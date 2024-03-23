package com.breitling.jclib.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.breitling.jclib.persistence.Position;

@Repository
public interface PositionDAO extends CrudRepository<Position,Long>
{

}
