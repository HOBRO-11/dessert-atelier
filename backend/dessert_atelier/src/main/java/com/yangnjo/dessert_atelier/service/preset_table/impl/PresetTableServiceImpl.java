package com.yangnjo.dessert_atelier.service.preset_table.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yangnjo.dessert_atelier.domain_service.product.PresetTableCommandService;
import com.yangnjo.dessert_atelier.domain_service.product.PresetTableQueryService;
import com.yangnjo.dessert_atelier.repository.product.dto.PresetTableDto;
import com.yangnjo.dessert_atelier.service.preset_table.PresetTableService;
import com.yangnjo.dessert_atelier.service.preset_table.dto.PresetTableUpdateForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PresetTableServiceImpl implements PresetTableService {

    private final PresetTableQueryService presetTableQueryService;
    private final PresetTableCommandService presetTableCommandService;

    @Override
    public List<PresetTableDto> getAllPresetTables() {
        return presetTableQueryService.getAllPresetTables().stream()
                .sorted(Comparator.comparing(PresetTableDto::getNumbering))
                .collect(Collectors.toList());
    }

    /**
     * @param forms
     * @throws IllegalArgumentException 중복된 순서를 가진 테이블이 있을 경우
     */
    @Override
    @Transactional
    public void update(List<PresetTableUpdateForm> forms) {
        checkDuplicate(forms);
        Map<Long, PresetTableDto> storedDpIdAndDto = getDpIdAndDtoMap();

        List<PresetTableUpdateForm> formsToCreate = new ArrayList<>();
        Map<Long, Integer> IdAndNumberingToUpdate = new HashMap<>();

        forms.forEach(form -> {
            if (storedDpIdAndDto.containsKey(form.getDpId())) {
                PresetTableDto presetTableDto = storedDpIdAndDto.get(form.getDpId());
                if (form.getNumbering() != presetTableDto.getNumbering()) {
                    IdAndNumberingToUpdate.put(presetTableDto.getId(), form.getNumbering());
                }
                storedDpIdAndDto.remove(form.getDpId());
            } else {
                formsToCreate.add(form);
            }
        });

        formsToCreate.forEach(form -> presetTableCommandService.create(form.toDto()));
        IdAndNumberingToUpdate.forEach((id, numbering) -> presetTableCommandService.updateNumbering(id, numbering));
        storedDpIdAndDto.forEach((dpId, dto) -> presetTableCommandService.delete(dto.getId()));
    }

    /**
     * @param forms
     * @throws IllegalArgumentException 중복된 순서를 가진 테이블이 있을 경우
     */
    private void checkDuplicate(List<PresetTableUpdateForm> forms) {
        boolean hasDuplicate = forms.stream()
                .map(PresetTableUpdateForm::getNumbering)
                .collect(Collectors.groupingBy(number -> number, Collectors.counting()))
                .values()
                .stream()
                .anyMatch(count -> count > 1);

        if (hasDuplicate) {
            throw new IllegalArgumentException("중복된 순서를 가진 테이블이 있습니다.");
        }
    }

    private Map<Long, PresetTableDto> getDpIdAndDtoMap() {
        return presetTableQueryService.getAllPresetTables().stream()
                .collect(Collectors.toMap(PresetTableDto::getDpId, dto -> dto));
    }

}
