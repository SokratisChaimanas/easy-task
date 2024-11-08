import {inject, Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs";
import {AddingTaskData} from "../interfaces/task-data";
import {AddTaskResponse, TaskCompletionResponse, TaskListResponse} from "../interfaces/responses";

@Injectable({
    providedIn: 'root'
})

export class TaskService {
    private http = inject(HttpClient)
    
    private API_URL_TASKS = 'http://localhost:8080/tasks'
    
    getAllTasks() {
        return this.http.get<TaskListResponse>(`${this.API_URL_TASKS}`).pipe(
            map(response => response.data)
        )
    }
    
    completeTask(uuid:string) {
        return this.http.put<TaskCompletionResponse>(`${this.API_URL_TASKS}/complete/${uuid}`, {})
    }
    
    addTask(task: AddingTaskData) {
        return this.http.post<AddTaskResponse>(`${this.API_URL_TASKS}`, task)
    }
}