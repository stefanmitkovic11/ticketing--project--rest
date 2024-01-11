package company.controller;

import company.dto.ProjectDTO;
import company.dto.UserDTO;
import company.entity.ResponseWrapper;
import company.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
@Tag(name = "ProjectController", description = "Project API")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    @RolesAllowed({"Admin", "Manager"})
    @Operation(summary = "GET Projects")
    public ResponseEntity<ResponseWrapper> projectResponseEntity(){

        List<ProjectDTO> projectDTOList = projectService.listAllProjects();

        return ResponseEntity.ok(new ResponseWrapper("Projects successfully retrieved", projectDTOList, HttpStatus.OK));
    }

    @GetMapping("/{projectCode}")
    @RolesAllowed("Manager")
    @Operation(summary = "GET Project by project code")
    public ResponseEntity<ResponseWrapper> getProjectByProjectCode(@PathVariable("projectCode") String projectCode) {
        ProjectDTO projectDTO = projectService.getByProjectCode(projectCode);

        return ResponseEntity.ok(new ResponseWrapper("Project successfully retrieved", projectDTO, HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed("Manager")
    @Operation(summary = "POST Project")
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody ProjectDTO projectDTO){
        projectService.save(projectDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Project successfully retrieved", HttpStatus.CREATED));
    }

    @PutMapping
    @RolesAllowed("Manager")
    @Operation(summary = "PUT Project")
    public ResponseEntity<ResponseWrapper> updateProject(@RequestBody ProjectDTO projectDTO){
        projectService.update(projectDTO);

        return ResponseEntity.ok(new ResponseWrapper("Project successfully updated", HttpStatus.OK));
    }

    @DeleteMapping("/{projectCode}")
    @RolesAllowed("Manager")
    @Operation(summary = "DELETE Project")
    public ResponseEntity<ResponseWrapper> deleteProject(@PathVariable("projectCode") String projectCode){
        projectService.delete(projectCode);

        return ResponseEntity.ok(new ResponseWrapper("Project successfully deleted", HttpStatus.OK));
    }


    @GetMapping("/manager/project-status")
    @RolesAllowed("Manager")
    @Operation(summary = "GET Project by Manager")
    public ResponseEntity<ResponseWrapper> getProjectByManager(@RequestBody UserDTO userDTO){
        List<ProjectDTO> projects = projectService.listAllProjectDetails();

        return ResponseEntity.ok(new ResponseWrapper("Projects are successfully retrieved", projects, HttpStatus.OK));
    }

    @PutMapping("/manager/complete/{projectCode}")
    @RolesAllowed("Manager")
    @Operation(summary = "PUT Project complete")
    public ResponseEntity<ResponseWrapper> managerCompleteProject(@PathVariable("projectCode") String projectCode){

        projectService.complete(projectCode);

        return ResponseEntity.ok(new ResponseWrapper("Projects are successfully completed", HttpStatus.OK));
    }
}
