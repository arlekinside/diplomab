package com.github.arlekinside.diploma.ws.controller;

import com.github.arlekinside.diploma.data.entity.Saving;
import com.github.arlekinside.diploma.data.repo.SavingRepo;
import com.github.arlekinside.diploma.logic.exception.BadRequestException;
import com.github.arlekinside.diploma.logic.exception.NotFoundException;
import com.github.arlekinside.diploma.logic.service.AccountingService;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/savings")
public class SavingController extends AbstractCrudController<Saving>{

    private final SavingRepo savingRepo;
    private final AccountingService accountingService;

    protected SavingController(SavingRepo savingRepo, AccountingService accountingService) {
        this.savingRepo = savingRepo;
        this.accountingService = accountingService;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {BadRequestException.class})
    public Saving create(Authentication principal, Saving saving) {
        saving.setUser(getUser(principal));
        var sav = savingRepo.save(saving);
        accountingService.handleSavingCreated(saving);
        return sav;
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Saving update(Authentication auth, Long id, Saving saving) {
        var existing = read(auth, id);
        existing.setFinished(saving.getFinished());
        return savingRepo.save(existing);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {BadRequestException.class})
    public void delete(Authentication auth, Long id) {
        accountingService.handleSavingDeleted(read(auth, id));
        savingRepo.deleteByIdAndUser(id, getUser(auth));
    }
}
