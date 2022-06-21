package com.attipoe.springit.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
//@NoArgsConstructor
public class Vote extends Auditable{

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private short direction;

    // user
    // link

    @NonNull
    @ManyToOne
    private Link link;



}

