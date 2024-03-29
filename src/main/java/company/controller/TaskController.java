package company.controller;

import company.dto.TaskDTO;
import company.entity.ResponseWrapper;
import company.enums.Status;
import company.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@Tag(name = "TaskController", description = "Task API")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @GetMapping
    @RolesAllowed("Manager")
    @Operation(summary = "GET Tasks")
    public ResponseEntity<ResponseWrapper> taskResponseEntity() {

        List<TaskDTO> taskDTOList = taskService.listAllTasks();


        return ResponseEntity.ok(new ResponseWrapper("Tasks are successfully retrieved", taskDTOList, HttpStatus.OK));
    }


    @GetMapping("/{id}")
    @RolesAllowed("Manager")
    @Operation(summary = "GET Task by id")
    public ResponseEntity<ResponseWrapper> getTaskById(@PathVariable("id") Long id) {
        TaskDTO taskDTO = taskService.findById(id);


        return ResponseEntity.ok(new ResponseWrapper("Task is successfully retrieved", taskDTO, HttpStatus.OK));
    }


    @PostMapping
    @RolesAllowed("Manager")
    @Operation(summary = "POST Task")
    public ResponseEntity<ResponseWrapper> createTask(@RequestBody TaskDTO taskDTO) {
        taskService.save(taskDTO);


        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Task is successfully created", HttpStatus.CREATED));
    }


    @DeleteMapping("/{id}")
    @RolesAllowed("Manager")
    @Operation(summary = "DELETE Task")
    public ResponseEntity<ResponseWrapper> deleteTask(@PathVariable("id") Long id) {
        taskService.delete(id);

        return ResponseEntity.ok(new ResponseWrapper("Task is successfully deleted", HttpStatus.OK));
    }

    @PutMapping()
    @RolesAllowed("Manager")
    @Operation(summary = "PUT Task")
    public ResponseEntity<ResponseWrapper> updateTask(@RequestBody TaskDTO taskDTO){
        taskService.update(taskDTO);

        return ResponseEntity.ok(new ResponseWrapper("Task is successfully updated", HttpStatus.OK));
    }

    @GetMapping("/employee/pending-tasks")
    @RolesAllowed("Employee")
    @Operation(summary = "GET Pending Tasks")
    public ResponseEntity<ResponseWrapper> employeePendingTasks() {

       List<TaskDTO> taskDTOList = taskService.listAllTasksByStatusIsNot(Status.COMPLETE);


       return ResponseEntity.ok(new ResponseWrapper("Task is successfully retrieved", taskDTOList, HttpStatus.OK));
    }

    @PutMapping("/employee/update")
    @RolesAllowed("Employee")
    @Operation(summary = "PUT Update Task status")
    public ResponseEntity<ResponseWrapper> employeeUpdatedTasks(@RequestBody TaskDTO taskDTO){
        taskService.updateStatus(taskDTO);


        return ResponseEntity.ok(new ResponseWrapper("Task status is successfully updated", HttpStatus.OK));
    }

    @GetMapping("/employee/archive")
    @RolesAllowed("Employee")
    @Operation(summary = "GET Archived Tasks")
    public ResponseEntity<ResponseWrapper> employeeArchivedTasks(){
        List<TaskDTO> taskDTOListArchived = taskService.listAllTasksByStatus(Status.COMPLETE);

        return ResponseEntity.ok(new ResponseWrapper("Archived tasks are successfully retrieved", taskDTOListArchived, HttpStatus.OK));
    }


}
