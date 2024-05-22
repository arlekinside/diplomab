package com.github.arlekinside.diploma.logic.service;

import com.github.arlekinside.diploma.data.entity.Saving;
import com.github.arlekinside.diploma.data.entity.User;
import com.github.arlekinside.diploma.data.entity.mf.MoneyFlow;
import com.github.arlekinside.diploma.ws.dto.DashboardDTO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface AccountingService {


    DashboardDTO analyzeUserData(User user);

    @Transactional(propagation = Propagation.REQUIRED)
    void handleMoneyFlowCreated(MoneyFlow moneyFlow);

    @Transactional(propagation = Propagation.REQUIRED)
    void handleMoneyFlowDeleted(MoneyFlow moneyFlow);

    @Transactional(propagation = Propagation.REQUIRED)
    void handleSavingDeposit(Saving saving);

    void handleSavingCreated(Saving saving);

    @Transactional(propagation = Propagation.REQUIRED)
    void handleSavingDeleted(Saving saving);

    DashboardDTO buildDashboard(Long userId);
}
