import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import { DashboardPage } from "@/pages/DashboardPage.tsx";
import { MainLayout } from "@/components/layout/MainLayout";
import { ThemeProvider } from "@/components/ui/theme-provider";
import { TaskCreatePage } from "@/pages/TaskCreatePage";
import { TaskDetailPage } from "@/pages/TaskDetailPage";
import { TaskEditPage } from "@/pages/TaskEditPage";

function App() {
  return (
    <ThemeProvider defaultTheme="system" storageKey="hmcts-task-theme">
      <BrowserRouter>
        <Routes>
          <Route element={<MainLayout />}>
            <Route path="/" element={<DashboardPage />} />
            <Route path="/tasks/create" element={<TaskCreatePage />} />
            <Route path="/tasks/:id" element={<TaskDetailPage />} />
            <Route path="/tasks/:id/edit" element={<TaskEditPage />} />
            <Route path="*" element={<Navigate to="/" replace />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </ThemeProvider>
  );
}

export default App;
