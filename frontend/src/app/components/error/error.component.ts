import {Component, Input, input} from '@angular/core';

@Component({
    selector: 'app-error',
    standalone: true,
    imports: [],
    templateUrl: './error.component.html',
    styleUrl: './error.component.css'
})

export class ErrorComponent {
    @Input({required: true}) errorMessage!: string;
}
