package company.controller;

import company.dto.ProjectDTO;
import company.dto.UserDTO;
import company.entity.ResponseWrapper;
import company.entity.User;
import company.mapper.MapperUtil;
import company.service.ProjectService;
import company.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;
    private final MapperUtil mapperUtil;

    public ProjectController(ProjectService projectService, UserService userService, MapperUtil mapperUtil) {
        this.projectService = projectService;
        this.userService = userService;
        this.mapperUtil = mapperUtil;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> projectResponseEntity(){

        List<ProjectDTO> projectDTOList = projectService.listAllProjects();

        return ResponseEntity.ok(new ResponseWrapper("Projects successfully retrieved", projectDTOList, HttpStatus.OK));
    }

    @GetMapping("/{projectCode}")
    public ResponseEntity<ResponseWrapper> getProjectByProjectCode(@PathVariable("projectCode") String projectCode) {
        ProjectDTO projectDTO = projectService.getByProjectCode(projectCode);

        return ResponseEntity.ok(new ResponseWrapper("Project successfully retrieved", projectDTO, HttpStatus.OK));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody ProjectDTO projectDTO){
        projectService.save(projectDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Project successfully retrieved", HttpStatus.CREATED));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper> updateProject(@RequestBody ProjectDTO projectDTO){
        projectService.update(projectDTO);

        return ResponseEntity.ok(new ResponseWrapper("Project successfully updated", HttpStatus.OK));
    }

    @DeleteMapping("/{projectCode}")
    public ResponseEntity<ResponseWrapper> deleteProject(@PathVariable("projectCode") String projectCode){
        projectService.delete(projectCode);

        return ResponseEntity.ok(new ResponseWrapper("Project successfully deleted", HttpStatus.OK));
    }


    @GetMapping("/manager/project-status")
    public ResponseEntity<ResponseWrapper> getProjectByManager(@RequestBody UserDTO userDTO){
        List<ProjectDTO> projects = projectService.listAllProjectDetails();

        return ResponseEntity.ok(new ResponseWrapper("Projects are successfully retrieved", projects, HttpStatus.OK));
    }

    @PutMapping("/manager/complete/{projectCode}")
    public ResponseEntity<ResponseWrapper> managerCompleteProject(@PathVariable("projectCode") String projectCode){

        projectService.complete(projectCode);

        return ResponseEntity.ok(new ResponseWrapper("Projects are successfully completed", HttpStatus.OK));
    }
}
