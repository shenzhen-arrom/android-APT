package com.langyi.apt_processor;

import com.google.auto.service.AutoService;
import com.langyi.apt_annotation.AptAnnotation;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
@SupportedOptions("MODULE_NAME")
@SupportedAnnotationTypes("com.langyi.apt_annotation.AptAnnotation")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AptAnnotationProcessor extends AbstractProcessor {

    Filer  filer;

    private String mModuleName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        mModuleName = processingEnv.getOptions().get("MODULE_NAME");
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set == null || set.isEmpty()) {
            return false;
        }

        //获取当前注解下的节点信息
        Set<? extends Element> rootElements = roundEnvironment.getElementsAnnotatedWith(AptAnnotation.class);

        // 构建 test 函数
        MethodSpec.Builder builder = MethodSpec.methodBuilder("test")
                .addModifiers(Modifier.PUBLIC) // 指定方法修饰符
                .returns(void.class) // 指定返回类型
                .addParameter(String.class, "param"); // 添加参数
        builder.addStatement("$T.out.println($S)", System.class, "模块: " + mModuleName);

        if (rootElements != null && !rootElements.isEmpty()) {
            for (Element element : rootElements) {
                //当前节点名称
                String elementName = element.getSimpleName().toString();
                //当前节点下注解的属性
                String desc = element.getAnnotation(AptAnnotation.class).desc();
                // 构建方法体
                builder.addStatement("$T.out.println($S)", System.class,
                        "节点: " + elementName + "  " + "描述: " + desc);
            }
        }
        MethodSpec main =builder.build();

        // 构建 HelloWorld 类
        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC) // 指定类修饰符
                .addMethod(main) // 添加方法
                .build();

        // 指定包路径，构建文件体
        JavaFile javaFile = JavaFile.builder("com.langyi.aptdemo", helloWorld).build();
        try {
            // 创建文件
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}