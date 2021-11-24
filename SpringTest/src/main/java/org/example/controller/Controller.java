package org.example.controller;

import org.example.logic.Pet;
import org.example.logic.PetModel;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {
    private static final PetModel petModel = PetModel.getInstance();
    private static final AtomicInteger newId = new AtomicInteger(1);
    PrintWriter pw = new PrintWriter(System.out, true);

    @PostMapping(value = "/createPet", consumes = "application/json")
    public String createPet(@RequestBody Pet pet){
            petModel.Add(pet, newId.getAndIncrement());
            if (petModel.getAll().size() == 1){
                return "Создан первый питомец!";
            }
            return "Создан новый питомец!";
    }

    @GetMapping(value = "/getAll", produces = "application/json")
    public Map<Integer, Pet> getAll(){
        return petModel.getAll();
    }

    @GetMapping(value = "/getPet", consumes = "application/json", produces = "application/json")
    public Pet getPet(@RequestBody Map<String, Integer> id){
        return petModel.getFromList(id.get("id"));
    }

    @DeleteMapping(value = "/deletePet", consumes = "application/json")
    public void deletePet(@RequestBody Map<String, Integer> id){
        petModel.Delete(id.get("id"));
    }

    @PutMapping(value = "/putPet/{id}", consumes = "application/json")
    public void putPet(@RequestBody Pet pet, @PathVariable("id") int id){
        petModel.Put(pet, id);
    }
}
