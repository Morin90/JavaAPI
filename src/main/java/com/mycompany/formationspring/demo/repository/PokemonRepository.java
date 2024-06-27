package com.mycompany.formationspring.demo.repository;

import com.mycompany.formationspring.demo.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    List<Pokemon> findByName(String name);


}