package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import ru.otus.spring.configs.AppProps;
import ru.otus.spring.exceptions.ExamException;
import ru.otus.spring.services.exam.Examiner;

@ShellComponent
@EnableConfigurationProperties(AppProps.class)
@RequiredArgsConstructor
public class AppShell {

    private static final Logger logger = LoggerFactory.getLogger(AppShell.class);

    private final AppProps appProps;

    private boolean accessGranted;

    private final Examiner examiner;

    @ShellMethod(value = "Access grant command", key = { "sudo", "su" })
    public String grantAccess(@ShellOption(defaultValue = "root") final String password) {
        accessGranted = password.equals(appProps.getSuperUserPassword());
        return accessGranted ? "Access granted!" : "Access denied!";
    }

    @ShellMethod(value = "Start exam command", key = {"e", "exam", "start-exam"})
    @ShellMethodAvailability(value = "isStartExamCommandAvailable")
    public void startExam() {
        try {
            examiner.exam();
        } catch (final ExamException e) {
            logger.error("Ошибка на экзамене: ", e);
        }
    }

    private Availability isStartExamCommandAvailable() {
        return accessGranted ? Availability.available() : Availability.unavailable("Access denied! You have to get access first!");
    }
}
