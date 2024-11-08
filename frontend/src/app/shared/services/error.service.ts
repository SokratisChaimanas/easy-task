import {Injectable, inject} from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import {Router} from '@angular/router';

@Injectable({
    providedIn: 'root'
})
export class ErrorService {
    private router = inject(Router);
    
    handleResponseError(error: HttpErrorResponse) {
        switch (error.status) {
            case 401:
                alert('User not authorized for this action');
                break;
            case 500:
                alert('Internal server error');
                break;
            case 0:
                alert('Backend server is not running');
                break;
            default:
                console.log('Unexpected error:', error);
                alert('Unexpected error. Please try again.');
                break;
        }
        
        this.router.navigate(['/tasks']); // Redirect to tasks page or login
        
    }
}
