package com.newspush.newspush.loader;

import com.newspush.newspush.domain.entity.User;
import com.newspush.newspush.domain.enums.PushType;
import com.newspush.newspush.domain.enums.RssCategory;
import com.newspush.newspush.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExcelDataLoader implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (userRepository.count() > 0) {
            log.info("이미 유저 데이터 존재함.");
            return;
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook(
                new ClassPathResource("users.xlsx").getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            List<User> users = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                String categories = row.getCell(4).getStringCellValue();

                User user = User.builder()
                        .name(row.getCell(1).getStringCellValue())
                        .deviceId(row.getCell(2).getStringCellValue())
                        .pushType(PushType.from(row.getCell(3).getStringCellValue()))
                        .dndTime(row.getCell(5).getStringCellValue())
                        .build();

                Arrays.stream(categories.split(","))
                        .map(String::trim)
                        .map(RssCategory::valueOf)
                        .forEach(user::addCategory);
                users.add(user);
            }

            userRepository.saveAll(users);
            log.info("유저 데이터 로딩 완료.");

        } catch (Exception e) {
            log.error("엑셀 파싱 실패", e);
        }

    }
}
