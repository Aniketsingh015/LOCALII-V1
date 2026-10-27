package com.localii.backend.catalog.dto;

import com.localii.backend.catalog.entity.Category;

import lombok.Getter;

import java.util.UUID;

@Getter

public class CategoryResponse{
    private final UUID id;
    private final String name;

    public CategoryResponse(Category c){//here we are using whole category rather than calling single value at a time
        //brecause in future if we add anything in category entity we will directly use it below
    this.id=c.getId();
    this.name=c.getName();
}

}

