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

package com.webhiob.GWT.lessoncontroller;

import com.webhiob.shared.jpa.entity.Employee;
import com.webhiob.shared.jpa.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("JPQL")
public class JPQLInjectionController
{
    @Autowired
    private EmployeeRepository employeeRepository;

    @RequestMapping(value = "/JPQLQuery")
    public List<Employee> getJPQLEmployee(@RequestParam(value = "nameParameter") String nameParameter) {

        return employeeRepository.getJPQLEmployeeByName(nameParameter);
    }

    @RequestMapping(value = "/SQLQuery")
    public List<Employee> getSQLEmployee(@RequestParam(value = "nameParameter") String nameParameter) {

        return employeeRepository.getSQLEmployeeByName(nameParameter);
    }
}
