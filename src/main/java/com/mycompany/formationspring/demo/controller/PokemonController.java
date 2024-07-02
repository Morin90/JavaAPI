package com.mycompany.formationspring.demo.controller;

import com.mycompany.formationspring.demo.entity.Pokemon;
import com.mycompany.formationspring.demo.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pokemons")
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    @GetMapping
    public List<Pokemon> getAllPokemons() {
        return pokemonService.getAllPokemons();
    }

    @GetMapping("/{id}")
    public Pokemon getPokemonById(@PathVariable Long id) {
        return pokemonService.getPokemonById(id);
    }

    @PostMapping
    public Pokemon createPokemon(@RequestBody Pokemon pokemon) {
        return pokemonService.savePokemon(pokemon);
    }

    @PutMapping("/{id}")
    public Pokemon updatePokemon(@PathVariable Long id, @RequestBody Pokemon pokemon) {
        pokemon.setId(id);
        return pokemonService.savePokemon(pokemon);
    }

    @DeleteMapping("/{id}")
    public void deletePokemon(@PathVariable Long id) {
        pokemonService.deletePokemon(id);
    }
    @GetMapping("/search")
    public List<Pokemon> searchPokemons(@RequestParam String term) {
        if (term.length() <= 1) {
            return List.of();
        }
        return pokemonService.searchPokemonsByName(term);
    }
}
