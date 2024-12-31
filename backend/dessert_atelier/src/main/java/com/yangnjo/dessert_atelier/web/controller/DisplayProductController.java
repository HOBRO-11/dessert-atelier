package com.yangnjo.dessert_atelier.web.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yangnjo.dessert_atelier.common.page_util.PageOption;
import com.yangnjo.dessert_atelier.common.page_util.PageResponse;
import com.yangnjo.dessert_atelier.domain_model.product.DisplayProductStatus;
import com.yangnjo.dessert_atelier.repository.product.dto.DpDto;
import com.yangnjo.dessert_atelier.repository.product.dto.DpSimpleDto;
import com.yangnjo.dessert_atelier.service.product.DisplayProductService;
import com.yangnjo.dessert_atelier.service.product.dto.DisplayProductEntityCreateForm;
import com.yangnjo.dessert_atelier.service.product.dto.DisplayProductEntityUpdateForm;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/display_product")
@RequiredArgsConstructor
public class DisplayProductController {

    private final DisplayProductService dpService;

    @PostMapping
    public ResponseEntity<Long> create(DisplayProductEntityCreateForm form) {
        Long dpId = dpService.create(form);
        return new ResponseEntity<>(dpId, HttpStatus.CREATED);
    }

    @GetMapping("/{dpId}")
    public ResponseEntity<DpDto> getMethodName(@PathVariable Long dpId) {
        Optional<DpDto> optionalDp = dpService.getById(dpId);

        if (optionalDp.isPresent()) {
            return new ResponseEntity<>(optionalDp.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping()
    public ResponseEntity<Page<DpSimpleDto>> getAll(
            @RequestParam(value = "status", required = true) DisplayProductStatus status,
            PageOption pageOption) {
        if (status == null) {
            status = DisplayProductStatus.ON_SALE;
        }

        return PageResponse.resp(dpService.getAllSimpleByDpStatus(status, pageOption));
    }

    @GetMapping("/except")
    public ResponseEntity<Page<DpSimpleDto>> getAllExceptStatus(
            @RequestParam(value = "status", required = true) DisplayProductStatus status,
            PageOption pageOption) {
        if (status == null) {
            status = DisplayProductStatus.HIDE;
        }

        return PageResponse.resp(dpService.getAllSimpleByExceptDpStatus(status, pageOption));
    }

    @PutMapping("/thumb/{dpId}")
    public void updateThumbs(@PathVariable Long dpId, DisplayProductEntityUpdateForm form) {
        form.setDpId(dpId);
        dpService.updateThumbs(form);
    }

    @PutMapping("/desc/{dpId}")
    public void updateDescription(@PathVariable Long dpId, DisplayProductEntityUpdateForm form) {
        form.setDpId(dpId);
        dpService.updateDescription(form);
    }

    @PutMapping("/option_layer/{dpId}")
    public void updateOptionLayer(@PathVariable Long dpId, DisplayProductEntityUpdateForm form) {
        form.setDpId(dpId);
        dpService.updateOptionLayer(form);
    }

    @PutMapping("/status/{dpId}")
    public void updateDisplayProductStatus(@PathVariable Long dpId, DisplayProductStatus status) {
        dpService.updateDisplayProductStatus(dpId, status);
    }

}
