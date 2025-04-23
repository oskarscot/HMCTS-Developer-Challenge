# HMCTS Task Manager Frontend

The frontend component of the HMCTS Task Manager application, built with React, TypeScript, and shadcn/ui components.

## Technology Stack

- **React 19**: For building the user interface
- **TypeScript**: For type safety and improved developer experience
- **Vite**: For fast builds and development experience
- **TailwindCSS 4**: For styling
- **shadcn/ui**: For UI components
- **React Router**: For client-side routing
- **React Hook Form**: For form management and validation
- **Zod**: For schema validation
- **Axios**: For API requests
- **date-fns**: For date formatting

## Features

- Task management interface
- Task filtering by status
- Light and dark mode support
- Form validation
- Toast notifications
- Confirmation dialogs

## Getting Started

### Prerequisites

- Node.js 18+ and npm
- Alternatively: Bun runtime (preferred)

### Installation

1. Install dependencies:

```bash
# Using npm
npm install

# Using Bun (recommended)
bun install
```

### Development

Start the development server:

```bash
# Using npm (adjust command)
npm run dev

# Using Bun
bun run dev
```

The application will be available at http://localhost:5173.

### Building for Production

Build the application:

```bash
# Using npm
npm run build

# Using Bun
bun run build
```

Preview the production build:

```bash
# Using npm
npm run preview

# Using Bun
bun run preview
```

## Project Structure

```
hmcts-frontend/
├── public/             # Static assets
├── src/
│   ├── api/            # API client code
│   ├── components/     # React components
│   │   ├── layout/     # Layout components
│   │   ├── tasks/      # Task-related components
│   │   └── ui/         # shadcn/ui components
│   ├── hooks/          # Custom React hooks
│   ├── lib/            # Utility functions and helpers
│   ├── pages/          # Page components
│   ├── types/          # TypeScript type definitions
│   ├── App.tsx         # Main application component
│   ├── index.css       # Global styles
│   └── main.tsx        # Application entry point
├── .eslintrc.js        # ESLint configuration
├── tailwind.config.js  # TailwindCSS configuration
├── tsconfig.json       # TypeScript configuration
└── vite.config.ts      # Vite configuration
```

## Theming

The application supports light and dark themes, which can be customized in `src/index.css`. Theme switching is handled via the `ThemeProvider` component.

## API Integration

The frontend communicates with the backend API defined in `src/api/taskApi.ts`. Make sure the backend server is running before using the frontend.
