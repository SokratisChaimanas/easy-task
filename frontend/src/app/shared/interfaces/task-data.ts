export interface TaskData {
    uuid: string,
    title: string,
    description: string,
    dueDate: string,
    status: string
}

export interface AddingTaskData {
    title: string,
    description: string,
    dueDate: string
}
