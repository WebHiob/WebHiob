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

package com.webhiob.shared.jpa.repository;

import com.webhiob.shared.jpa.dao.JpaDao;
import com.webhiob.shared.jpa.entity.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class EmployeeRepositoryImpl extends JpaDao<Employee, Integer> implements EmployeeRepository
{
    public EmployeeRepositoryImpl()
    {
	super(Employee.class);
    }

    @Transactional
    @Override
    public void init() {
	insertEmployee("Jan", "Warsaw", "passJan");
	insertEmployee("Bob", "London", "passBob");
	insertEmployee("Ela", "Paris", "passEla");
    }

    private void insertEmployee(String name, String city, String password) {
	Employee employee = new Employee();
	employee.setName(name);
	employee.setCity(city);
	employee.setPassword(password);

	insert(employee);
    }

    @Override
    public List<Employee> getJPQLEmployeeByName(String nameParameter)
    {
	String q = "SELECT e FROM Employee e WHERE e.name = '" + nameParameter + "'";
	TypedQuery<Employee> query = entityManager.createQuery(q, Employee.class);
	return query.getResultList();
    }

    @Override
    public List<Employee> getSQLEmployeeByName(String nameParameter)
    {
	String q = "SELECT e.id, e.name, e.password, e.city FROM Employee e WHERE e.name = '" + nameParameter + "'";
	Query nativeQuery = entityManager.createNativeQuery(q, Employee.class);
	return nativeQuery.getResultList();
    }



    /*
    //
    //
    //  Solution of the JPQL injection lesson
    //
    //
    //
    //
    //
     @Override
    public List<Employee> getJPQLEmployeeByName(String nameParameter)
    {
	String q = "SELECT e FROM Employee e WHERE e.name = :name";
	TypedQuery<Employee> query = entityManager.createQuery(q, Employee.class);
	query.setParameter("name",nameParameter);
	return query.getResultList();
    }

    @Override
    public List<Employee> getSQLEmployeeByName(String nameParameter)
    {
	String q = "SELECT e.id, e.name, e.password, e.city FROM Employee e WHERE e.name = :name";
	Query nativeQuery = entityManager.createNativeQuery(q, Employee.class);
	nativeQuery.setParameter("name", nameParameter);
	return nativeQuery.getResultList();
    }


     */

}

