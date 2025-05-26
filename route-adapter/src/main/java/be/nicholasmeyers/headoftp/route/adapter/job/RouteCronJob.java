package be.nicholasmeyers.headoftp.route.adapter.job;

import be.nicholasmeyers.headoftp.route.usecase.SendVirtualGhostUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*@RequiredArgsConstructor
@Component
public class RouteCronJob {

    private final SendVirtualGhostUseCase sendVirtualGhostUseCase;

    @Scheduled(cron = "* * * * * *")
    public void cronJob() {
        sendVirtualGhostUseCase.sendVirtualGhost();
    }

}*/
