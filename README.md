# HMCTS Task Manager

> [!WARNING]  
> This project is a part of the HMCTS Application process, it is not intended to be deployable!

A full-stack task management application built for the His Majesty's Courts and Tribunals Service (HMCTS).

## Overview

This application provides a modern, responsive interface for managing case work tasks. It consists of:

- **Frontend**: A React-based SPA built with Vite, TypeScript, and shadcn/ui components
- **Backend**: A Spring Boot RESTful API with PostgreSQL database

## Project Structure

The project is organized into two main directories:

- `hmcts-frontend/`: React frontend application
- `hmcts-backend/`: Spring Boot backend application

Each component has its own README with specific setup and development instructions.

## Features

- Create, read, update, and delete tasks
- Track task status (Pending, In Progress, Completed, Cancelled)
- Set and manage task due dates
- Filter tasks by status
- Responsive design for desktop and mobile
- Light and dark theme support

## Getting Started

### Prerequisites

- Node.js 18+ and npm/Bun (for frontend)
- Java 17+ and Gradle (for backend)
- PostgreSQL 14+

### Installation and Setup

1. **Clone the repository**

```bash
git clone https://github.com/oskarscot/hmcts-task-manager.git
cd hmcts-task-manager
```

2. **Set up the backend**

See [Backend README](hmcts-backend/README.md) for detailed instructions.

```bash
cd hmcts-backend
./gradlew bootRun
```

3. **Set up the frontend**

See [Frontend README](hmcts-frontend/README.md) for detailed instructions.

```bash
cd hmcts-frontend
npm install
npm run dev
```

4. **Access the application**

The frontend will be available at http://localhost:5173
The backend API will be available at http://localhost:8080

## API Documentation

API documentation is available via Swagger UI at http://localhost:8080/swagger-ui.html when the backend is running.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Built with shadcn/ui components
- Uses Spring Boot for the backend implementation