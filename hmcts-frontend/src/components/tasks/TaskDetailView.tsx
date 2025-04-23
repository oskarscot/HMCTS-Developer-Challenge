import { useEffect, useState } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import { Task, TaskStatus } from "../../types/task";
import taskApi from "../../api/taskApi";
import { Button } from "../ui/button";
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from "../ui/card";
import { TaskStatusBadge } from "./TaskStatusBadge";
import { formatDateTime } from "../../lib/utils";
import { ArrowLeft, Calendar, CheckCircle, Clock, Edit, Trash } from "lucide-react";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "../ui/select";
import { toast } from "sonner";

export function TaskDetailView() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [task, setTask] = useState<Task | null>(null);
  const [loading, setLoading] = useState(true);
  const [updating, setUpdating] = useState(false);

  useEffect(() => {
    const fetchTask = async () => {
      if (!id) return;
      
      try {
        setLoading(true);
        const data = await taskApi.getTaskById(parseInt(id));
        setTask(data);
      } catch (error) {
        toast.error("Error fetching task", {
          description: "There was a problem loading the task details. Please try again.",
        });
        navigate("/");
      } finally {
        setLoading(false);
      }
    };

    fetchTask();
  }, [id, navigate]);

  const handleStatusChange = async (status: string) => {
    if (!task || !id) return;
    
    try {
      setUpdating(true);
      const updatedTask = await taskApi.updateTaskStatus(parseInt(id), status as TaskStatus);
      setTask(updatedTask);
      toast.info("Status updated", {
        description: `Task status changed to ${status}`,
      });
    } catch (error) {
      toast.error("Error updating status", {
        description: "There was a problem updating the task status. Please try again.",
      });
    } finally {
      setUpdating(false);
    }
  };

  const handleDelete = async () => {
    if (!task || !id) return;
    
    if (confirm("Are you sure you want to delete this task?")) {
      try {
        await taskApi.deleteTask(parseInt(id));
        toast.info("Task deleted", {
          description: "The task has been successfully deleted.",
        });
        navigate("/");
      } catch (error) {
        toast.error("Error deleting task", {
          description: "There was a problem deleting the task. Please try again.",
        });
      }
    }
  };

  if (loading) {
    return <div className="flex justify-center p-8">Loading task details...</div>;
  }

  if (!task) {
    return <div className="text-center p-8">Task not found</div>;
  }

  return (
    <div>
      <div className="mb-6">
        <Button variant="ghost" asChild className="pl-0">
          <Link to="/">
            <ArrowLeft className="mr-2 h-4 w-4" />
            Back to Tasks
          </Link>
        </Button>
      </div>

      <Card className="max-w-3xl mx-auto">
        <CardHeader>
          <div className="flex justify-between items-start">
            <div>
              <CardTitle className="text-2xl">{task.title}</CardTitle>
              <p className="text-sm text-muted-foreground mt-1">
                Created {formatDateTime(task.createdAt)}
              </p>
            </div>
            <TaskStatusBadge status={task.status} dueDate={task.dueDate} />
          </div>
        </CardHeader>
        
        <CardContent className="space-y-6">
          <div>
            <h3 className="text-lg font-medium mb-2">Description</h3>
            <p className="whitespace-pre-line">
              {task.description || "No description provided"}
            </p>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div className="flex items-center">
              <Calendar className="h-5 w-5 mr-2 text-muted-foreground" />
              <div>
                <p className="text-sm font-medium">Due Date</p>
                <p>{formatDateTime(task.dueDate)}</p>
              </div>
            </div>
            
            <div className="flex items-center">
              <Clock className="h-5 w-5 mr-2 text-muted-foreground" />
              <div>
                <p className="text-sm font-medium">Last Updated</p>
                <p>{formatDateTime(task.updatedAt)}</p>
              </div>
            </div>
          </div>
          
          <div>
            <h3 className="text-lg font-medium mb-2">Update Status</h3>
            <div className="flex items-center gap-4">
              <Select
                value={task.status}
                onValueChange={handleStatusChange}
                disabled={updating}
              >
                <SelectTrigger className="w-[180px]">
                  <SelectValue placeholder="Change status" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value={TaskStatus.PENDING}>Pending</SelectItem>
                  <SelectItem value={TaskStatus.IN_PROGRESS}>In Progress</SelectItem>
                  <SelectItem value={TaskStatus.COMPLETED}>Completed</SelectItem>
                  <SelectItem value={TaskStatus.CANCELLED}>Cancelled</SelectItem>
                </SelectContent>
              </Select>
              
              {task.status === TaskStatus.PENDING && (
                <Button 
                  onClick={() => handleStatusChange(TaskStatus.IN_PROGRESS)}
                  disabled={updating}
                >
                  <CheckCircle className="mr-2 h-4 w-4" />
                  Start Working
                </Button>
              )}
              
              {task.status === TaskStatus.IN_PROGRESS && (
                <Button 
                  onClick={() => handleStatusChange(TaskStatus.COMPLETED)}
                  disabled={updating}
                >
                  <CheckCircle className="mr-2 h-4 w-4" />
                  Mark Complete
                </Button>
              )}
            </div>
          </div>
        </CardContent>
        
        <CardFooter className="flex justify-between">
          <Button variant="outline" asChild>
            <Link to={`/tasks/${task.id}/edit`}>
              <Edit className="mr-2 h-4 w-4" />
              Edit Task
            </Link>
          </Button>
          
          <Button variant="destructive" onClick={handleDelete}>
            <Trash className="mr-2 h-4 w-4" />
            Delete Task
          </Button>
        </CardFooter>
      </Card>
    </div>
  );
}