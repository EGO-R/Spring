package org.java4me.spring.mapper;

public interface Mapper<F, T> {
    T map(F obj);

    default T map(F fromObject, T toObject) {
        return toObject;
    }
}
