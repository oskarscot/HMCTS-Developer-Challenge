import { type ClassValue, clsx } from 'clsx';
import { twMerge } from 'tailwind-merge';
import { formatDistanceToNow, format, parseISO, isBefore } from 'date-fns';
import { TaskStatus } from '../types/task';

/**
 * Merges tailwind classes
 */
export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

/**
 * Formats a date string to display how long until/since the date
 */
export function formatTimeDistance(dateString: string): string {
  const date = parseISO(dateString);
  return formatDistanceToNow(date, { addSuffix: true });
}

/**
 * Formats a date string to a readable date format
 */
export function formatDate(dateString: string): string {
  return format(parseISO(dateString), 'PPP');
}

/**
 * Formats a date string to a readable date and time format
 */
export function formatDateTime(dateString: string): string {
  return format(parseISO(dateString), 'PPp');
}

/**
 * Checks if a task is overdue
 */
export function isTaskOverdue(dueDate: string): boolean {
  return isBefore(parseISO(dueDate), new Date());
}

/**
 * Get task status badge color
 */
export function getStatusColor(status: TaskStatus, isOverdue = false): string {
  if (isOverdue && status !== TaskStatus.COMPLETED) {
    return 'destructive';
  }

  switch (status) {
    case TaskStatus.PENDING:
      return 'secondary';
    case TaskStatus.IN_PROGRESS:
      return 'primary';
    case TaskStatus.COMPLETED:
      return 'success';
    case TaskStatus.CANCELLED:
      return 'muted';
    default:
      return 'default';
  }
}

/**
 * Get task status display text
 */
export function getStatusText(status: TaskStatus): string {
  return status.replace('_', ' ');
}