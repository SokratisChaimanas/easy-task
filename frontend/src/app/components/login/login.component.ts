import {Component, inject, OnInit, output, signal} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {SuccessComponent} from "../success/success.component";

import {ErrorComponent} from "../error/error.component";
import {AuthService} from "../../shared/services/auth.service";

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [
        RouterLink,
        FormsModule,
        SuccessComponent,
        ErrorComponent,
        ReactiveFormsModule
    ],
    templateUrl: './login.component.html',
    styleUrl: './login.component.css'
})

export class LoginComponent implements OnInit{
    
    route = inject(ActivatedRoute);
    router = inject(Router)
    authService = inject(AuthService)
    
    wasRegistered = signal<boolean>(false);
    areCredentialsInvalid = signal<boolean>(false);
    // loggedInSuccessfully = output<boolean>();
    
    form = new FormGroup({
        username: new FormControl(''),
        password: new FormControl('')
    });
    
    ngOnInit(): void {
        this.route.queryParamMap.subscribe(params => {
            if (params.get('was-registered')) this.wasRegistered.set(true)
            this.areCredentialsInvalid.set(params.get('invalid-credentials') === 'true')
        });
    }
    
    onSubmit() {
        const credentials = {
            username: this.form.value.username!,
            password: this.form.value.password!
        }
        
        this.authService.loginUser(credentials).subscribe({
            next: response => {
                console.log(response);
                this.authService.storeToken(response);
                this.authService.loggedInUser.set(this.authService.decodeToken(response));
                this.router.navigate(['tasks']);
            },
            error: error => {
                if (error.status === 400) {
                    console.log(error);
                    this.router.navigate(['login'], {queryParams: {'invalid-credentials': true}});
                }
            }
        });
    }
}
