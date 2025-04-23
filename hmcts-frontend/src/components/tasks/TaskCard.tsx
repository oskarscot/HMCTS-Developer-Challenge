import { useState } from "react";
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Task } from "@/types/task";
import { TaskStatusBadge } from "@/components/tasks/TaskStatusBadge";
import { formatTimeDistance, formatDate } from "@/lib/utils";
import { Button } from "@/components/ui/button";
import { CalendarIcon, Edit, Trash } from "lucide-react";
import { Link } from "react-router-dom";
import { ConfirmDeleteDialog } from "@/components/dialog/ConfirmDeleteDialog";

interface TaskCardProps {
  task: Task;
  onDelete: (id: number) => void;
}

export function TaskCard({ task, onDelete }: TaskCardProps) {
  const [showDeleteDialog, setShowDeleteDialog] = useState(false);

  const handleDeleteClick = () => {
    setShowDeleteDialog(true);
  };

  const handleConfirmDelete = () => {
    onDelete(task.id);
    setShowDeleteDialog(false);
  };

  return (
    <Card className="w-full">
      <CardHeader className="pb-2">
        <div className="flex justify-between items-start">
          <CardTitle className="text-lg truncate">{task.title}</CardTitle>
          <TaskStatusBadge status={task.status} dueDate={task.dueDate} />
        </div>
      </CardHeader>
      
      <CardContent>
        <p className="text-muted-foreground text-sm mb-4 line-clamp-2">
          {task.description || "No description provided"}
        </p>
        
        <div className="flex items-center text-sm text-muted-foreground">
          <CalendarIcon className="h-4 w-4 mr-1" />
          <span>Due {formatTimeDistance(task.dueDate)} ({formatDate(task.dueDate)})</span>
        </div>
      </CardContent>
      
      <CardFooter className="flex justify-between pt-2">
        <Button variant="ghost" size="sm" asChild>
          <Link to={`/tasks/${task.id}`}>View Details</Link>
        </Button>
        
        <div className="flex gap-2">
          <Button variant="outline" size="icon" asChild>
            <Link to={`/tasks/${task.id}/edit`}>
              <Edit className="h-4 w-4" />
            </Link>
          </Button>
          
          <Button 
            variant="outline" 
            size="icon" 
            onClick={handleDeleteClick}
          >
            <Trash className="h-4 w-4" />
          </Button>
        </div>
      </CardFooter>

      <ConfirmDeleteDialog
        open={showDeleteDialog}
        onOpenChange={setShowDeleteDialog}
        onConfirm={handleConfirmDelete}
        title="Delete Task"
        description="Are you sure you want to delete this task? This action cannot be undone."
      />
    </Card>
  );
}