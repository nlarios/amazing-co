package com.tradeshift.amazing.core.mapper;

import com.tradeshift.amazing.domain.dto.NodeDTO;
import com.tradeshift.amazing.domain.entity.Node;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EntityDtoMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public EntityDtoMapper() {
        this.modelMapper.addConverter(uuidToStringConverter);
        this.modelMapper.addConverter(stringToUuidConverter);
    }

    public static Converter<UUID, String> uuidToStringConverter = new Converter<UUID, String>() {
        @Override
        public String convert(MappingContext<UUID, String> mappingContext) {
            if (mappingContext.getSource() != null) {
                return mappingContext.getSource().toString();
            }
            return null;
        }
    };

    public static Converter<String, UUID> stringToUuidConverter = new Converter<String, UUID>() {
        @Override
        public UUID convert(MappingContext<String, UUID> mappingContext) {
            if (mappingContext.getSource() != null) {
                return UUID.fromString(mappingContext.getSource());
            }
            return null;
        }
    };



    public NodeDTO map(Node entity) {
        return this.modelMapper.map(entity, NodeDTO.class);
    }

    public Node map(NodeDTO dto) {
        return this.modelMapper.map(dto, Node.class);
    }


}
