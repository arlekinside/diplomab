package com.github.arlekinside.diploma.logic;

import com.github.arlekinside.diploma.data.DataModule;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.github.arlekinside.diploma.logic")
@EnableAutoConfiguration
@Import(DataModule.class)
public class LogicModule {

}
