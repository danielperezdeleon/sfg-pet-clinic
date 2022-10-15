package com.immutableant.sfgpetclinic.services.map;

import com.immutableant.sfgpetclinic.model.BaseEntity;

import java.util.*;

public abstract class AbstractMapService<T extends BaseEntity, ID extends Long> {
  protected Map<Long, T> map = new HashMap<>();

  private Long getNextId() {
    Long nextId = null;
    try {
      nextId = Collections.max(map.keySet()) + 1;

    } catch (NoSuchElementException exp) {
      System.out.println("No starting element, setting initial value");
      nextId = 1L;
    }
    return nextId;
  }

  Set<T> findAll() {
    return new HashSet<>(map.values());
  }

  T findById(ID id) {
    return map.get(id);
  }

  T save(T object) {
    if (object != null) {
      if (object.getId() == null) {
        object.setId(getNextId());
      }
      map.put(object.getId(), object);
    } else {
      throw new RuntimeException("Object cannot be null");
    }

    return object;
  }

  void deleteById(ID id) {
    map.remove(id);
  }

  void delete(T object) {
    map.entrySet().removeIf(entry -> entry.getValue().equals(object));
  }
}
