package com.immutableant.sfgpetclinic.services.springdatajpa;

import com.immutableant.sfgpetclinic.model.Owner;
import com.immutableant.sfgpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

  @InjectMocks OwnerSDJpaService ownerSDJpaService;

  @Mock OwnerRepository ownerRepository;

  Long ownerId = 1L;
  String lastName = "Smith";

  @BeforeEach
  void setUp() {}

  @Test
  void findAll() {
    Set<Owner> resultSet = new HashSet<>();
    resultSet.add(Owner.builder().id(ownerId).lastName(lastName).build());
    when(ownerRepository.findAll()).thenReturn(resultSet);
    Set<Owner> ownerSet = ownerSDJpaService.findAll();
    assertEquals(1, ownerSet.size());
  }

  @Test
  void findById() {
    when(ownerRepository.findById(ownerId))
        .thenReturn(Optional.ofNullable(Owner.builder().id(ownerId).lastName(lastName).build()));
    Owner owner = ownerSDJpaService.findById(ownerId);
    assertEquals(ownerId, owner.getId());
  }

  @Test
  void findByIdNotFound() {
    when(ownerRepository.findById(ownerId)).thenReturn(Optional.empty());
    Owner owner = ownerSDJpaService.findById(ownerId);
    assertNull(owner);
  }

  @Test
  void save() {
    Owner owner = Owner.builder().id(ownerId).lastName(lastName).build();
    when(ownerRepository.save(owner)).thenReturn(owner);
    Owner ownerResult = ownerSDJpaService.save(owner);
    assertEquals(ownerResult, owner);
  }

  @Test
  void delete() {
    Owner owner = Owner.builder().id(ownerId).lastName(lastName).build();
    ArgumentCaptor<Owner> ownerArgumentCaptor = ArgumentCaptor.forClass(Owner.class);
    doNothing().when(ownerRepository).delete(ownerArgumentCaptor.capture());
    ownerSDJpaService.delete(owner);
    assertEquals(ownerArgumentCaptor.getValue(), owner);
  }

  @Test
  void deleteById() {
    Owner owner = Owner.builder().id(ownerId).lastName(lastName).build();
    ArgumentCaptor<Long> ownerArgumentCaptor = ArgumentCaptor.forClass(Long.class);
    doNothing().when(ownerRepository).deleteById(ownerArgumentCaptor.capture());
    ownerSDJpaService.deleteById(ownerId);
    assertEquals(ownerArgumentCaptor.getValue(), ownerId);
  }

  @Test
  void deleteByIdNotFound() {
    Owner owner = Owner.builder().id(ownerId).lastName(lastName).build();
    ArgumentCaptor<Long> ownerArgumentCaptor = ArgumentCaptor.forClass(Long.class);
    doNothing().when(ownerRepository).deleteById(ownerArgumentCaptor.capture());
    ownerSDJpaService.deleteById(ownerId);
    assertEquals(ownerArgumentCaptor.getValue(), ownerId);
  }

  @Test
  void findByLastName() {
    Owner owner = Owner.builder().id(ownerId).lastName(lastName).build();
    List<Owner> owners = new ArrayList<>();
    owners.add(owner);
    when(ownerRepository.findAllByLastNameContainingIgnoreCase(lastName)).thenReturn(owners);
    List<Owner> foundOwners = ownerSDJpaService.findByLastName(lastName);
    assertTrue(foundOwners.contains(owner));
  }

  @Test
  void findByLastNameNotFound() {
    when(ownerRepository.findAllByLastNameContainingIgnoreCase(lastName)).thenReturn(null);
    List<Owner> foundOwners = ownerSDJpaService.findByLastName(lastName);
    assertNull(foundOwners);
  }
}
