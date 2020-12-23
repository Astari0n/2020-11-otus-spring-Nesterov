package ru.otus.spring.services.io;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import ru.otus.spring.exceptions.IOServiceException;
import ru.otus.spring.services.localization.MessageLocalizationService;

@Service
@RequiredArgsConstructor
public class MessageIOServiceImpl implements MessageIOService {

    private final IOService io;

    private final MessageLocalizationService localizationService;

    @Override
    public void printMsg(final String key, final Object... args) throws IOServiceException {
        io.print(localizationService.getText(key, args));
    }

    @Override
    public void printlnMsg(final String key, final Object... args) throws IOServiceException {
        io.println(localizationService.getText(key, args));
    }

    @Override
    public void println() throws IOServiceException {
        io.println();
    }

    @Override
    public String readMsg(final String key, final Object... args) {
        return localizationService.getText(key, args);
    }
}
