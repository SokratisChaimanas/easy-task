import {Component, DestroyRef, inject} from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {AbstractControl, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {AuthService} from '../../shared/services/auth.service';
import {ErrorComponent} from '../error/error.component';
import {UserRegisterData} from "../../shared/interfaces/user-data";
import {firstValueFrom} from "rxjs";

@Component({
    selector: 'app-signup',
    standalone: true,
    imports: [
        RouterLink,
        FormsModule,
        ErrorComponent,
        ReactiveFormsModule
    ],
    templateUrl: './signup.component.html',
    styleUrls: ['./signup.component.css']
})

export class SignupComponent {
    authService = inject(AuthService);      // Service responsible for user related requests
    router = inject(Router);
    destroyRef = inject(DestroyRef)
    
    form = new FormGroup({
            username: new FormControl('', {
                    validators: [
                        Validators.required,
                        Validators.minLength(3),
                        Validators.maxLength(20)
                    ],
                    updateOn: 'blur'
                },
            ),
            password: new FormControl('', {
                    validators: [
                        Validators.required
                        ,
                        Validators.pattern('^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$')
                    ],
                    updateOn: 'blur'
                }
            ),
            confirmPassword: new FormControl('',)
        },
        {
            validators: this.passwordsMatchValidator,
            updateOn: 'change'
        })
    
    errorMessages = {
        username: {
            required: 'Username is required',
            invalid: 'Username must be between 3-20 characters longs',
            inUse: 'Username is already in use'
        },
        password: {
            required: 'Password is required',
            invalid: 'Password must contain at least 1 letter, 1 number, 1 symbol and have 8 characters'
        },
        general: {
            passwordMismatch: 'Confirm password does not match'
        }
    };
    
    
    onSubmit() {
        this.trimValues();
        this.form.markAllAsTouched();
        this.form.updateValueAndValidity();
        
        if (this.form.invalid) {
            return
        }
        
        const userData: UserRegisterData = {
            username: this.form.value.username!,
            password: this.form.value.password!,
            confirmPassword: this.form.value.confirmPassword!
        }
        
        const subscription = this.authService.registerUser(userData).subscribe({
            next: response => {
                this.router.navigate(['login'], {queryParams: {'was-registered': response.uuid}})
            },
            error: error => {
                if (error.status === 409) {
                    this.form.get('username')?.setErrors({usernameExists: true})
                    this.form.updateValueAndValidity()
                    return;
                } else {
                    console.log(error)
                    alert('Unexpected Error. Please try again')
                }
            }
        })
        
        this.destroyRef.onDestroy(() => {
            subscription.unsubscribe();
        });
    }
    
    /**
     * Custom validator that checks if password and confirm password are the same.
     * @param control The form control the validator gets applied to.
     */
    passwordsMatchValidator(control: AbstractControl): { [key: string]: boolean } | null {
        const form = control as FormGroup;
        
        if (form.get('password')?.value !== form.get('confirmPassword')?.value) {
            return {passwordMissmatch: true};
        }
        
        return null;
    }
    
    /**
     * Trims the values of the string fields of the form.
     */
    trimValues() {
        const username = this.form.get('username')!.value;
        const password = this.form.get('password')!.value;
        
        this.form.get('username')!.setValue(username!.trim());
        this.form.get('password')!.setValue(password!.trim());
    }
    
    /**
     * Check if the username already exists in the database.
     * If it exists the error validator for the username gets updated.
     */
    checkUsernameAvailability() {
        const username = this.form.get('username')?.value?.trim();
        if (!username) {
            return
        }
        
        this.authService.checkUsernameExists(username).subscribe({
            next: isUsernameUsed => {
                if (isUsernameUsed) {
                    this.form.get('username')?.setErrors({usernameExists: true})
                }
            },
            error: error => {
            }
        })
    }
}
