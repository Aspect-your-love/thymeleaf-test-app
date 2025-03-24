package net.aspect.education.thymeleaftestapp.db.dto.mapper;

/**
 * Интерфейс маппера. Служит для обобщения всех мапперов.
 * Работает с двумя типами объектов:
 * <b>T</b> - Entity
 * <b>D</b> - DTO*/
public interface Mapper<E,D> {
    public D toDTO(E entity);
    public E toEntity(D dto);
}
