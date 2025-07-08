package ru.otus.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.otus.model.dto.TrainerWorkloadDto;
import ru.otus.service.TrainerService;

@Component
@RequiredArgsConstructor
public class JMSListener {

    private final TrainerService trainerService;

    @JmsListener(destination = "trainer-workload-queue")
    public void handleMessage(TrainerWorkloadDto trainerDto) {

        //This part is needed for demonstration of dead letter queue
        //        if (trainerDto.getDuration() > 10) {
//            throw new RuntimeException("Test");
//        }
        trainerService.save(trainerDto);
    }
}