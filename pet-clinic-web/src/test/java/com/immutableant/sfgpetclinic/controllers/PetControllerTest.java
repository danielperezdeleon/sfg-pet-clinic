package com.immutableant.sfgpetclinic.controllers;

import com.immutableant.sfgpetclinic.model.Owner;
import com.immutableant.sfgpetclinic.model.Pet;
import com.immutableant.sfgpetclinic.services.OwnerService;
import com.immutableant.sfgpetclinic.services.PetService;
import com.immutableant.sfgpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PetControllerTest {
  @Mock OwnerService ownerService;

  @Mock PetTypeService petTypeService;

  @Mock PetService petService;

  @InjectMocks PetController petController;

  MockMvc mockMvc;

  Owner owner;
  Owner ownerWithPet;

  Pet pet;

  @BeforeEach
  void setUp() {
    owner = Owner.builder().id(1L).firstName("test 1").lastName("Test 1").build();
    ownerWithPet = Owner.builder().id(1L).firstName("test 1").lastName("Test 1").build();
    pet = Pet.builder().id(1L).name("name").build();
    ownerWithPet.getPets().add(pet);
    mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
  }

  @Test
  void initCreationForm() throws Exception {
    when(ownerService.findById(ArgumentMatchers.any())).thenReturn(owner);
    mockMvc
        .perform(get("/owners/1/pets/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("pets/createOrUpdatePetForm"))
        .andExpect((model().attributeExists("pet")));
  }

  @Test
  void processCreationForm() throws Exception {
    when(ownerService.findById(ArgumentMatchers.any())).thenReturn(owner);
    when(ownerService.save(ArgumentMatchers.any())).thenReturn(Owner.builder().id(1L).build());
    mockMvc
        .perform(post("/owners/1/pets/new").param("name", "name"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/owners/1"))
        .andExpect((model().attributeExists("owner")));
    verify(ownerService).save(ArgumentMatchers.any());
  }

  @Test
  void processCreationValidationRepetedForm() throws Exception {
    when(ownerService.findById(ArgumentMatchers.any())).thenReturn(ownerWithPet);
    mockMvc
        .perform(post("/owners/1/pets/new").param("name", "name"))
        .andExpect(status().isOk())
        .andExpect(view().name("pets/createOrUpdatePetForm"))
        .andExpect(model().attributeExists("pet"));
  }

  @Test
  void processCreationValidationFailForm() throws Exception {
    when(ownerService.findById(ArgumentMatchers.any())).thenReturn(owner);
    mockMvc
        .perform(post("/owners/1/pets/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("pets/createOrUpdatePetForm"))
        .andExpect(model().attributeExists("pet"));
  }

  @Test
  void initUpdateForm() throws Exception {
    when(ownerService.findById(1L)).thenReturn(ownerWithPet);
    mockMvc
        .perform(get("/owners/1/pets/1/edit"))
        .andExpect(status().isOk())
        .andExpect(view().name("pets/createOrUpdatePetForm"))
        .andExpect((model().attributeExists("pet")))
        .andExpect((model().attribute("pet", pet)));
    verify(ownerService, times(2)).findById(ArgumentMatchers.any());
  }

  @Test
  void processUpdateForm() throws Exception {
    when(ownerService.findById(1L)).thenReturn(ownerWithPet);
    when(ownerService.save(ArgumentMatchers.any())).thenReturn(Owner.builder().id(1L).build());
    mockMvc
        .perform(post("/owners/1/pets/1/edit").param("name", "namename"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/owners/1"));
    verify(ownerService, times(2)).findById(ArgumentMatchers.any());
    verify(ownerService).save(ArgumentMatchers.any());
  }
}
