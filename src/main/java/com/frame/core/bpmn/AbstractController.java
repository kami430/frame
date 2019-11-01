package com.frame.core.bpmn;


import org.camunda.bpm.engine.*;

public abstract class AbstractController {

    protected ProcessEngine processEngine;
    protected RepositoryService repositoryService;
    protected RuntimeService runtimeService;
    protected TaskService taskService;
    protected HistoryService historyService;
    protected ManagementService managementService;


    public AbstractController() {
        super();
        processEngine = ProcessEngines.getDefaultProcessEngine();
        repositoryService = processEngine.getRepositoryService();
        runtimeService = processEngine.getRuntimeService();
        taskService = processEngine.getTaskService();
        historyService = processEngine.getHistoryService();
        managementService = processEngine.getManagementService();
    }
}
