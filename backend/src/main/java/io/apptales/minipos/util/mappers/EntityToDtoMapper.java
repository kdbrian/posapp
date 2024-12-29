package io.apptales.minipos.util.mappers;

public abstract class EntityToDtoMapper <T,D>{
    public abstract T fromDto(D d);
    public abstract D toDto(T t);
}
