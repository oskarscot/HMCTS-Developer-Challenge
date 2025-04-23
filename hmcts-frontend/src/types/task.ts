export enum TaskStatus {
    PENDING = 'PENDING',
    IN_PROGRESS = 'IN_PROGRESS',
    COMPLETED = 'COMPLETED',
    CANCELLED = 'CANCELLED'
}

export interface Task {
    id: number;
    title: string;
    description?: string;
    status: TaskStatus;
    dueDate: string; // ISO date string
    createdAt: string; // ISO date string
    updatedAt: string; // ISO date string
}

export interface TaskCreate {
    title: string;
    description?: string;
    status: TaskStatus;
    dueDate: string; // ISO date string
}

export interface TaskUpdate {
    title?: string;
    description?: string;
    status?: TaskStatus;
    dueDate?: string; // ISO date string
}