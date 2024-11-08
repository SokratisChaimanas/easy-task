import {effect, inject, Injectable, signal} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {LoggedInUserData, UserLoginData, UserRegisterData} from "../interfaces/user-data";
import {map} from "rxjs";
import {
    UserRegistrationResponse, UserLoginResponse, UsernameCheckResponse
} from "../interfaces/responses";
import {jwtDecode} from "jwt-decode";

@Injectable({
    providedIn: 'root'
})


export class AuthService {
    private http = inject(HttpClient)
    private API_URL_AUTH = 'http://localhost:8080/auth'
    
    loggedInUser = signal<LoggedInUserData | null>(null)
    
    constructor() {
        const authToken = localStorage.getItem('authToken');
        if (authToken) {
            this.loggedInUser.set(this.decodeToken(authToken));
        }
    }
    
    decodeToken(token: string) {
        return jwtDecode(token).sub as unknown as LoggedInUserData;
    }
    
    
    /**
     * Sends the user registration data to the server.
     * @param userdata The JSON containing the registration data.
     */
    registerUser(userdata: UserRegisterData) {
        return this.http.post<UserRegistrationResponse>(`${this.API_URL_AUTH}/register`, userdata).pipe(
            map(response => response.data)      // holds the uuid and username of the created user
        )
    }
    
    /**
     * Sends the login data to the server.
     * @param credentials The credentials of the user.
     */
    loginUser(credentials: UserLoginData) {
        return this.http.post<UserLoginResponse>(`${this.API_URL_AUTH}/login`, credentials).pipe(
            map(response => response.data)      // Holds the generated JWT token
        )
    }
    
    /**
     * Performs a get request to the server checking if a username exists.
     * @param username The username that gets checked.
     */
    checkUsernameExists(username: string) {
        return this.http.get<UsernameCheckResponse>(`${this.API_URL_AUTH}/${username}`).pipe(
            map(response => response.data !== null)     // true if the username exists, false otherwise
        )
    }
    
    /**
     * Stores in the local storage an authToken key with the value of the token.
     * @param token The token passed on from the backend.
     */
    storeToken(token: string) {
        localStorage.setItem('authToken', token)
    }
    
    /**
     * Removes the authToken key-value pair from the local storage.
     */
    removeToken() {
        localStorage.removeItem('authToken')
    }
}
