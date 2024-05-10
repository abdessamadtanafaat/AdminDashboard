package com.majorMedia.BackOfficeDashboard.entity.campaign;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "compagne_language",
            joinColumns = @JoinColumn(name = "language_id" ,referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "compagne_id",referencedColumnName ="id")
    )
    private Set<Campaign> compagnes;


}
