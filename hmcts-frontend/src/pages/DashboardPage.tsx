import { TaskList } from "@/components/tasks/TaskList";

export function DashboardPage() {
    return (
      <div className="container mx-auto py-8">
        <h1 className="text-2xl font-bold mb-6">Task Management</h1>
        <TaskList />
      </div>
    );
  }