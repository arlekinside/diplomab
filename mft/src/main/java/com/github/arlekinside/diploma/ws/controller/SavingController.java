package com.github.arlekinside.diploma.ws.controller;

import com.github.arlekinside.diploma.data.entity.Saving;
import com.github.arlekinside.diploma.data.repo.SavingRepo;
import com.github.arlekinside.diploma.logic.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/savings")
@RequiredArgsConstructor
public class SavingController extends AbstractCrudController<Saving>{

    private final SavingRepo savingRepo;

    @Override
    public Saving create(Authentication principal, Saving saving) {
        saving.setUser(getUser(principal));
        return savingRepo.save(saving);
    }

    @Override
    public List<Saving> readAll(Authentication auth) {
        return savingRepo.findAllByUser(getUser(auth))
                .stream()
                .sorted((s1, s2) -> s1.getTimeData().getDateCreated().isAfter(s2.getTimeData().getDateCreated()) ? -1 : 1)
                .toList();
    }

    @Override
    public Saving read(Authentication auth, Long id) {
        return savingRepo.findByIdAndUser(id, getUser(auth))
                .orElseThrow(() -> new NotFoundException(id, Saving.class));
    }

    @Override
    public Saving update(Authentication auth, Long id, Saving saving) {
        var existing = read(auth, id);
        existing.setFinished(saving.getFinished());
        existing.setUser(getUser(auth));
        return savingRepo.save(saving);
    }

    @Override
    public void delete(Authentication auth, Long id) {
        //TODO BK get the money back to budget
        savingRepo.deleteByIdAndUser(id, getUser(auth));
    }
}
