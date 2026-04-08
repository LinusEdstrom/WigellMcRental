package com.edstrom.WigellMcRental.service;

import com.edstrom.WigellMcRental.dto.McCreateDto;
import com.edstrom.WigellMcRental.dto.McDto;
import com.edstrom.WigellMcRental.exception.McNotFoundException;
import com.edstrom.WigellMcRental.mapper.McMapper;
import com.edstrom.WigellMcRental.model.Mc;
import com.edstrom.WigellMcRental.repository.McRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class McService {

    private final McRepository mcRepository;

    public McService(McRepository mcRepository) {
        this.mcRepository = mcRepository;
    }

    @Transactional
    public McDto create(McCreateDto dto) {
        Mc entityMc = McMapper.toEntity(dto);
        Mc saved = mcRepository.save(entityMc);
        return McMapper.toDto(saved);
    }
    @Transactional(readOnly = true)
    public List<McDto> findAll() {
        return mcRepository.findAll().stream()
                .map(McMapper::toDto)
                .toList();
    }
    @Transactional(readOnly = true)
    public McDto findById(Long id){
        return mcRepository.findById(id)
                .map(McMapper::toDto)
                .orElseThrow(()-> new McNotFoundException(id));
    }

    @Transactional
    public void delete(Long id) {
       Mc mcDelete = mcRepository.findById(id)
               .orElseThrow(() -> new McNotFoundException(id));

       if(mcDelete.getBookings() != null){
           mcDelete.getBookings().forEach(booking -> booking.getMcs().remove(mcDelete));
        }
        mcRepository.deleteById(id);
    }

}
