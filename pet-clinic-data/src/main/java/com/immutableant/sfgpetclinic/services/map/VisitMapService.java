package com.immutableant.sfgpetclinic.services.map;

import com.immutableant.sfgpetclinic.model.Visit;
import com.immutableant.sfgpetclinic.services.VisitService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class VisitMapService extends AbstractMapService<Visit, Long> implements VisitService {

  private final VisitService visitService;

  public VisitMapService(VisitService visitService) {
    this.visitService = visitService;
  }

  @Override
  public Set<Visit> findAll() {
    return super.findAll();
  }

  @Override
  public void deleteById(Long id) {
    super.deleteById(id);
  }

  @Override
  public void delete(Visit visit) {
    super.delete(visit);
  }

  @Override
  public Visit save(Visit visit) {
    if (visit.getPet() == null
        || visit.getPet().getOwner() == null
        || visit.getPet().getId() == null) {
      throw new RuntimeException("Invalid Visit");
    }

    return super.save(visit);
  }

  @Override
  public Visit findById(Long id) {
    return super.findById(id);
  }
}