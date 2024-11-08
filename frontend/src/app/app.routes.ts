import {Routes} from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {SignupComponent} from "./components/signup/signup.component";
import {TasksComponent} from "./components/tasks/tasks.component";
import {AddTaskComponent} from "./components/tasks/add-task/add-task.component";
import {authGuard, loggedInGuard} from "./shared/guards/auth.guard";

export const routes: Routes = [
    {path: '', redirectTo: '/tasks', pathMatch: 'full'},
    {path: 'login', component: LoginComponent, canActivate: [loggedInGuard]},
    {path: 'sign-up', component: SignupComponent, canActivate: [loggedInGuard]},
    {path: 'tasks', component: TasksComponent, canActivate: [authGuard]},
    {path: 'tasks/add-task', component: AddTaskComponent, canActivate: [authGuard]}
];

