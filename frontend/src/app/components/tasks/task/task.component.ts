import {Component, inject, Input, output} from '@angular/core';
import {TaskData} from "../../../shared/interfaces/task-data";
import {DatePipe} from "@angular/common";
import {TaskService} from "../../../shared/services/task.service";

@Component({
    selector: 'app-task',
    standalone: true,
    imports: [
        DatePipe
    ],
    templateUrl: './task.component.html',
    styleUrl: './task.component.css'
})
export class TaskComponent {
    @Input({required:true}) task!: TaskData
    
    completed = output<string>()
    
    taskService = inject(TaskService)
    
    onTaskCompleted() {
       this.taskService.completeTask(this.task.uuid).subscribe({
           next: response => {
               console.log(response.data);
               this.completed.emit(this.task.uuid);
           },
           error: error => {
               console.log(error)
           }
       })
    }
}
