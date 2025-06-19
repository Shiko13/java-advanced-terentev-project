package ru.otus.service;

import com.netflix.discovery.EurekaClient;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.exception.AccessException;
import ru.otus.exception.ErrorMessageConstants;
import ru.otus.mapper.TrainingMapper;
import ru.otus.model.Training;
import ru.otus.model.dto.*;
import ru.otus.repo.TraineeRepo;
import ru.otus.repo.TrainerRepo;
import ru.otus.repo.TrainingRepo;
import ru.otus.spec.TrainingTraineeSpecification;
import ru.otus.spec.TrainingTrainerSpecification;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepo trainingRepo;

    private final TraineeRepo traineeRepo;

    private final TrainerRepo trainerRepo;

    private final TrainingMapper trainingMapper;

    private final JmsTemplate jmsTemplate;

    private final EurekaClient eurekaClient;

    private final TokenExtractorService tokenExtractorService;

    @Override
    @Transactional
    public Training save(TrainingDtoInput trainingDtoInput, HttpServletRequest request) {
        log.info("save, trainingDtoInput = {}", trainingDtoInput);

        var trainingToSave = trainingMapper.toEntity(trainingDtoInput);
        var trainee = traineeRepo.findByUser_Username(trainingDtoInput.getTraineeUsername())
                .orElseThrow(() -> new AccessException(ErrorMessageConstants.ACCESS_ERROR_MESSAGE));
        var trainer = trainerRepo.findByUser_Username(trainingDtoInput.getTrainerUsername())
                .orElseThrow(() -> new AccessException(ErrorMessageConstants.ACCESS_ERROR_MESSAGE));

        trainingToSave.setTrainee(trainee);
        trainingToSave.setTrainer(trainer);

        var savedTraining = trainingRepo.save(trainingToSave);

        sendRequest(savedTraining, ActionType.POST);

        return savedTraining;
    }

    @Override
    @Timed("findByDateRangeAndTraineeUsernameTime")
    public List<TrainingForTraineeDtoOutput> findByDateRangeAndTraineeUsername(
            TrainingTraineeSpecification specification) {

        log.info("findByDateRangeAndTraineeUsername, specification = {}", specification);

        var trainings = trainingRepo.findAll(specification);

        return trainingMapper.toTrainingForTraineeDtoList(trainings);
    }

    @Override
    @Timed("findByDateRangeAndTrainerUsernameTime")
    public List<TrainingForTrainerDtoOutput> findByDateRangeAndTrainerUsername(
            TrainingTrainerSpecification specification) {
        log.info("findByDateRangeAndTrainerUsername, specification = {}", specification);

        var trainings = trainingRepo.findAll(specification);

        return trainingMapper.toTrainingForTrainerDtoList(trainings);
    }

    @Override
    @Transactional
    public void deleteById(Long id, HttpServletRequest request) {
        log.info("deleteById, id = {}", id);

        var training = getTrainingById(id);

        trainingRepo.delete(training);

        sendRequest(training, ActionType.DELETE);
    }

    private Training getTrainingById(Long id) {
        return trainingRepo.findById(id)
                .orElseThrow(() -> new AccessException(ErrorMessageConstants.NOT_FOUND_MESSAGE));
    }

    public void sendRequest(Training training, ActionType actionType) {
        TrainerWorkloadDto trainerDto = createTrainerDto(training, actionType);
        jmsTemplate.convertAndSend("trainer-workload-queue", trainerDto);
    }

    private TrainerWorkloadDto createTrainerDto(Training training, ActionType actionType) {
        return TrainerWorkloadDto.builder()
                .username(training.getTrainer().getUser().getUsername())
                .firstName(training.getTrainer().getUser().getFirstName())
                .lastName(training.getTrainer().getUser().getLastName())
                .isActive(training.getTrainer().getUser().getIsActive())
                .date(training.getDate())
                .duration(training.getDuration())
                .actionType(actionType)
                .build();
    }
}
