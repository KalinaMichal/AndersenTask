package org.example.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter(value = AccessLevel.PUBLIC)
@AllArgsConstructor
public class FamilyMemberDto {

    private Long id;

    private String name;

    private Long fatherId;

    private Long motherId;
}
