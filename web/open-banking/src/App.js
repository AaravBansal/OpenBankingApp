import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';

import LoginPage from './LoginPage';
import RegistrationPage from './RegistrationPage';
import Dashboard from './Dashboard';

import { ThemeProvider, createTheme } from '@mui/material/styles';

// Create MUI theme with Montserrat font
const theme = createTheme({
    typography: {
        fontFamily: "'Montserrat', sans-serif",
    },
});

export default function App() {
    const [googleUser, setGoogleUser] = useState(null);

    const clearGoogleUser = () => setGoogleUser(null);

    return (
        <ThemeProvider theme={theme}>
            <Router>
                <Routes>
                    <Route path="/" element={<Navigate to="/login" replace />} />

                    <Route
                        path="/login"
                        element={<LoginPage setGoogleUser={setGoogleUser} />}
                    />

                    <Route
                        path="/register"
                        element={
                            <RegistrationPage
                                googleUser={googleUser}
                                clearGoogleUser={clearGoogleUser}
                            />
                        }
                    />

                    <Route path="/dashboard" element={<Dashboard />} />
                </Routes>
            </Router>
        </ThemeProvider>
    );
}
