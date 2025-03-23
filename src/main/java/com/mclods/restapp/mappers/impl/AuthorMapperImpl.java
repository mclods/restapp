package com.mclods.restapp.mappers.impl;

import com.mclods.restapp.domain.dto.AuthorDto;
import com.mclods.restapp.domain.entities.AuthorEntity;
import com.mclods.restapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapperImpl implements Mapper<AuthorEntity, AuthorDto> {
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(AuthorMapperImpl.class);

    public AuthorMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDto mapTo(AuthorEntity authorEntity) {
        logger.info("Mapping AuthorEntity to AuthorDto");
        return modelMapper.map(authorEntity, AuthorDto.class);
    }

    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) {
        logger.info("Mapping AuthorDto to AuthorEntity");
        return modelMapper.map(authorDto, AuthorEntity.class);
    }
}
