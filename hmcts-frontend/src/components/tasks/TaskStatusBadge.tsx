import { Badge } from "@/components/ui/badge";
import { TaskStatus } from "@/types/task";
import { getStatusColor, getStatusText, isTaskOverdue } from "@/lib/utils";

interface TaskStatusBadgeProps {
  status: TaskStatus;
  dueDate?: string;
}

export function TaskStatusBadge({ status, dueDate }: TaskStatusBadgeProps) {
  const isOverdue = dueDate ? isTaskOverdue(dueDate) : false;
  const variant = getStatusColor(status, isOverdue);
  
  return (
    <Badge variant={variant as "default" | "secondary" | "destructive" | "outline" | "success" | "primary" | "muted"}>
      {isOverdue && status !== TaskStatus.COMPLETED ? "OVERDUE" : getStatusText(status)}
    </Badge>
  );
}