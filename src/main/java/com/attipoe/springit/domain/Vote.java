package com.attipoe.springit.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
//@NoArgsConstructor
public class Vote {

    @Id
    @GeneratedValue
    private Long id;
    private int vote;

    // user
    // link
}

