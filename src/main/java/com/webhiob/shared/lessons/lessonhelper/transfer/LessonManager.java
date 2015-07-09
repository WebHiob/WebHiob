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

package com.webhiob.shared.lessons.lessonhelper.transfer;

import com.webhiob.shared.lessons.LessonCategory;
import com.webhiob.shared.lessons.lessonhelper.creation.Lesson;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LessonManager
{
    private static List<Lesson> allLessons;
    private static List<LessonCategoryJSON> lessonCategoriesWithLessons;

    public static List<LessonCategoryJSON> getLessonCategoriesWithLessons()
    {
	return lessonCategoriesWithLessons;
    }

    public static void setAllLessons(List<Lesson> allLessons)
    {
	LessonManager.allLessons = allLessons;
	setLessonCategoriesWithLessons();
    }

    private static void setLessonCategoriesWithLessons()
    {
	List<LessonCategoryJSON> lessonCategories = new ArrayList<>();
	LessonCategoryJSON introduction = createLessonCategories(lessonCategories);
	addLessonsToCategories(lessonCategories);
	sortLessonCategories(lessonCategories);
	changeIntroductionCategoryToFirstPlace(lessonCategories, introduction);

	lessonCategoriesWithLessons = lessonCategories;
    }

    private static LessonCategoryJSON createLessonCategories(List<LessonCategoryJSON> lessonCategories)
    {
	LessonCategoryJSON introduction = null;

	for (LessonCategory category : LessonCategory.values())
	{
	    LessonCategoryJSON lessonCategoryJSON = new LessonCategoryJSON();
	    lessonCategoryJSON.setCategory(category);
	    lessonCategories.add(lessonCategoryJSON);
	    if (category == LessonCategory.Introduction)
	    {
		introduction = lessonCategoryJSON;
	    }
	}
	return introduction;
    }

    private static void addLessonsToCategories(List<LessonCategoryJSON> lessonCategories)
    {
	for (Lesson lesson : allLessons)
	{
	    for (LessonCategoryJSON category : lessonCategories)
	    {
		if (category.getCategory() == lesson.getCategory())
		{
		    LessonJSON lessonJSON = new LessonJSON();
		    lessonJSON.setId(lesson.getUrl());
		    lessonJSON.setName(lesson.getName());
		    lessonJSON.setViewName(lesson.getViewName());
		    lessonJSON.setTechnology(lesson.getTechnology());
		    category.addLesson(lessonJSON);
		}
	    }
	}

	for (LessonCategoryJSON category : lessonCategories)
	{
	    sortLessonsInCategory(category.getChildren());
	}
    }

    private static void sortLessonsInCategory(List<LessonJSON> lessons)
    {
	Collections.sort(lessons, new Comparator<LessonJSON>()
	{
	    @Override public int compare(final LessonJSON object1, final LessonJSON object2)
	    {
		return object1.getText().compareToIgnoreCase(object2.getText());
	    }
	});
    }

    private static void sortLessonCategories(List<LessonCategoryJSON> lessonCategories)
    {
	Collections.sort(lessonCategories, new Comparator<LessonCategoryJSON>()
	{
	    @Override public int compare(final LessonCategoryJSON object1, final LessonCategoryJSON object2)
	    {
		return object1.getCategory().toString().compareToIgnoreCase(object2.getCategory().toString());
	    }
	});
    }

    private static void changeIntroductionCategoryToFirstPlace(List<LessonCategoryJSON> lessonCategories, LessonCategoryJSON introduction)
    {
	lessonCategories.remove(introduction);
	lessonCategories.add(0, introduction);
    }

    public static Lesson getFaceletLesson(String viewName)
    {
	Lesson faceletLesson = null;
	for (Lesson lesson : allLessons)
	{
	    if (lesson.getViewName().equals(viewName))
	    {
		faceletLesson = lesson;
		break;
	    }
	}
	if (faceletLesson == null)
	{
	    faceletLesson = createDefaultLesson();
	}
	return faceletLesson;
    }

    private static Lesson createDefaultLesson()
    {
	Class<?> clazz;
	Constructor<?> ctor;
	Lesson lesson = null;
	try
	{
	    clazz = Class.forName("com.webhiob.shared.lessons.lesson.introduction.AboutWebHiob");
	    ctor = clazz.getConstructor();
	    lesson = (Lesson) ctor.newInstance();
	}
	catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e)
	{
	    e.printStackTrace();

	}
	return lesson;
    }
}
