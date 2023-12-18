package com.relax.relax.common.controller;

import com.relax.relax.common.annotation.MappingType;
import com.relax.relax.common.domain.RelaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
public class BaseController<T> {

    @MappingType(RequestMethod.POST)
    @ResponseBody
    public RelaxResult add(@RequestBody T entity){
        log.info("execute add method ,requestBody is {}",entity);
        return RelaxResult.success();
    }

    @MappingType(RequestMethod.POST)
    @ResponseBody
    public RelaxResult update(@RequestBody T entity){
        log.info("execute update method ,requestBody is {}",entity);
        return RelaxResult.success();
    }

    @MappingType(RequestMethod.POST)
    @ResponseBody
    public RelaxResult delete(@RequestBody T entity){
        log.info("execute delete method ,requestBody is {}",entity);
        return RelaxResult.success();
    }

    @MappingType(RequestMethod.GET)
    @ResponseBody
    public RelaxResult info(@RequestParam T entity){
        log.info("execute info method ,RequestParam is {}",entity);
        return RelaxResult.success();
    }

    @MappingType(RequestMethod.POST)
    @ResponseBody
    public RelaxResult page(@RequestBody T entity){
        log.info("execute page method ,requestBody is {}",entity);
        return RelaxResult.success();
    }

}
