import {Component, computed, inject, Input, OnInit, signal} from '@angular/core';
import {TaskComponent} from "./task/task.component";
import {TaskService} from "../../shared/services/task.service";
import {TaskData} from "../../shared/interfaces/task-data";
import {RouterOutlet} from "@angular/router";

@Component({
    selector: 'app-tasks',
    standalone: true,
    imports: [
        TaskComponent,
        RouterOutlet
    ],
    templateUrl: './tasks.component.html',
    styleUrl: './tasks.component.css'
})

export class TasksComponent implements OnInit {
    private taskService = inject(TaskService)
    private allTasks = signal<TaskData[]>([]);
    // private activeTasks = signal<Task[]>(this.allTasks().filter(task => task.status === 'ACTIVE'));
    // private completedTasks = signal<Task[]>(this.allTasks().filter(task => task.status === 'COMPLETED'));
    
    currentTaskType = signal<string>('Active')
    
    tasksToShow: TaskData[] = [];
    
    ngOnInit() {
        this.taskService.getAllTasks().subscribe({
            next: response => {
                this.allTasks.set(response);
                this.showActiveTasks()
            },
            error: error => {
                if (error.status === 0) {
                    alert('Backend server is not running')
                }
                console.log(error)
            }
        })
    }
    
    showActiveTasks() {
        this.tasksToShow = this.allTasks().filter(task => task.status === 'ACTIVE');
        this.currentTaskType.set('Active')
    }
    
    showCompletedTasks() {
        this.tasksToShow = this.allTasks().filter(task => task.status === 'COMPLETED');
        this.currentTaskType.set('Completed')
    }
    
    onCompletedTask(uuid: string) {
        this.allTasks().find(task => task.uuid === uuid)!.status = 'COMPLETED'
        this.showActiveTasks();
    }
}