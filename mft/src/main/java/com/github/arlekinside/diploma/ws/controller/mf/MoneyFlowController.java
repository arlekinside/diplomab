package com.github.arlekinside.diploma.ws.controller.mf;

import com.github.arlekinside.diploma.data.entity.mf.MoneyFlow;
import com.github.arlekinside.diploma.data.repo.mf.MoneyFlowRepo;
import com.github.arlekinside.diploma.logic.exception.NotFoundException;
import com.github.arlekinside.diploma.logic.service.AccountingService;
import com.github.arlekinside.diploma.ws.controller.AbstractCrudController;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class MoneyFlowController<T extends MoneyFlow> extends AbstractCrudController<T> {

    protected final MoneyFlowRepo<T> moneyFlowRepo;
    protected final AccountingService accountingService;

    public MoneyFlowController(MoneyFlowRepo<T> moneyFlowRepo, AccountingService accountingService) {
        this.moneyFlowRepo = moneyFlowRepo;
        this.accountingService = accountingService;
    }


    @Override
    public T create(Authentication principal, T t) {
        t.setUser(getUser(principal));
        return moneyFlowRepo.save(t);
    }

    @Override
    public List<T> readAll(Authentication auth) {
        return moneyFlowRepo.findAllByUser(getUser(auth))
                .stream()
                .sorted((m1, m2) -> m1.getTimeData().getDateCreated().isAfter(m2.getTimeData().getDateCreated()) ? -1 : 1)
                .toList();
    }

    @Override
    public T read(Authentication auth, Long id) {
        return moneyFlowRepo.findByIdAndUser(id, getUser(auth)).orElseThrow(() -> new NotFoundException(id, null));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public T update(Authentication auth, Long id, T t) {
        throw new NotFoundException(null, null);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(Authentication auth, Long id) {
        moneyFlowRepo.deleteByIdAndUser(id, getUser(auth));
    }
}
