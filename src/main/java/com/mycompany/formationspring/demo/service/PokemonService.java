package com.mycompany.formationspring.demo.service;

import com.mycompany.formationspring.demo.entity.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mycompany.formationspring.demo.repository.PokemonRepository;
import java.util.List;

@Service
public class PokemonService {

    @Autowired
    private PokemonRepository PokemonRepository;

    public List<Pokemon> getAllPokemons() {
        return PokemonRepository.findAll();
    }

    public Pokemon getPokemonById(Long id) {
        return PokemonRepository.findById(id).orElse(null);
    }

    public Pokemon savePokemon(Pokemon pokemon) {
        return PokemonRepository.save(pokemon);
    }

    public void deletePokemon(Long id) {
        PokemonRepository.deleteById(id);
    }
}