package com.github.arlekinside.diploma.ws.controller;

import com.github.arlekinside.diploma.data.entity.Saving;
import com.github.arlekinside.diploma.data.repo.SavingRepo;
import com.github.arlekinside.diploma.logic.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/saving")
@RequiredArgsConstructor
public class SavingController extends AbstractCrudController<Saving>{

    private final SavingRepo savingRepo;

    @Override
    public Saving create(Authentication auth, @RequestBody Saving saving) {
        saving.setUser(getUser(auth));
        return savingRepo.save(saving);
    }

    @Override
    public List<Saving> readAll(Authentication auth) {
        return savingRepo.findAllByUser(getUser(auth));
    }

    @Override
    public Saving read(Authentication auth, @PathVariable Long id) {
        return savingRepo.findByIdAndUser(id, getUser(auth))
                .orElseThrow(() -> new NotFoundException(id, Saving.class));
    }

    @Override
    public Saving update(Authentication auth, Long id, Saving saving) {
        throw new NotFoundException(null, null);
    }

    @Override
    public void delete(Authentication auth, @PathVariable Long id) {
        savingRepo.deleteByIdAndUser(id, getUser(auth));
    }
}
