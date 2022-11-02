package com.immutableant.sfgpetclinic.controllers;

import com.immutableant.sfgpetclinic.model.Owner;
import com.immutableant.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

  @Mock OwnerService ownerService;

  @InjectMocks OwnerController ownerController;

  MockMvc mockMvc;

  Set<Owner> owners = new HashSet<>();

  @BeforeEach
  void setUp() {
    owners.add(Owner.builder().id(1L).lastName("abc").build());
    owners.add(Owner.builder().id(2L).lastName("def").build());
    owners.add(Owner.builder().id(3L).lastName("ghi").build());

    mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
  }

  @Test
  void listOwners() throws Exception {
    when(ownerService.findAll()).thenReturn(owners);
    mockMvc
        .perform(get("/owners"))
        .andExpect(status().is(200))
        .andExpect(view().name("owners/index"))
        .andExpect(model().attributeExists("owners"))
        .andExpect(model().attribute("owners", owners));
  }

  @Test
  void findOwners() throws Exception {

    mockMvc
        .perform(get("/owners/find"))
        .andExpect(status().is(200))
        .andExpect(view().name("owners/findOwners"))
        .andExpect((model().attributeExists("owner")));
  }

  @Test
  void showOwner() throws Exception {
    when(ownerService.findById(1L)).thenReturn(Owner.builder().id(1L).build());
    mockMvc
        .perform(get("/owners/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("owners/ownerDetails"))
        .andExpect(model().attribute("owner", hasProperty("id", is(1L))));
  }

  @Test
  void processFindFormReturnMany() throws Exception {
    List<Owner> ownerList = new ArrayList<>();
    ownerList.add(Owner.builder().id(1L).lastName("abc").build());
    ownerList.add(Owner.builder().id(2L).lastName("abcd").build());
    when(ownerService.findByLastName("abc")).thenReturn(ownerList);
    mockMvc
        .perform(get("/owners/findOwners").param("lastName", "abc"))
        .andExpect(status().isOk())
        .andExpect(view().name("owners/ownersList"))
        .andExpect(model().attribute("selections", ownerList));
  }

  @Test
  void processFindFormReturnOne() throws Exception {
    List<Owner> ownerList = new ArrayList<>();
    ownerList.add(Owner.builder().id(1L).lastName("abc").build());
    when(ownerService.findByLastName("abc")).thenReturn(ownerList);
    mockMvc
        .perform(get("/owners/findOwners").param("lastName", "abc"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/owners/" + 1L));
  }

  @Test
  void processFindFormReturnNone() throws Exception {
    List<Owner> ownerList = new ArrayList<>();
    when(ownerService.findByLastName("abc")).thenReturn(ownerList);
    mockMvc
        .perform(get("/owners/findOwners").param("lastName", "abc"))
        .andExpect(status().isOk())
        .andExpect(view().name("owners/findOwners"));
  }

  @Test
  void initCreationForm() throws Exception {
    mockMvc
        .perform(get("/owners/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("owners/createOrUpdateOwnerForm"))
        .andExpect((model().attributeExists("owner")));
    verifyNoInteractions(ownerService);
  }

  @Test
  void processCreationForm() throws Exception {
    when(ownerService.save(ArgumentMatchers.any())).thenReturn(Owner.builder().id(1L).build());
    mockMvc
        .perform(post("/owners/new").param("lastName", "lastName").param("firstName", "firstName"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/owners/1"))
        .andExpect((model().attributeExists("owner")));
    verify(ownerService).save(ArgumentMatchers.any());
  }

  @Test
  void processCreationValidationFailForm() throws Exception {
    mockMvc
        .perform(post("/owners/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    verifyNoInteractions(ownerService);
  }

  @Test
  void initUpdateOwnerForm() throws Exception {
    when(ownerService.findById(1L)).thenReturn(Owner.builder().id(1L).build());
    mockMvc
        .perform(get("/owners/1/edit"))
        .andExpect(status().isOk())
        .andExpect(view().name("owners/createOrUpdateOwnerForm"))
        .andExpect((model().attributeExists("owner")));
    verify(ownerService).findById(ArgumentMatchers.any());
  }

  @Test
  void processUpdateOwnerForm() throws Exception {
    when(ownerService.save(ArgumentMatchers.any())).thenReturn(Owner.builder().id(1L).build());
    mockMvc
        .perform(
            post("/owners/1/edit").param("lastName", "lastName").param("firstName", "firstName"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/owners/1"))
        .andExpect((model().attributeExists("owner")));
    verify(ownerService).save(ArgumentMatchers.any());
  }

  @Test
  void processUpdateOwnerValidationFailForm() throws Exception {
    mockMvc
        .perform(post("/owners/1/edit"))
        .andExpect(status().isOk())
        .andExpect(view().name("owners/createOrUpdateOwnerForm"))
        .andExpect((model().attributeExists("owner")));
    verifyNoInteractions(ownerService);
  }
}
