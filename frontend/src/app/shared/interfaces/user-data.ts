export interface UserRegisterData {
    username: string,
    password: string,
    confirmPassword: string;
}

export interface UserLoginData {
    username: string,
    password: string
}

export interface LoggedInUserData {
    username: string,
}
