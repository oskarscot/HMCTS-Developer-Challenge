import { Outlet } from "react-router-dom";
import { Header } from "./Header";
import { Toaster } from "../ui/sonner";

export function MainLayout() {
  return (
    <div className="flex min-h-screen flex-col">
      <Header />
      <main className="flex-1">
        <Outlet />
      </main>
      <Toaster richColors/>
    </div>
  );
}
