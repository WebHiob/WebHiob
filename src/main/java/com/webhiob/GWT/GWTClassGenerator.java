package com.webhiob.GWT;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.webhiob.GWT.utils.GWTClassFromStringFactory;
import com.webhiob.GWT.utils.GWTLesson;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GWTClassGenerator extends Generator
{
    public final Class<?> INTERFACE = GWTLesson.class;

    @Override public String generate(TreeLogger logger, GeneratorContext context, String typeName)
		    throws UnableToCompleteException
    {
	List<JClassType> allInstantiableClasses = findAllClassesMarkedWithInterface(context);
	String genPackageName = GWTClassFromStringFactory.class.getPackage().getName();
	String genClassName = GWTClassFromStringFactory.class.getSimpleName() + "Impl";
	ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(genPackageName, genClassName);
	PrintWriter printWriter = context.tryCreate(logger, genPackageName, genClassName);
	if (printWriter != null)
	{
	    composer.addImplementedInterface(GWTClassFromStringFactory.class.getSimpleName());
	    SourceWriter sourceWriter = composer.createSourceWriter(context, printWriter);
	    generateFactoryClass(genClassName, sourceWriter, logger, allInstantiableClasses);
	}
	return composer.getCreatedClassName();
    }

    private List<JClassType> findAllClassesMarkedWithInterface(GeneratorContext context)
    {
	TypeOracle oracle = context.getTypeOracle();
	JClassType markerInterface = oracle.findType(INTERFACE.getName());
	List<JClassType> allInstantiableClasses = new ArrayList<>();
	for (JClassType classType : oracle.getTypes())
	{
	    if (!classType.equals(markerInterface) && classType.isAssignableTo(markerInterface))
	    {
		allInstantiableClasses.add(classType);
	    }
	}
	return allInstantiableClasses;
    }

    private void generateFactoryClass(String genClassName, SourceWriter sourceWriter, TreeLogger logger, List<JClassType> allInstantiableClasses)
    {
	sourceWriter.println("public " + genClassName + "() {} \n");
	generateFactoryMethod(allInstantiableClasses, sourceWriter);
	sourceWriter.commit(logger);
    }

    private void generateFactoryMethod(List<JClassType> allInstantiableClasses, SourceWriter sourceWriter)
    {
	sourceWriter.println("public Object init(String className) { \n");
	sourceWriter.println("Object newObject = null; \n");
	for (JClassType classType : allInstantiableClasses)
	{
	    sourceWriter.println("if (className.equals(\"" + classType.getQualifiedSourceName() + "\")) { \n");
	    sourceWriter.println("newObject = new " + classType.getQualifiedSourceName() + "(); \n");
	    sourceWriter.println("} \n");
	}
	sourceWriter.println("return newObject; \n");
	sourceWriter.println("} \n");
    }
}
