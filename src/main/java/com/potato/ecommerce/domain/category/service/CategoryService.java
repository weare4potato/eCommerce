package com.potato.ecommerce.domain.category.service;

import static com.potato.ecommerce.global.exception.ExceptionMessage.ONE_DEPTH_NOT_FOUND;
import static com.potato.ecommerce.global.exception.ExceptionMessage.TWO_DEPTH_NOT_FOUND;

import com.potato.ecommerce.domain.category.dto.OneDepthDto;
import com.potato.ecommerce.domain.category.dto.ThreeDepthDto;
import com.potato.ecommerce.domain.category.dto.TwoDepthDto;
import com.potato.ecommerce.domain.category.entity.OneDepthEntity;
import com.potato.ecommerce.domain.category.entity.TwoDepthEntity;
import com.potato.ecommerce.domain.category.repository.OneDepthRepository;
import com.potato.ecommerce.domain.category.repository.ThreeDepthRepository;
import com.potato.ecommerce.domain.category.repository.TwoDepthRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final OneDepthRepository oneDepthRepository;
    private final TwoDepthRepository twoDepthRepository;
    private final ThreeDepthRepository threeDepthRepository;

    public List<OneDepthDto> getAllOneDepths() {
        return oneDepthRepository.findAll()
            .stream()
            .map(OneDepthDto::of)
            .toList();
    }

    public List<TwoDepthDto> getTwoDepthsByOneDepth(Long oneDepthId) {
        OneDepthEntity oneDepth = oneDepthRepository.findById(oneDepthId)
            .orElseThrow(() -> new EntityNotFoundException(ONE_DEPTH_NOT_FOUND.toString()));
        return twoDepthRepository.findByOneDepth(oneDepth).stream()
            .map(TwoDepthDto::of)
            .toList();
    }

    public List<ThreeDepthDto> getThreeDepthsByTwoDepth(Long twoDepthId) {
        TwoDepthEntity twoDepth = twoDepthRepository.findById(twoDepthId)
            .orElseThrow(() -> new EntityNotFoundException(TWO_DEPTH_NOT_FOUND.toString()));
        return threeDepthRepository.findByTwoDepth(twoDepth)
            .stream()
            .map(ThreeDepthDto::of)
            .toList();
    }

}
