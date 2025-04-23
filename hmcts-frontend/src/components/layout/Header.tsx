import { Link } from "react-router-dom";
import { ModeToggle } from "@/components/ui/mode-toggle";
import { Button } from "@/components/ui/button";
import { PlusCircle } from "lucide-react";

export function Header() {
  return (
    <header className="border-b">
      <div className="container mx-auto flex h-16 items-center justify-between px-4">
        <Link to="/" className="flex items-center">
          <h1 className="text-xl font-bold">HMCTS Task Manager</h1>
        </Link>

        <div className="flex items-center gap-4">
          <Button asChild variant="outline" size="sm">
            <Link to="/tasks/create">
              <PlusCircle className="mr-2 h-4 w-4" />
              New Task
            </Link>
          </Button>
          <ModeToggle />
        </div>
      </div>
    </header>
  );
}
