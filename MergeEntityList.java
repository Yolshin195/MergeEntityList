package com.yolshin.merge;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Attention!
 * if Function<T, String> lambdaGetCode == null:
 *     throw new RuntimeException("You must set lambdaGetCode with param T entity and return String")
 *
 * @param <T> - this is the type of your Entity class
 */
public class MergeEntityList<T> {
    public static final String version = "1.1.0";

    @FunctionalInterface
    public interface LambdaOnCollision<T> {
        T onCollision(MergeEntityList.Type type, List<T> entityList);
    }

    public enum Type {
        NEW, OLD
    }

    private Function<T, String> lambdaGetCode = null;
    private BiConsumer<T, T> lambdaOnOldAndNew = null;
    private LambdaOnCollision<T> lambdaOnCollision = null;
    private Consumer<T> lambdaOnOnlyNew = null;
    private Consumer<T> lambdaOnOnlyOld = null;

    public void merge(List<T> newEntityList, List<T> oldEntityList) {
        Map<String, Map<Type, List<T>>> value = new HashMap<>();

        this.fill(value, newEntityList, Type.NEW);
        this.fill(value, oldEntityList, Type.OLD);

        for (Map<Type,  List<T>> entityMap : value.values()) {
            if (entityMap.containsKey(Type.NEW) && entityMap.containsKey(Type.OLD)) {
                onOldAndNew(getEntity(entityMap, Type.NEW), getEntity(entityMap, Type.OLD));
            } else if (entityMap.containsKey(Type.NEW)) {
                onOnlyNew(getEntity(entityMap, Type.NEW));
            } else if (entityMap.containsKey(Type.OLD)) {
                onOnlyOld(getEntity(entityMap, Type.OLD));
            }
        }
    }

    protected void fill(Map<String, Map<Type, List<T>>> value, List<T> entityList, Type type) {
        for (T entity : entityList ) {
            String key = this.getCode(entity);
            if (!value.containsKey(key)) {
                Map<Type, List<T>> entityMap = new HashMap<>();
                value.put(key, entityMap);
            }

            if (!value.get(key).containsKey(type)) {
                value.get(key).put(type, new ArrayList<>());
            }

            value.get(key).get(type).add(entity);
        }
    }

    protected String getCode(T entity) {
        if (Objects.nonNull(lambdaGetCode)) {
            return this.lambdaGetCode.apply(entity);
        }

        throw new RuntimeException("You must set lambdaGetCode with param T entity and return String");
    }

    protected void onOnlyNew(T newEntity) {
        if (Objects.nonNull(lambdaOnOnlyNew)) {
            lambdaOnOnlyNew.accept(newEntity);
        }
    }

    protected void onOnlyOld(T oldEntity) {
        if (Objects.nonNull(lambdaOnOnlyNew)) {
            lambdaOnOnlyOld.accept(oldEntity);
        }
    }

    protected void onOldAndNew(T newEntity, T oldEntity) {
        if (Objects.nonNull(lambdaOnOldAndNew)) {
            lambdaOnOldAndNew.accept(newEntity, oldEntity);
        }
    }

    protected T getEntity(Map<Type,  List<T>> entityMap, Type type) {
        List<T> entityList = entityMap.get(type);

        if (entityList.size() == 1) {
            return entityList.get(0);
        }

        if (Objects.nonNull(lambdaOnCollision)) {
            return lambdaOnCollision.onCollision(type, entityList);
        }

        return entityList.get(0);
    }

    public MergeEntityList<T> setLambdaGetCode(Function<T, String> lambdaGetCode) {
        this.lambdaGetCode = lambdaGetCode;
        return this;
    }

    public MergeEntityList<T> setLambdaOnOldAndNew(BiConsumer<T, T> lambdaOnOldAndNew) {
        this.lambdaOnOldAndNew = lambdaOnOldAndNew;
        return this;
    }

    public MergeEntityList<T> setLambdaOnOnlyNew(Consumer<T> lambdaOnOnlyNew) {
        this.lambdaOnOnlyNew = lambdaOnOnlyNew;
        return this;
    }

    public MergeEntityList<T> setLambdaOnOnlyOld(Consumer<T> lambdaOnOnlyOld) {
        this.lambdaOnOnlyOld = lambdaOnOnlyOld;
        return this;
    }

    public MergeEntityList<T> setLambdaOnCollision(LambdaOnCollision<T> lambdaOnCollision) {
        this.lambdaOnCollision = lambdaOnCollision;
        return this;
    }
}
