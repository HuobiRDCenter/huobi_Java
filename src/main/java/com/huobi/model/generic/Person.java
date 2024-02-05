package com.huobi.model.generic;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Person {
    private Integer id;
    private String name;
    private Integer weight;
}
