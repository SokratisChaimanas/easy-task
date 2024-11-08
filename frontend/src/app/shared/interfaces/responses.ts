import { TaskData } from "./task-data";

export interface UsernameCheckResponse {
    data: {
        uuid: string;
        username: string;
    } | null; // Nullable data if request provided blank username
}

export interface UserRegistrationResponse {
    data: {
        uuid: string;       // Uuid of the created user
        username: string;   // Username of the created user
    };
}

export interface UserLoginResponse {
    data: string; // Holds the JWT token
}

export interface TaskListResponse {
    data: TaskData[]; // List of all the for the principal user tasks
}

export interface TaskCompletionResponse {
    data: TaskData; // Details of the completed task
}

export interface AddTaskResponse {
    data: TaskData; // Details of the added task
}
