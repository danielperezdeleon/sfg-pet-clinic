package com.immutableant.sfgpetclinic.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "owners")
public class Owner extends Person {

  @Column(name = "address")
  private String address;

  @Column(name = "city")
  private String city;

  @Column(name = "telephone")
  private String telephone;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
  @ToString.Exclude
  private final Set<Pet> pets = new HashSet<>();

  public Pet getPet(Long id) {
    for (Pet pet : getPets()) {
      if (!pet.isNew()) {
        Long compId = pet.getId();
        if (compId.equals(id)) {
          return pet;
        }
      }
    }
    return null;
  }

  public void addPet(Pet pet) {
    if (pet.isNew()) {
      getPets().add(pet);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Owner owner = (Owner) o;
    return getId() != null && Objects.equals(getId(), owner.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  public Pet getPet(String name, boolean ignoreNew) {
    name = name.toLowerCase();
    for (Pet pet : getPets()) {
      if (!ignoreNew || !pet.isNew()) {
        String compName = pet.getName();
        compName = compName == null ? "" : compName.toLowerCase();
        if (compName.equals(name)) {
          return pet;
        }
      }
    }
    return null;
  }
}
