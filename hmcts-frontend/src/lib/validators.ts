import { z } from 'zod';
import { TaskStatus } from '@/types/task';

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
    .refine((date) => {
      const dateObj = new Date(date);
      const today = new Date();
      today.setHours(0, 0, 0, 0);
      
      return dateObj >= today;
    }, {
      message: 'Due date must be in the present or future',
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