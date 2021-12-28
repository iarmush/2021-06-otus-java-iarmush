package ru.otus.mapper;

import ru.otus.crm.model.annottaions.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData {

    private final String name;
    private final Constructor<T> constructor;
    private final List<Field> allFields;
    private final Field idField;
    private final List<Field> fieldsWithoutId;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        name = clazz.getSimpleName();
        constructor = getConstructorWithoutException(clazz);
        allFields = Arrays.asList(clazz.getDeclaredFields());
        idField = getFieldWithId(allFields);
        fieldsWithoutId = getFieldsExceptFieldWithId();
    }

    private Field getFieldWithId(List<Field> allFields) {
        for (Field field : allFields) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }
        return null;
    }

    private List<Field> getFieldsExceptFieldWithId() {
        List<Field> fieldList = new ArrayList<>(allFields);
        fieldList.removeIf(field -> field.isAnnotationPresent(Id.class));
        return fieldList;
    }

    private Constructor<T> getConstructorWithoutException(Class<T> clazz) {
        try {
            return clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("not found constructor");
        }
    }

    @Override
    public String getName() {
        int lastPoint = name.lastIndexOf(".");
        return name.substring(lastPoint + 1).toLowerCase(Locale.ROOT);
    }

    @Override
    public <T> Constructor<T> getConstructor() {
        return (Constructor<T>) constructor;
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }
}
