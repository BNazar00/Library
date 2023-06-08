package com.bn.clients.util.converter;

import com.bn.clients.util.marker.Convertible;
import java.lang.reflect.Type;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DtoConverter {
    private final ModelMapper modelMapper;

    public DtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <T, D extends com.bn.clients.util.marker.Convertible> D convertToEntity(T dto, D entity) {
        return modelMapper.map(dto, (Type) entity.getClass());
    }

    public <T, D extends com.bn.clients.util.marker.Convertible> T convertToDto(D entity, Type dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public <T, D extends com.bn.clients.util.marker.Convertible> D convertFromDtoToDto(T dtoConverted,
                                                                                       D dtoConverting) {
        return modelMapper.map(dtoConverted, (Type) dtoConverting.getClass());
    }

    public <T extends com.bn.clients.util.marker.Convertible, D> List<D> convertToDtoList(List<T> entityList,
                                                                                          Class<D> dtoClass) {
        return entityList.stream()
                .map(entity -> modelMapper.map(entity, dtoClass))
                .toList();
    }

    public <T, D extends Convertible> List<D> convertToEntityList(List<T> dtoList, Class<D> entityClass) {
        return dtoList.stream()
                .map(dto -> modelMapper.map(dto, entityClass))
                .toList();
    }
}
