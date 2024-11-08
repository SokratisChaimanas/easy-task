import {Component, inject} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {TaskService} from "../../../shared/services/task.service";
import {AddingTaskData} from "../../../shared/interfaces/task-data";
import {ErrorComponent} from "../../error/error.component";

@Component({
    selector: 'app-add-task',
    standalone: true,
    imports: [
        FormsModule,
        ReactiveFormsModule,
        ErrorComponent
    ],
    templateUrl: './add-task.component.html',
    styleUrl: './add-task.component.css'
})
export class AddTaskComponent {
    router = inject(Router);
    taskService = inject(TaskService);
    
    form = new FormGroup({
        title: new FormControl('', {
            validators: [
                Validators.required,
                Validators.minLength(5),
                Validators.maxLength(255)
            ],
            updateOn: 'change'
        }),
        description: new FormControl('', {
            validators: [Validators.required],
            updateOn: 'change'
        }),
        dueDate: new FormControl('', {
            validators: [
                Validators.required,
                this.dateValidator
            ],
            updateOn: 'change'
        })
    });
    
    errorMessages = {
        title: {
            required: 'Title is required',
            invalid: 'Title must be between 5-255 characters long'
        },
        description: {
            required: 'Description is required'
        },
        dueDate: {
            required: 'Due date is required',
            invalid: 'Due date cannot be before today'
        }
    }
    
    onSubmit() {
        this.trimValues();
        this.form.markAllAsTouched();
        this.form.updateValueAndValidity();
        
        if (this.form.invalid) {
            return;
        }
        
        const taskToAdd: AddingTaskData = {
            title: this.form.value.title!,
            description: this.form.value.description!,
            dueDate: this.form.value.dueDate!
        }
        
        this.taskService.addTask(taskToAdd).subscribe({
            next: response => {
                console.log(response)
                this.router.navigate(['/tasks'])
            },
            error: error => {
                console.log(error)
                alert('Unexpected Error. Please try again')
            }
        })
    }
    
    onCancel() {
        this.router.navigate(['/tasks'])
    }
    
    dateValidator(control: AbstractControl): { [key: string]: boolean } | null{
        const value = control.value;
        
        if (!value) {
            return null;
        }
        
        const inputDate = new Date(value);
        const today = new Date();
        today.setHours(0,0,0,0);
        
        if (inputDate < today) {
            return {dateError: true};
        }
        return null;
    }
    
    trimValues() {
        const title = this.form.get('title')?.value;
        const description = this.form.get('description')?.value
        
        this.form.get('title')?.setValue(title!.trim())
        this.form.get('description')?.setValue(description!.trim())
    }
}
