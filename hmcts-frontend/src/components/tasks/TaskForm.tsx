import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useNavigate } from "react-router-dom";
import { Task, TaskStatus } from "@/types/task";
import { TaskCreateInput, TaskUpdateInput, taskCreateSchema, taskUpdateSchema } from "../../lib/validators";
import taskApi from "@/api/taskApi";
import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "../ui/form";
import { Input } from "../ui/input";
import { Textarea } from "../ui/textarea";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "../ui/select";
import { CalendarIcon, Loader2 } from "lucide-react";
import { format } from "date-fns";
import { cn } from "../../lib/utils";
import { Popover, PopoverContent, PopoverTrigger } from "../ui/popover";
import { Calendar } from "../ui/calendar";
import { toast } from "sonner";

interface TaskFormProps {
  initialData?: Task;
  isEditing?: boolean;
}

export function TaskForm({ initialData, isEditing = false }: TaskFormProps) {
  const navigate = useNavigate();
  
  const form = useForm<TaskCreateInput | TaskUpdateInput>({
    resolver: zodResolver(isEditing ? taskUpdateSchema : taskCreateSchema),
    defaultValues: initialData ? {
      title: initialData.title,
      description: initialData.description || "",
      status: initialData.status,
      dueDate: initialData.dueDate.split("T")[0], // Get just the date part
    } : {
      title: "",
      description: "",
      status: TaskStatus.PENDING,
      dueDate: "",
    },
  });
  
  const { isSubmitting } = form.formState;

  const onSubmit = async (data: TaskCreateInput | TaskUpdateInput) => {
    try {
      if (isEditing && initialData) {
        await taskApi.updateTask(initialData.id, data);
        toast.info("Task updated", {
          description: "The task has been successfully updated.",
        });
      } else {
        await taskApi.createTask(data as TaskCreateInput);
        toast.info("Task created", {
          description: "The task has been successfully created.",
        });
      }
      navigate("/");
    } catch (error) {
      toast.error(`Error ${isEditing ? "updating" : "creating"} task`, {
        description: `There was a problem ${isEditing ? "updating" : "creating"} the task. Please try again.`,
      });
    }
  };

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-6">
        <FormField
          control={form.control}
          name="title"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Title</FormLabel>
              <FormControl>
                <Input placeholder="Task title" {...field} />
              </FormControl>
              <FormDescription>
                A clear, concise title for the task.
              </FormDescription>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="description"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Description</FormLabel>
              <FormControl>
                <Textarea
                  placeholder="Detailed description of the task"
                  className="min-h-32"
                  {...field}
                />
              </FormControl>
              <FormDescription>
                Provide details, requirements, or context for the task.
              </FormDescription>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="status"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Status</FormLabel>
              <Select
                onValueChange={field.onChange}
                defaultValue={field.value}
                disabled={isSubmitting}
              >
                <FormControl>
                  <SelectTrigger>
                    <SelectValue placeholder="Select a status" />
                  </SelectTrigger>
                </FormControl>
                <SelectContent>
                  <SelectItem value={TaskStatus.PENDING}>Pending</SelectItem>
                  <SelectItem value={TaskStatus.IN_PROGRESS}>In Progress</SelectItem>
                  <SelectItem value={TaskStatus.COMPLETED}>Completed</SelectItem>
                  <SelectItem value={TaskStatus.CANCELLED}>Cancelled</SelectItem>
                </SelectContent>
              </Select>
              <FormDescription>
                The current status of the task.
              </FormDescription>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="dueDate"
          render={({ field }) => (
            <FormItem className="flex flex-col">
              <FormLabel>Due Date</FormLabel>
              <Popover>
                <PopoverTrigger asChild>
                  <FormControl>
                    <Button
                      variant="outline"
                      className={cn(
                        "w-full pl-3 text-left font-normal",
                        !field.value && "text-muted-foreground"
                      )}
                      disabled={isSubmitting}
                    >
                      {field.value ? (
                        format(new Date(field.value), "PPP")
                      ) : (
                        <span>Select a date</span>
                      )}
                      <CalendarIcon className="ml-auto h-4 w-4 opacity-50" />
                    </Button>
                  </FormControl>
                </PopoverTrigger>
                <PopoverContent className="w-auto p-0" align="start">
                  <Calendar
                    mode="single"
                    selected={field.value ? new Date(field.value) : undefined}
                    onSelect={(date) => field.onChange(date ? format(date, "yyyy-MM-dd") : "")}
                    disabled={(date) => date < new Date()}
                    initialFocus
                  />
                </PopoverContent>
              </Popover>
              <FormDescription>
                The date by which the task needs to be completed.
              </FormDescription>
              <FormMessage />
            </FormItem>
          )}
        />

        <div className="flex gap-4 justify-end">
          <Button 
            type="button" 
            variant="outline" 
            onClick={() => navigate(-1)}
            disabled={isSubmitting}
          >
            Cancel
          </Button>
          <Button type="submit" disabled={isSubmitting}>
            {isSubmitting && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
            {isEditing ? "Update Task" : "Create Task"}
          </Button>
        </div>
      </form>
    </Form>
  );
}