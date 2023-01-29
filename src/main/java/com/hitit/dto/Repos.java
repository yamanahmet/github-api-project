package com.hitit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repos {
    private Long id;
    private String node_id;
    private String name;
    private String full_name;

}
