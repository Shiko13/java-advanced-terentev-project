package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.model.Trainer;
import ru.otus.model.dto.ActionType;
import ru.otus.model.dto.TrainerWorkloadDto;

import java.util.HashMap;
import java.util.Map;

@Service
public class TrainerServiceImpl implements TrainerService {

    Map<String, Trainer> sum = new HashMap<>();

    @Override
    public void save(TrainerWorkloadDto trainerWorkloadDto) {

        if (trainerWorkloadDto.getActionType().equals(ActionType.DELETE)) {
            trainerWorkloadDto.setDuration(-trainerWorkloadDto.getDuration());
        }

        Trainer trainer;
        Integer year = trainerWorkloadDto.getDate().getYear();
        Integer month = trainerWorkloadDto.getDate().getMonthValue();

        if (sum.get(trainerWorkloadDto.getUsername()) != null) {
            trainer = sum.get(trainerWorkloadDto.getUsername());

            updateDuration(trainerWorkloadDto, trainer, year, month);
        } else {
            trainer = createTrainer(trainerWorkloadDto, month, year);
        }

        sum.put(trainerWorkloadDto.getUsername(), trainer);
    }

    private static Trainer createTrainer(TrainerWorkloadDto trainerWorkloadDto, Integer month, Integer year) {
        Trainer trainer;
        Map<Integer, Long> months = new HashMap<>();
        months.put(month, trainerWorkloadDto.getDuration());
        Map<Integer, Map<Integer, Long>> years = new HashMap<>();
        years.put(year, months);

        trainer = Trainer.builder()
                .username(trainerWorkloadDto.getUsername())
                .firstName(trainerWorkloadDto.getFirstName())
                .lastName(trainerWorkloadDto.getLastName())
                .isActive(trainerWorkloadDto.getIsActive())
                .duration(years)
                .build();
        return trainer;
    }

    private static void updateDuration(TrainerWorkloadDto trainerWorkloadDto, Trainer trainer, Integer year,
                                       Integer month) {
        trainer.getDuration()
                .computeIfAbsent(year, k -> new HashMap<>())
                .merge(month, trainerWorkloadDto.getDuration(), Long::sum);
    }
}
