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

package com.webhiob.shared.init;

import com.webhiob.shared.lessons.lessonhelper.creation.Lesson;
import com.webhiob.shared.lessons.lessonhelper.transfer.LessonManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitClass implements InitializingBean
{
    @Override
    public void afterPropertiesSet() throws Exception
    {
	BeanDefinitionRegistry bdr = initBeanDefinitionRegistry();
	List<Lesson> allLessons = createLessons(bdr);
	LessonManager.setAllLessons(allLessons);
    }

    private BeanDefinitionRegistry initBeanDefinitionRegistry()
    {
	BeanDefinitionRegistry bdr = new SimpleBeanDefinitionRegistry();
	ClassPathBeanDefinitionScanner s = new ClassPathBeanDefinitionScanner(bdr);
	s.setIncludeAnnotationConfig(false);
	TypeFilter tf = new AssignableTypeFilter(Lesson.class);
	s.addIncludeFilter(tf);
	s.scan("com.webhiob.shared.lessons.lesson");
	return bdr;
    }

    private List<Lesson> createLessons(BeanDefinitionRegistry bdr)
    {
	String[] beans = bdr.getBeanDefinitionNames();
	List<Lesson> allLessons = new ArrayList<>();
	for (String bean : beans)
	{
	    BeanDefinition beanDefinition = bdr.getBeanDefinition(bean);
	    String beanClassName = beanDefinition.getBeanClassName();
	    Class<?> clazz ;
	    Constructor<?> ctor;
	    Lesson lesson = null;
	    try
	    {
		clazz = Class.forName(beanClassName);
		ctor = clazz.getConstructor();
		lesson = (Lesson) ctor.newInstance();
	    }
	    catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e)
	    {
		e.printStackTrace();
	    }
	    assert lesson != null;
	    allLessons.add(lesson);
	}
	return allLessons;
    }
}
