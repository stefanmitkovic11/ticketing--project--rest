package company.service.impl;

import company.dto.RoleDTO;
import company.mapper.MapperUtil;
import company.mapper.RoleMapper;
import company.repository.RoleRepository;
import company.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final MapperUtil mapperUtil;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper, MapperUtil mapperUtil) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<RoleDTO> listAllRoles() {
//        return roleRepository.findAll().stream().map(roleMapper::convertToDto).collect(Collectors.toList());
        return roleRepository.findAll().stream().map(role -> mapperUtil.convert(role, new RoleDTO())).collect(Collectors.toList());
    }

    @Override
    public RoleDTO findById(Long id) {
//        return roleMapper.convertToDto(roleRepository.findById(id).get());
        return mapperUtil.convert(roleRepository.findById(id).get(), new RoleDTO());
    }
}
