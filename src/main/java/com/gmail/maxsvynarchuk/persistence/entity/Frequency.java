package com.gmail.maxsvynarchuk.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "frequencies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Frequency implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "frequency_id")
    private Integer id;

    @Column(name = "frequency_name")
    @NotNull
    private String name;

    @NotNull
    private String meaning;
}
