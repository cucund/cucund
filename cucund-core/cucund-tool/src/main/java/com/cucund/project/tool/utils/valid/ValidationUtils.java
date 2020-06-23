package com.cucund.project.tool.utils.valid;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.cucund.project.tool.utils.json.JSONUtils;
import com.cucund.project.tool.utils.reflect.FieldDepthSearch;
import com.cucund.project.tool.utils.string.MessageUtil;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.util.StringUtils;

public class ValidationUtils {

    private static Validator failFastValidator = ((HibernateValidatorConfiguration) Validation.byProvider(HibernateValidator.class).configure()).failFast(true).buildValidatorFactory().getValidator();
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private ValidationUtils() {
    }

    public static <T> ValidResult fastFailValidate(T obj) {
        Set<ConstraintViolation<T>> constraintViolations = failFastValidator.validate(obj, new Class[0]);
        ConstraintViolation constraintViolation = constraintViolations.iterator().next();
        ValidResult validResult = constraintViolations.size() > 0 ? ValidResult.fail(fillStr((ConstraintViolation) constraintViolations)): ValidResult.success();
        return validResult;
    }

    public static <T> ValidResult allCheckValidate(T obj) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj, new Class[0]);
        if (constraintViolations.size() <= 0) {
            return ValidResult.success();
        } else {
            List<String> errorMessages = new LinkedList();
            Iterator iterator = constraintViolations.iterator();

            while (iterator.hasNext()) {
                ConstraintViolation<T> violation = (ConstraintViolation) iterator.next();
                String message = fillStr(violation);
                errorMessages.add(message);
            }

            return ValidResult.fail(errorMessages);
        }
    }

    private static<T> String fillStr(ConstraintViolation<T> violation) {
        String propertyName = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
        String message = null;
        try {
            Map<String,String> map = new HashMap<String,String>();
            dataInfoToMap(violation, propertyName, map);
//            annotationToMap(violation,propertyName,map);
            message = MessageUtil.getMes(violation.getMessage(),"\\{(.*?)\\}","{","}",map);
        } catch (NoSuchFieldException e) {
        }
        return message;
    }

    static List<String> ignoreList = new ArrayList<>();
    static {
        ignoreList.add("toString");
        ignoreList.add("hashCode");
        ignoreList.add("annotationType");
        ignoreList.add("equals");
    }

    private static <T> void annotationToMap(ConstraintViolation<T> violation, String propertyName, Map<String, String> map) {
        Annotation annotation = violation.getConstraintDescriptor().getAnnotation();
        Method[] methods = annotation.annotationType().getMethods();
        for (Method m: methods) {
            String name = m.getName();
            try {
                if(ignoreList.contains(name))
                    continue;
                Object invoke = m.invoke(annotation);
                if(invoke == null){
                    map.put(name, "");
                }else {
                    if(invoke instanceof Class[]){
                        Object[] x= (Object[]) invoke;
                        if(x.length == 0) {
                            invoke = "";
                        }else if(x.length == 1) {
                            invoke = x[0];
                        }
                    }
                    map.put(name, JSONUtils.obj2json(invoke));
                }
            } catch (Exception e) {
            }
        }
    }

    private static <T> void dataInfoToMap(ConstraintViolation<T> violation, String propertyName, Map<String, String> map) throws  NoSuchFieldException {
        Class<T> rootBeanClass = violation.getRootBeanClass();
        Field field = FieldDepthSearch.getField(rootBeanClass, propertyName);
        DataInfo[] dataInfos = field.getAnnotationsByType(DataInfo.class);
        for (DataInfo dataInfo: dataInfos){
            String key = dataInfo.key();
            if(StringUtils.isEmpty(key))
                continue;
            String value = dataInfo.value();
            map.put(key,value);
        }
    }
}
