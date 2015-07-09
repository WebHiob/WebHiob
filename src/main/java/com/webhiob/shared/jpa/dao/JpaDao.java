/***************************************************************************************************
 *
 *
 * This file is part of WebHiob, an Robert Rozmus utility.
 * WebHiob was created a as part of the master thesis by Robert Rozmus (student of Warsaw University of Technology, Institute of Computer Science)
 * Copyright (c) 2015 Robert Rozmus
 *
 * This program has got two licences:
 *  1. For non-commercial use - you can redistribute it and/or modify it under the terms of the
 *  		GNU General Public License version 3.0 (GPLv3);
 *
 *  2. For any commercial use (including payable academic lectures) - you must obtain the permission from the author
 *  (Robert Rozmus) to use it in these purposes
 *
 * @author <a href="mailto:robertrozmusjob@gmail.com">Robert Rozmus</a>
 */

package com.webhiob.shared.jpa.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public abstract class JpaDao<T, I> implements Dao<T, I>
{
    @PersistenceContext
    protected EntityManager entityManager;

    protected Class<T> entityClass;

    public JpaDao(Class<T> entityClass)
    {
	this.entityClass = entityClass;
    }

    @Override
    @Transactional(readOnly = true)
    public T find(I id)
    {
	return entityManager.find(entityClass, id);
    }

    @Override
    @Transactional
    public T update(T entity)
    {
	return entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void insert(T entity)
    {
	entityManager.persist(entity);
    }
}
