package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        String questionsFileName = fileNameProvider.getTestFileName();
        List<QuestionDto> questionDtoList = getQuestionsFromCsv(questionsFileName);
        return questionDtoList.stream()
                .map(QuestionDto::toDomainObject)
                .toList();
    }


    private List<QuestionDto> getQuestionsFromCsv(String questionsFileName) {
        String errMsg = "Can't read the file: " + questionsFileName;

        InputStream inputStream = (getClass().getClassLoader().getResourceAsStream(questionsFileName));
        if (inputStream == null) {
            throw new QuestionReadException(errMsg, null);
        }

        try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return new CsvToBeanBuilder<QuestionDto>(reader)
                    .withSeparator(';')
                    .withIgnoreQuotations(true)
                    .withSkipLines(1) // header is a comment
                    .withType(QuestionDto.class)
                    .build()
                    .parse();
        } catch (IOException ioE) {
            throw new QuestionReadException(errMsg, ioE);
        }
    }

}
