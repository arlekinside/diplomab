package com.github.arlekinside.diploma.ws;

import com.github.arlekinside.diploma.logic.LogicModule;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(LogicModule.class)
class WSModule {
}
