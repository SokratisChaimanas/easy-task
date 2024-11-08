import {Component, inject, OnInit, signal} from '@angular/core';
import {RouterLink} from "@angular/router";
import {AuthService} from "../../shared/services/auth.service";

@Component({
    selector: 'app-header',
    standalone: true,
    imports: [
        RouterLink
    ],
    templateUrl: './header.component.html',
    styleUrl: './header.component.css'
})

export class HeaderComponent {
    authService = inject(AuthService);
    
    loggedInUser = this.authService.loggedInUser;
    
    onLogout() {
        this.authService.removeToken();
        this.authService.loggedInUser.set(null)
    }
}
