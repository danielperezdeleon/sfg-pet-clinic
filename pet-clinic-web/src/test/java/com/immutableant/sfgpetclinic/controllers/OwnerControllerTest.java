package com.immutableant.sfgpetclinic.controllers;

import com.immutableant.sfgpetclinic.model.Owner;
import com.immutableant.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

  @Mock OwnerService ownerService;

  @InjectMocks OwnerController ownerController;

  MockMvc mockMvc;

  Set<Owner> owners = new HashSet<>();

  @BeforeEach
  void setUp() {
    owners.add(Owner.builder().id(1L).build());
    owners.add(Owner.builder().id(2L).build());
    owners.add(Owner.builder().id(3L).build());

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
        .andExpect(view().name("notimplemented"));
  }
}
