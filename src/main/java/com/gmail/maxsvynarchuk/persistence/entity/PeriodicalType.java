package com.gmail.maxsvynarchuk.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "periodical_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeriodicalType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "periodical_type_id")
    private Integer id;

    @Column(name = "type_name")
    @NotNull
    private String name;

    @Column(name = "type_description")
    private String description;
}
