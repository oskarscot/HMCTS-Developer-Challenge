import { z } from 'zod';
import { TaskStatus } from '../types/task';

const isFutureDate = (date: Date) => {
  const now = new Date();
  now.setHours(0, 0, 0, 0); // Reset hours to compare just the date
  return date >= now;
};

export const taskCreateSchema = z.object({
  title: z
    .string()
    .min(3, { message: 'Title must be at least 3 characters' })
    .max(100, { message: 'Title must be less than 100 characters' }),
  description: z
    .string()
    .max(500, { message: 'Description must be less than 500 characters' })
    .optional(),
  status: z.nativeEnum(TaskStatus, {
    errorMap: () => ({ message: 'Please select a valid status' }),
  }),
  dueDate: z
    .string()
    .refine((date) => !isNaN(new Date(date).getTime()), {
      message: 'Please enter a valid date',
    })
    .refine((date) => isFutureDate(new Date(date)), {
      message: 'Due date must be in the future',
    }),
});

export const taskUpdateSchema = z.object({
  title: z
    .string()
    .min(3, { message: 'Title must be at least 3 characters' })
    .max(100, { message: 'Title must be less than 100 characters' })
    .optional(),
  description: z
    .string()
    .max(500, { message: 'Description must be less than 500 characters' })
    .optional(),
  status: z
    .nativeEnum(TaskStatus, {
      errorMap: () => ({ message: 'Please select a valid status' }),
    })
    .optional(),
  dueDate: z
    .string()
    .refine((date) => !date || !isNaN(new Date(date).getTime()), {
      message: 'Please enter a valid date',
    })
    .optional(),
});

export type TaskCreateInput = z.infer<typeof taskCreateSchema>;
export type TaskUpdateInput = z.infer<typeof taskUpdateSchema>;