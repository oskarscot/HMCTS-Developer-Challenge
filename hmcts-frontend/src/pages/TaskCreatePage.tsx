import { TaskForm } from "@/components/tasks/TaskForm";
import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { ArrowLeft } from "lucide-react";

export function TaskCreatePage() {
  return (
    <div className="container mx-auto py-8">
      <div className="mb-6">
        <Button variant="ghost" asChild className="pl-0">
          <Link to="/">
            <ArrowLeft className="mr-2 h-4 w-4" />
            Back to Tasks
          </Link>
        </Button>
      </div>
      
      <div className="max-w-2xl mx-auto">
        <h1 className="text-2xl font-bold mb-6">Create New Task</h1>
        <TaskForm />
      </div>
    </div>
  );
}