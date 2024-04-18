package com.pagacz.gateway.application.scheduler;

import com.pagacz.database.service.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TaskScheduler {
    private static final Logger log = LoggerFactory.getLogger(TaskScheduler.class);
    private final TaskService taskService;

    @Autowired
    public TaskScheduler(OfferService offerService, TaskService taskService) {
        this.taskService = taskService;
    }

    @Scheduled(cron = "0 */10 * ? * *")
    public void sendOffersByEmail() {
        log.info("Scheduled task scrapOffersAndSendEmail started.");
        taskService.sendEmailWithOffers();
        log.info("Scheduled task scrapOffersAndSendEmail finished.");
    }

    @Scheduled(cron = "0 */5 6-23 ? * *")
    public void taskWriteOffersToGoogleSheets() {
        log.info("Scheduled task writeOffersToGoogleSheets started.");
        taskService.addOffersToGoogleSheet();
        log.info("Scheduled task writeOffersToGoogleSheets finished.");
    }

}
