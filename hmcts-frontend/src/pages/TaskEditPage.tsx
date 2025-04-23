import { useState, useEffect } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import { TaskForm } from "@/components/tasks/TaskForm";
import { Task } from "@/types/task";
import taskApi from "@/api/taskApi";
import { Button } from "@/components/ui/button";
import { ArrowLeft } from "lucide-react";
import { toast } from "sonner";

export function TaskEditPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [task, setTask] = useState<Task | null>(null);
  const [loading, setLoading] = useState(true);

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

  if (loading) {
    return <div className="flex justify-center p-8">Loading task...</div>;
  }

  if (!task) {
    return <div className="text-center p-8">Task not found</div>;
  }

  return (
    <div className="container mx-auto py-8">
      <div className="mb-6">
        <Button variant="ghost" asChild className="pl-0">
          <Link to={`/tasks/${id}`}>
            <ArrowLeft className="mr-2 h-4 w-4" />
            Back to Task
          </Link>
        </Button>
      </div>
      
      <div className="max-w-2xl mx-auto">
        <h1 className="text-2xl font-bold mb-6">Edit Task</h1>
        <TaskForm initialData={task} isEditing />
      </div>
    </div>
  );
}